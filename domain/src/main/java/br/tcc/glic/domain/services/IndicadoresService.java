package br.tcc.glic.domain.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.entities.TipoRegistro;
import br.tcc.glic.data.repositories.Repository;
import br.tcc.glic.data.repositories.RepositoryFactory;
import br.tcc.glic.domain.R;
import br.tcc.glic.domain.core.Indicador;
import br.tcc.glic.domain.enums.QualidadeRegistro;
import br.tcc.glic.domain.enums.TipoIndicador;
import br.tcc.glic.domain.utils.MathUtils;

/**
 * Serviço para cálculo de indicadores
 * Created by André on 26/02/2016.
 */
public class IndicadoresService {

    private final Map<TipoIndicador, Indicador> cache;

    private final String stringDataUltimaSemana;
    private final String stringDataUltimoMes;
    private final String stringDataUltimoDia;
    private final Context context;
    private final Repository<Registro> repository;

    public IndicadoresService(Context context) {
        this.context = context;
        this.cache = new HashMap<>();

        repository = RepositoryFactory.get(Registro.class);

        Calendar agora = Calendar.getInstance();

        agora.add(Calendar.DAY_OF_MONTH, -1);
        stringDataUltimoDia = String.valueOf(agora.getTimeInMillis());
        agora.add(Calendar.DAY_OF_MONTH, -6);
        stringDataUltimaSemana = String.valueOf(agora.getTimeInMillis());
        agora.add(Calendar.DAY_OF_MONTH, -23);
        stringDataUltimoMes = String.valueOf(agora.getTimeInMillis());
    }

    public List<Indicador> getIndicadores() {
        return getIndicadores(false);
    }

    public List<Indicador> getIndicadores(boolean noCache){
        TipoIndicador[] tiposIndicador = TipoIndicador.values();
        List<Indicador> indicadores = new ArrayList<>(tiposIndicador.length);

        for (TipoIndicador tipo :
                tiposIndicador) {
            indicadores.add(getIndicador(tipo, noCache));
        }

        return indicadores;
    }

    public Indicador getIndicador(TipoIndicador tipo, boolean noCache) {
        if(noCache)
            cache.remove(tipo);

        return getIndicador(tipo);
    }

    public Indicador getIndicador(TipoIndicador tipo) {
        if(cache.containsKey(tipo))
            return cache.get(tipo);

        Indicador indicador = new Indicador();
        indicador.setTipo(tipo);
        indicador.setValor(calcularValor(tipo));

        indicador.setQualidade(getQualidadeIndicador(indicador));

        cache.put(tipo, indicador);

        return  indicador;
    }

    private QualidadeRegistro getQualidadeIndicador(Indicador indicador) {
        TipoIndicador tipo = indicador.getTipo();
        if(tipo == TipoIndicador.MediaGlicemicaDia
                || tipo == TipoIndicador.MediaGlicemicaSemana
                || tipo == TipoIndicador.MediaGlicemicaMes)
            return getQualidadeGlicemia(indicador.getValor(), context);

        if(tipo == TipoIndicador.VariabilidadeGlicemiaSemana)
            return getQualidadeVariabilidade(indicador.getValor(),
                    getIndicador(TipoIndicador.MediaGlicemicaSemana).getValor());

        if(tipo == TipoIndicador.VariabilidadeGlicemiaMes)
            return getQualidadeVariabilidade(indicador.getValor(),
                    getIndicador(TipoIndicador.MediaGlicemicaMes).getValor());

        return QualidadeRegistro.Bom;
    }

    private QualidadeRegistro getQualidadeVariabilidade(double valor, double mediaCorrespondente) {
        if(valor > (mediaCorrespondente / 3d))
            return QualidadeRegistro.Alto;

        return QualidadeRegistro.Bom;
    }

    private static QualidadeRegistro getQualidadeGlicemia(double valor, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int min = Integer.parseInt(pref.getString(context.getString(R.string.min_pre_glycemia_config),
                "0"));
        int max = Integer.parseInt(pref.getString(context.getString(R.string.max_pre_glycemia_config),
                String.valueOf(Integer.MAX_VALUE)));

        if(valor < min)
            return QualidadeRegistro.Baixo;

        if(valor > max)
            return QualidadeRegistro.Alto;

        return QualidadeRegistro.Bom;
    }

    private double calcularValor(TipoIndicador tipo) {
        switch (tipo){
            case VariabilidadeGlicemiaSemana:
                return calcularVariabilidadeGlicemiaSemana();
            case VariabilidadeGlicemiaMes:
                return calcularVariabilidadeGlicemiaMes();
            case MediaGlicemicaDia:
                return calcularMediaGlicemcaDia();
            case MediaGlicemicaSemana:
                return calcularMediaGlicemicaSemana();
            case MediaGlicemicaMes:
                return calcularMediaGlicemicaMes();
            case MediaCarboidratosDia:
                return calcularMediaCarboidratosDia();
            case MediaCarboidratosSemana:
                return calcularMediaCarboidratosSemana();
            case MediaCarboidratosMes:
                return calcularMediaCarboidratosMes();
        }

        throw new UnsupportedOperationException("Tipo de indicador não suportado.");
    }

    public static int calcularGME(double valor) {
        return (int)Math.round((28.7 * valor) - 46.7);
    }

    private double calcularMediaCarboidratosMes() {
        List<Registro> carboidratos = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.CarboidratoIngerido.toString(),
                        stringDataUltimoMes);

        return MathUtils.calcularMedia(getValores(carboidratos));
    }

    private double calcularMediaCarboidratosSemana() {
        List<Registro> carboidratos = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.CarboidratoIngerido.toString(),
                        stringDataUltimaSemana);

        return MathUtils.calcularMedia(getValores(carboidratos));
    }

    private double calcularMediaCarboidratosDia() {
        List<Registro> carboidratos = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.CarboidratoIngerido.toString(),
                        stringDataUltimoDia);

        return MathUtils.calcularMedia(getValores(carboidratos));
    }

    private double calcularMediaGlicemicaMes() {
        List<Registro> glicemias= repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimoMes);

        return MathUtils.calcularMedia(getValores(glicemias));
    }

    private double calcularMediaGlicemicaSemana() {
        List<Registro> glicemias= repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimaSemana);

        return MathUtils.calcularMedia(getValores(glicemias));
    }

    private double calcularMediaGlicemcaDia() {
        List<Registro> glicemias= repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimoDia);

        return MathUtils.calcularMedia(getValores(glicemias));
    }

    private double calcularVariabilidadeGlicemiaMes() {
        List<Registro> glicemias = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimoMes);

        return MathUtils.calcularDesvioPadrao(getValores(glicemias));
    }

    private double calcularVariabilidadeGlicemiaSemana() {
        List<Registro> glicemias = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimaSemana);

        return MathUtils.calcularDesvioPadrao(getValores(glicemias));
    }

    private List<Double> getValores(List<Registro> glicemiasSemana) {
        List<Double> valores = new ArrayList<>(glicemiasSemana.size());

        for (Registro glicemia :
                glicemiasSemana) {
            valores.add(glicemia.getValor());
        }

        return valores;
    }
}
