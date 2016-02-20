package br.tcc.glic.domain.utils;

import br.tcc.glic.data.entities.TipoInsulina;

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
}
