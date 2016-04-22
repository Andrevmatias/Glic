package br.tcc.glic.domain.enums;

import android.content.Context;

import br.tcc.glic.domain.R;

/**
 * Tipos de indicadores calculados pelo sistema
 * Created by André on 25/01/2016.
 */
public enum TipoIndicador {
    VariabilidadeGlicemiaSemana,
    VariabilidadeGlicemiaMes,
    MediaGlicemicaDia,
    MediaGlicemicaSemana,
    MediaGlicemicaMes,
    MediaCarboidratosDia,
    MediaCarboidratosSemana,
    MediaCarboidratosMes;

    public String toString(Context context) {
        switch (this) {
            case VariabilidadeGlicemiaSemana:
                return context.getString(R.string.week_glycemia_variability);
            case VariabilidadeGlicemiaMes:
                return context.getString(R.string.month_glycemia_variability);
            case MediaGlicemicaDia:
                return context.getString(R.string.day_glycemia_average);
            case MediaGlicemicaSemana:
                return context.getString(R.string.week_glycemia_average);
            case MediaGlicemicaMes:
                return context.getString(R.string.month_glycemia_average);
            case MediaCarboidratosDia:
                return context.getString(R.string.day_carbohydrates_average);
            case MediaCarboidratosSemana:
                return context.getString(R.string.week_carbohydrates_average);
            case MediaCarboidratosMes:
                return context.getString(R.string.month_carbohydrates_average);
        }

        return super.toString();
    }
}
