package br.tcc.glic.domain.enums;

import android.content.Context;

import br.tcc.glic.domain.R;

/**
 * Created by André on 27/01/2016.
 */
public enum EstadoPersonagem {
    MuitoMal,
    Mal,
    Normal,
    Bem,
    MuitoBem;

    public String toString(Context context) {
        switch (this){
            case MuitoMal:
                return context.getString(R.string.very_bad);
            case Mal:
                return context.getString(R.string.bad);
            case Normal:
                return context.getString(R.string.normal);
            case Bem:
                return context.getString(R.string.well);
            case MuitoBem:
                return context.getString(R.string.very_well);
        }

        return super.toString();
    }
}
