package br.tcc.glic.domain.services.defaultdata;

import java.util.ArrayList;
import java.util.List;

import br.tcc.glic.domain.core.TipoInsulina;

/**
 * Created by André on 21/02/2016.
 */
public final class TiposInsulinaPadrao {
    private static List<TipoInsulina> tiposInsulinaPadrao;

    static {
        tiposInsulinaPadrao = new ArrayList<>();

        TipoInsulina regular = new TipoInsulina();
        regular.setCodigo(1l);
        regular.setNome("Regular");
        regular.setInicio(30);
        regular.setPico(120);
        regular.setDuracao(360);

        TipoInsulina aspart = new TipoInsulina();
        aspart.setCodigo(2l);
        aspart.setNome("Aspart/Lispro/Glulisina");
        aspart.setInicio(10);
        aspart.setPico(30);
        aspart.setDuracao(180);

        TipoInsulina nph = new TipoInsulina();
        nph.setCodigo(3l);
        nph.setNome("NPH");
        nph.setInicio(60);
        nph.setPico(180);
        nph.setDuracao(720);

        TipoInsulina glargina = new TipoInsulina();
        glargina.setCodigo(4l);
        glargina.setNome("Glargina");
        glargina.setInicio(60);
        glargina.setDuracao(1440);

        TipoInsulina determir = new TipoInsulina();
        determir.setCodigo(5l);
        determir.setNome("Determir");
        determir.setInicio(60);
        determir.setDuracao(1200);

        tiposInsulinaPadrao.add(regular);
        tiposInsulinaPadrao.add(aspart);
        tiposInsulinaPadrao.add(nph);
        tiposInsulinaPadrao.add(glargina);
        tiposInsulinaPadrao.add(determir);
    }

    public static List<TipoInsulina> getAll() {
        return tiposInsulinaPadrao;
    }
}
