package br.tcc.glic.userconfiguration;

import android.content.Context;

import br.tcc.glic.R;
import br.tcc.glic.domain.enums.TipoTerapia;

/**
 * Enumerador de tipos de campos de registro de dados
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

    public int getOrder(){
        switch (this)
        {
            case Glycemia: return 0;
            case Carbohydrates: return 1;
            case HbA1c: return 3;
            case Insulin: return 2;
            default: return -1;
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
