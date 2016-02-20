package br.tcc.glic.domain.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.entities.TipoRegistro;
import br.tcc.glic.data.repositories.Repository;
import br.tcc.glic.data.repositories.RepositoryFactory;
import br.tcc.glic.domain.utils.Conversions;

/**
 * Created by André on 02/02/2016.
 */
public class RegistrarDadosService {
    public void registrarGlicemia(int valor, Date hora){
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        Registro registro = new Registro(TipoRegistro.Glicemia);
        registro.setHora(hora);
        registro.setValor(valor);
        rep.save(registro);
    }

    public  void registrarGlicemia(br.tcc.glic.domain.core.Glicemia glicemia){
        this.registrarGlicemia(glicemia.getValor(), glicemia.getHora());
    }

    public void registrarCarboidratosIngeridos(int quantidade, Date hora){
        Repository<Registro> rep = RepositoryFactory.get(Registro.class);
        Registro registro = new Registro(TipoRegistro.CarboidratoIngerido);
        registro.setHora(hora);
        registro.setValor(quantidade);
        rep.save(registro);
    }

    public  void registrarGlicemia(br.tcc.glic.domain.core.CarboidratoIngerido carboidratoIngerido){
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
            if(registro.getTipo() == TipoRegistro.Glicemia) {
                br.tcc.glic.domain.core.Glicemia novaGlicemia = new br.tcc.glic.domain.core.Glicemia();
                novaGlicemia.setCodigo(registro.getId());
                novaGlicemia.setHora(registro.getHora());
                novaGlicemia.setValor((int)registro.getValor());
                registrosRet.add(novaGlicemia);
            }
        }

        return  registrosRet;
    }
}
