package br.tcc.glic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.tcc.glic.R;
import br.tcc.glic.domain.enums.TipoIndicador;
import br.tcc.glic.domain.services.IndicadoresService;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndicatorsFragment extends Fragment {

    private TextView txtDayAvgGlycemia;
    private TextView txtWeekAvgGlycemia;
    private TextView txtMonthAvgGlycemia;
    private TextView txtWeekVarGlycemia;
    private TextView txtMonthVarGlycemia;
    private TextView txtDayAvgCarbohydrates;
    private TextView txtWeekAvgCarbohydrates;
    private TextView txtMonthAvgCarbohydrates;
    private View containerCarbohydrateIndicators;

    public IndicatorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indicators, container, false);
        initComponents(view);
        calcIndicators();
        return view;
    }

    public void calcIndicators() {
        IndicadoresService service = new IndicadoresService();

        double avgDayGlycemia = service.getIndicador(TipoIndicador.MediaGlicemicaDia).getValor();
        double avgWeekGlycemia = service.getIndicador(TipoIndicador.MediaGlicemicaSemana).getValor();
        double avgMonthGlycemia = service.getIndicador(TipoIndicador.MediaGlicemicaMes).getValor();

        double varWeekGlycemia = service.getIndicador(TipoIndicador.VariabilidadeGlicemiaSemana).getValor();
        double varMonthGlycemia = service.getIndicador(TipoIndicador.VariabilidadeGlicemiaMes).getValor();

        double avgDayCarbohydrates = service.getIndicador(TipoIndicador.MediaCarboidratosDia).getValor();
        double avgWeekCarbohydrates = service.getIndicador(TipoIndicador.MediaCarboidratosSemana).getValor();
        double avgMonthCarbohydrates = service.getIndicador(TipoIndicador.MediaCarboidratosMes).getValor();

        txtDayAvgGlycemia
                .setText(Double.isNaN(avgDayGlycemia) ? "-"
                        : String.valueOf((int)Math.floor(avgDayGlycemia)));
        txtWeekAvgGlycemia
                .setText(Double.isNaN(avgWeekGlycemia) ? "-"
                        : String.valueOf((int)Math.floor(avgWeekGlycemia)));
        txtMonthAvgGlycemia
                .setText(Double.isNaN(avgMonthGlycemia) ? "-"
                        : String.valueOf((int)Math.floor(avgMonthGlycemia)));

        txtWeekVarGlycemia
                .setText(Double.isNaN(varWeekGlycemia) ? "-"
                        : String.valueOf((int)Math.floor(varWeekGlycemia)));
        txtMonthVarGlycemia
                .setText(Double.isNaN(varMonthGlycemia) ? "-"
                        : String.valueOf((int)Math.floor(varMonthGlycemia)));

        txtDayAvgCarbohydrates
                .setText(Double.isNaN(avgDayCarbohydrates) ? "-"
                        : String.valueOf((int)Math.floor(avgDayCarbohydrates)));
        txtWeekAvgCarbohydrates
                .setText(Double.isNaN(avgWeekCarbohydrates) ? "-"
                        : String.valueOf((int)Math.floor(avgWeekCarbohydrates)));
        txtMonthAvgCarbohydrates
                .setText(Double.isNaN(avgMonthCarbohydrates) ? "-"
                        : String.valueOf((int)Math.floor(avgMonthCarbohydrates)));

        if(Double.isNaN(avgDayCarbohydrates)
                && Double.isNaN(avgWeekCarbohydrates)
                && Double.isNaN(avgMonthCarbohydrates)) {
            containerCarbohydrateIndicators.setVisibility(View.GONE);
        } else {
            containerCarbohydrateIndicators.setVisibility(View.VISIBLE);
        }
    }

    private void initComponents(View view) {
        containerCarbohydrateIndicators = view.findViewById(R.id.carbohydrates_indicators_container);

        txtDayAvgGlycemia = (TextView) view.findViewById(R.id.day_average_glycemia_indicator);
        txtWeekAvgGlycemia = (TextView) view.findViewById(R.id.week_average_glycemia_indicator);
        txtMonthAvgGlycemia = (TextView) view.findViewById(R.id.month_average_glycemia_indicator);

        txtWeekVarGlycemia = (TextView) view.findViewById(R.id.week_glycemia_variability_indicator);
        txtMonthVarGlycemia = (TextView) view.findViewById(R.id.month_glycemia_variability_indicator);

        txtDayAvgCarbohydrates = (TextView) view.findViewById(R.id.day_average_carbohydrates_indicator);
        txtWeekAvgCarbohydrates = (TextView) view.findViewById(R.id.week_average_carbohydrates_indicator);
        txtMonthAvgCarbohydrates = (TextView) view.findViewById(R.id.month_average_carbohydrates_indicator);
    }

}
