package br.tcc.glic.domain.utils;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.entities.TipoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;

/**
 * Created by André on 19/02/2016.
 */
public final class Conversions {
    public static TipoInsulina tipoInsulina(br.tcc.glic.domain.core.TipoInsulina tipo) {
        TipoInsulina tipoBd = new TipoInsulina();
        tipoBd.setId(tipo.getCodigo());
        tipoBd.setDuracao(tipo.getDuracao());
        tipoBd.setInicio(tipo.getInicio());
        tipoBd.setNome(tipo.getNome());
        tipoBd.setPico(tipo.getPico());
        return tipoBd;
    }

    public static br.tcc.glic.domain.core.TipoInsulina tipoInsulina(TipoInsulina tipo) {
        br.tcc.glic.domain.core.TipoInsulina tipoBd = new br.tcc.glic.domain.core.TipoInsulina();
        tipoBd.setCodigo(tipo.getId());
        tipoBd.setDuracao(tipo.getDuracao());
        tipoBd.setInicio(tipo.getInicio());
        tipoBd.setNome(tipo.getNome());
        tipoBd.setPico(tipo.getPico());
        return tipoBd;
    }

    public static Glicemia glicemia(Registro registro) {
        Glicemia glicemia = new Glicemia();
        glicemia.setCodigo(registro.getId());
        glicemia.setHora(registro.getHora());
        glicemia.setValor((int) registro.getValor());
        return glicemia;
    }

    public static CarboidratoIngerido carboidratoIngerido(Registro registro) {
        CarboidratoIngerido carboidrato = new CarboidratoIngerido();
        carboidrato.setCodigo(registro.getId());
        carboidrato.setHora(registro.getHora());
        carboidrato.setQuantidade((int) registro.getValor());
        return carboidrato;
    }

    public static HemoglobinaGlicada hemoglobinaGlicada(Registro registro) {
        HemoglobinaGlicada glicemia = new HemoglobinaGlicada();
        glicemia.setCodigo(registro.getId());
        glicemia.setHora(registro.getHora());
        glicemia.setValor(registro.getValor());
        return glicemia;
    }
}
