package br.tcc.glic.domain.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.entities.TipoInsulina;
import br.tcc.glic.data.entities.TipoRegistro;
import br.tcc.glic.domain.R;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.enums.QualidadeRegistro;

/**
 * Classe para conversão BD <-> Domínio
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

    public static Glicemia glicemia(Registro registro, Context context) {
        Glicemia glicemia = new Glicemia();
        glicemia.setCodigo(registro.getId());
        glicemia.setHora(registro.getHora());
        glicemia.setValor((int) registro.getValor());
        glicemia.setQualidade(getQualidadeGlicemia(glicemia, context));

        return glicemia;
    }

    private static QualidadeRegistro getQualidadeGlicemia(Glicemia glicemia, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int min = Integer.parseInt(pref.getString(context.getString(R.string.min_pre_glycemia_config),
                "0"));
        int max = Integer.parseInt(pref.getString(context.getString(R.string.max_pre_glycemia_config),
                String.valueOf(Integer.MAX_VALUE)));

        if(glicemia.getValor() < min)
            return QualidadeRegistro.Baixo;

        if(glicemia.getValor() > max)
            return QualidadeRegistro.Alto;

        return QualidadeRegistro.Bom;
    }

    public static CarboidratoIngerido carboidratoIngerido(Registro registro) {
        CarboidratoIngerido carboidrato = new CarboidratoIngerido();
        carboidrato.setCodigo(registro.getId());
        carboidrato.setHora(registro.getHora());
        carboidrato.setQuantidade((int) registro.getValor());
        return carboidrato;
    }

    public static HemoglobinaGlicada hemoglobinaGlicada(Registro registro) {
        HemoglobinaGlicada hemoglobinaGlicada = new HemoglobinaGlicada();
        hemoglobinaGlicada.setCodigo(registro.getId());
        hemoglobinaGlicada.setHora(registro.getHora());
        hemoglobinaGlicada.setValor(registro.getValor());
        return hemoglobinaGlicada;
    }

    public static AplicacaoInsulina aplicacaoInsulina(Registro registro) {
        AplicacaoInsulina aplicacaoInsulina = new AplicacaoInsulina();
        aplicacaoInsulina.setCodigo(registro.getId());
        aplicacaoInsulina.setHora(registro.getHora());
        aplicacaoInsulina.setQuantidade(registro.getValor());
        aplicacaoInsulina.setTipo(tipoInsulina(registro.getTipoInsulina()));
        return aplicacaoInsulina;
    }

    public static Registro registro(br.tcc.glic.domain.core.Glicemia glicemia) {
        Registro registroBd = new Registro(TipoRegistro.Glicemia);
        registroBd.setId(glicemia.getCodigo());
        registroBd.setHora(glicemia.getHora());
        registroBd.setValor(glicemia.getValor());
        return registroBd;
    }

    public static Registro registro(br.tcc.glic.domain.core.CarboidratoIngerido carboidratoIngerido) {
        Registro registroBd = new Registro(TipoRegistro.CarboidratoIngerido);
        registroBd.setId(carboidratoIngerido.getCodigo());
        registroBd.setHora(carboidratoIngerido.getHora());
        registroBd.setValor(carboidratoIngerido.getQuantidade());
        return registroBd;
    }

    public static Registro registro(br.tcc.glic.domain.core.AplicacaoInsulina aplicacaoInsulina) {
        Registro registroBd = new Registro(TipoRegistro.AplicacaoInsulina);
        registroBd.setId(aplicacaoInsulina.getCodigo());
        registroBd.setHora(aplicacaoInsulina.getHora());
        registroBd.setValor(aplicacaoInsulina.getQuantidade());
        registroBd.setTipoInsulina(tipoInsulina(aplicacaoInsulina.getTipo()));
        return registroBd;
    }

    public static Registro registro(br.tcc.glic.domain.core.HemoglobinaGlicada hemoglobinaGlicada) {
        Registro registroBd = new Registro(TipoRegistro.HemoglobinaGlicada);
        registroBd.setId(hemoglobinaGlicada.getCodigo());
        registroBd.setHora(hemoglobinaGlicada.getHora());
        registroBd.setValor(hemoglobinaGlicada.getValor());
        return registroBd;
    }
}
