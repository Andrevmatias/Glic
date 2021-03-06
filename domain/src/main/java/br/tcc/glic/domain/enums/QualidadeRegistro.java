package br.tcc.glic.domain.enums;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

import br.tcc.glic.domain.R;

/**
 * Qualidade do valor de um registro
 * Created by André on 15/03/2016.
 */
public enum QualidadeRegistro {
    Baixo,
    Bom,
    Alto;

    public String toString(Context context) {
        switch (this)
        {
            case Baixo:
                return context.getString(R.string.low_fem);
            case Bom:
                return context.getString(R.string.good_fem);
            case Alto:
                return context.getString(R.string.high_fem);
        }

        throw new RuntimeException("QualidadeRegistro not recognized");
    }

    public Drawable getDrawable(Context context) {
        switch (this)
        {
            case Baixo:
                return ContextCompat.getDrawable(context, R.drawable.ic_low);
            case Bom:
                return ContextCompat.getDrawable(context, R.drawable.ic_good);
            case Alto:
                return ContextCompat.getDrawable(context, R.drawable.ic_high);
        }

        throw new RuntimeException("QualidadeRegistro not recognized");
    }

    public static QualidadeRegistro getQualidadeGlicemia(int valor, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int min = Integer.parseInt(pref.getString(context.getString(R.string.min_pre_glycemia_config),
                "0"));
        int max = Integer.parseInt(pref.getString(context.getString(R.string.max_pre_glycemia_config),
                String.valueOf(Integer.MAX_VALUE)));

        if(valor < min)
            return QualidadeRegistro.Baixo;

        if(valor > max)
            return QualidadeRegistro.Alto;

        return QualidadeRegistro.Bom;
    }
}
