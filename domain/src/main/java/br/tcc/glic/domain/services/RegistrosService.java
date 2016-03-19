package br.tcc.glic.domain.services;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.entities.TipoRegistro;
import br.tcc.glic.data.repositories.Repository;
import br.tcc.glic.data.repositories.RepositoryFactory;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.utils.Conversions;

/**
 * Serviço de registro de dados de tratamento
 * Created by André on 02/02/2016.
 */
public class RegistrosService {

    private final Context context;

    public RegistrosService(Context context) {
        this.context = context;
    }

    public void registrarGlicemia(int valor, Date hora){
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        Registro registro = new Registro(TipoRegistro.Glicemia);
        registro.setHora(hora);
        registro.setValor(valor);
        rep.save(registro);
    }

    public  void registrarGlicemia(br.tcc.glic.domain.core.Glicemia glicemia){
        this.registrarGlicemia(glicemia.getValor(), glicemia.getHora());
        glicemia.setQualidade(Conversions.getQualidadeGlicemia(glicemia.getValor(), context));
    }

    public void registrarCarboidratosIngeridos(int quantidade, Date hora){
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        Registro registro = new Registro(TipoRegistro.CarboidratoIngerido);
        registro.setHora(hora);
        registro.setValor(quantidade);
        rep.save(registro);
    }

    public  void registrarCarboidratosIngeridos(br.tcc.glic.domain.core.CarboidratoIngerido carboidratoIngerido){
        this.registrarCarboidratosIngeridos(carboidratoIngerido.getQuantidade(), carboidratoIngerido.getHora());
    }


    public void registrarHemoglobinaGlicada(double valor, Date hora){
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        Registro registro = new Registro(TipoRegistro.HemoglobinaGlicada);
        registro.setHora(hora);
        registro.setValor(valor);
        rep.save(registro);
    }

    public  void registrarHemoglobinaGlicada(br.tcc.glic.domain.core.HemoglobinaGlicada hemoglobinaGlicada){
        this.registrarHemoglobinaGlicada(hemoglobinaGlicada.getValor(), hemoglobinaGlicada.getHora());
        hemoglobinaGlicada.setGme(IndicadoresService.calcularGME(hemoglobinaGlicada.getValor()));
        hemoglobinaGlicada.setQualidade(Conversions.getQualidadeGlicemia(hemoglobinaGlicada.getGme(), context));
    }

    public void registrarAplicacaoInsulina(double quantidade, Date hora, br.tcc.glic.domain.core.TipoInsulina tipo){
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        Registro registro = new Registro(TipoRegistro.AplicacaoInsulina);
        registro.setHora(hora);
        registro.setValor(quantidade);
        registro.setTipoInsulina(Conversions.tipoInsulina(tipo));
        rep.save(registro);
    }

    public  void registrarAplicacaoInsulina(br.tcc.glic.domain.core.AplicacaoInsulina aplicacaoInsulina){
        this.registrarAplicacaoInsulina(aplicacaoInsulina.getQuantidade(),
                aplicacaoInsulina.getHora(), aplicacaoInsulina.getTipo());
    }

    public List<br.tcc.glic.domain.core.Registro> listRegistros() {
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        List<Registro> registros =  rep.toList();
        Collections.sort(registros);
        List<br.tcc.glic.domain.core.Registro> registrosRet = new ArrayList<>();
        for (Registro registro :
                registros) {
            switch (registro.getTipo()) {
                case Glicemia:
                    Glicemia glicemia = Conversions.glicemia(registro, context);
                    registrosRet.add(glicemia);
                    break;
                case HemoglobinaGlicada:
                    HemoglobinaGlicada hemoglobinaGlicada = Conversions.hemoglobinaGlicada(registro, context);
                    registrosRet.add(hemoglobinaGlicada);
                    break;
                case CarboidratoIngerido:
                    CarboidratoIngerido carboidratoIngerido = Conversions.carboidratoIngerido(registro);
                    registrosRet.add(carboidratoIngerido);
                    break;
                case AplicacaoInsulina:
                    AplicacaoInsulina aplicacaoInsulina = Conversions.aplicacaoInsulina(registro);
                    registrosRet.add(aplicacaoInsulina);
                    break;
            }
        }

        return  registrosRet;
    }

    public void delete(br.tcc.glic.domain.core.Registro registro) {
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        Registro registroBd = convertRegistro(registro);
        rep.delete(registroBd);
    }

    public void update(br.tcc.glic.domain.core.Registro registro) {
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        Registro registroBd = convertRegistro(registro);
        rep.save(registroBd);
    }

    private Registro convertRegistro(br.tcc.glic.domain.core.Registro registro) {
        if(registro instanceof Glicemia)
            return Conversions.registro((Glicemia) registro);

        if(registro instanceof CarboidratoIngerido)
            return Conversions.registro((CarboidratoIngerido) registro);

        if(registro instanceof AplicacaoInsulina)
            return Conversions.registro((AplicacaoInsulina) registro);

        if(registro instanceof HemoglobinaGlicada)
            return Conversions.registro((HemoglobinaGlicada) registro);

        return null;
    }
}
