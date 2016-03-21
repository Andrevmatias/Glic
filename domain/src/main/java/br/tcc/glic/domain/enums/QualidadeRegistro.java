package br.tcc.glic.domain.enums;

import android.content.Context;

import br.tcc.glic.domain.R;

/**
 * Qualidade do valor de um registro
 * Created by André on 15/03/2016.
 */
public enum QualidadeRegistro {
    Baixo,
    Bom,
    Alto;

    public String getDescription(Context context) {
        switch (this)
        {
            case Baixo:
                return context.getString(R.string.low);
            case Bom:
                return context.getString(R.string.good);
            case Alto:
                return context.getString(R.string.high);
        }

        throw new RuntimeException("QualidadeRegistro not recognized");
    }
}
