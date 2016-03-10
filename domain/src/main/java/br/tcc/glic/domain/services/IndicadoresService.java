package br.tcc.glic.domain.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.entities.TipoRegistro;
import br.tcc.glic.data.repositories.Repository;
import br.tcc.glic.data.repositories.RepositoryFactory;
import br.tcc.glic.domain.core.Indicador;
import br.tcc.glic.domain.enums.TipoIndicador;

/**
 * Serviço para cálculo de indicadores
 * Created by André on 26/02/2016.
 */
public class IndicadoresService {

    private final String stringDataUltimaSemana;
    private final String stringDataUltimoMes;
    private final String stringDataUltimoDia;
    private final Repository<Registro> repository;

    public IndicadoresService() {
        repository = RepositoryFactory.get(Registro.class);

        Calendar agora = Calendar.getInstance();

        agora.add(Calendar.DAY_OF_MONTH, -1);
        stringDataUltimoDia = String.valueOf(agora.getTimeInMillis());
        agora.add(Calendar.DAY_OF_MONTH, -6);
        stringDataUltimaSemana = String.valueOf(agora.getTimeInMillis());
        agora.add(Calendar.DAY_OF_MONTH, -23);
        stringDataUltimoMes = String.valueOf(agora.getTimeInMillis());
    }

    public List<Indicador> getIndicadores(){
        TipoIndicador[] tiposIndicador = TipoIndicador.values();
        List<Indicador> indicadores = new ArrayList<>(tiposIndicador.length);

        for (TipoIndicador tipo :
                tiposIndicador) {
            indicadores.add(getIndicador(tipo));
        }

        return indicadores;
    }

    public Indicador getIndicador(TipoIndicador tipo) {
        Indicador indicador = new Indicador();
        indicador.setTipo(tipo);
        indicador.setValor(calcularValor(tipo));
        return  indicador;
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
            case GlicemiaMediaEstimada:
                return calcularGlicemiaMediaEstimada();
        }

        throw new UnsupportedOperationException("Tipo de indicador não suportado.");
    }

    private double calcularGlicemiaMediaEstimada() {
        List<Registro> ultimoHbA1c = repository
                .find("tipo = ?",
                        new String[]{ TipoRegistro.HemoglobinaGlicada.toString() },
                        null, "hora desc", "1");

        if(!ultimoHbA1c.isEmpty())
            return calcularGME(ultimoHbA1c.get(0).getValor());
        else
            return 0;
    }

    private double calcularGME(double valor) {
        return (28.7 * valor) - 46.7;
    }

    private double calcularMediaCarboidratosMes() {
        List<Registro> carboidratos = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.CarboidratoIngerido.toString(),
                        stringDataUltimoMes);

        return calcularMedia(getValores(carboidratos));
    }

    private double calcularMediaCarboidratosSemana() {
        List<Registro> carboidratos = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.CarboidratoIngerido.toString(),
                        stringDataUltimaSemana);

        return calcularMedia(getValores(carboidratos));
    }

    private double calcularMediaCarboidratosDia() {
        List<Registro> carboidratos = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.CarboidratoIngerido.toString(),
                        stringDataUltimoDia);

        return calcularMedia(getValores(carboidratos));
    }

    private double calcularMediaGlicemicaMes() {
        List<Registro> glicemias= repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimoMes);

        return calcularMedia(getValores(glicemias));
    }

    private double calcularMediaGlicemicaSemana() {
        List<Registro> glicemias= repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimaSemana);

        return calcularMedia(getValores(glicemias));
    }

    private double calcularMediaGlicemcaDia() {
        List<Registro> glicemias= repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimoDia);

        return calcularMedia(getValores(glicemias));
    }

    private double calcularVariabilidadeGlicemiaMes() {
        List<Registro> glicemias = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimoMes);

        return calcularDesvioPadrao(getValores(glicemias));
    }

    private double calcularVariabilidadeGlicemiaSemana() {
        List<Registro> glicemias = repository
                .find("tipo = ? and hora >= ?",
                        TipoRegistro.Glicemia.toString(),
                        stringDataUltimaSemana);

        return calcularDesvioPadrao(getValores(glicemias));
    }

    private List<Double> getValores(List<Registro> glicemiasSemana) {
        List<Double> valores = new ArrayList<>(glicemiasSemana.size());

        for (Registro glicemia :
                glicemiasSemana) {
            valores.add(glicemia.getValor());
        }

        return valores;
    }

    private double calcularDesvioPadrao(List<Double> valores) {
        double media = calcularMedia(valores);
        double acumulador = 0l;

        for (double valor :
                valores) {
            acumulador += Math.pow(valor - media, 2);
        }

        return Math.sqrt(acumulador / (double)valores.size());
    }

    private double calcularMedia(List<Double> valores) {
        double acumulador = 0l;
        for (double valor :
                valores) {
            acumulador += valor;
        }
        return acumulador / (double)valores.size();
    }
}
