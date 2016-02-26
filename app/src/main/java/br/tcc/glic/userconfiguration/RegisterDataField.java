package br.tcc.glic.userconfiguration;

import android.content.Context;

import br.tcc.glic.R;
import br.tcc.glic.domain.enums.TipoTerapia;

/**
 * Created by André on 14/02/2016.
 */
public enum RegisterDataField {
    Glycemia,
    Carbohydrates,
    Insulin,
    HbA1c;

    public static RegisterDataField[] getByTipoTerapia(TipoTerapia tipoTerapia){
        switch (tipoTerapia){
            case Convencional:
                return new RegisterDataField[]{Glycemia, Insulin};
            case Intensiva:
                return  new RegisterDataField[]{Glycemia, Carbohydrates, Insulin};
            default:
                return new RegisterDataField[] {Glycemia};
        }
    }

    public String getHumanReadableString(Context context){
        switch (this)
        {
            case Glycemia: return context.getString(R.string.glycemia);
            case Carbohydrates: return context.getString(R.string.ingested_carbohydrates);
            case HbA1c: return context.getString(R.string.hba1c);
            case Insulin: return context.getString(R.string.injected_insulin);
            default: return null;
        }
    }
}
