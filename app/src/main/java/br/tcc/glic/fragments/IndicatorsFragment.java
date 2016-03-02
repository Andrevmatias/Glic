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

        txtDayAvgGlycemia
                .setText(String
                        .valueOf(service.getIndicador(TipoIndicador.MediaGlicemicaDia).getValor()));
        txtWeekAvgGlycemia.setText(String
                .valueOf(service.getIndicador(TipoIndicador.MediaGlicemicaSemana).getValor()));
        txtMonthAvgGlycemia.setText(String
                .valueOf(service.getIndicador(TipoIndicador.MediaGlicemicaMes).getValor()));

        txtWeekVarGlycemia.setText(String
                .valueOf(service.getIndicador(TipoIndicador.VariabilidadeGlicemiaSemana).getValor()));
        txtMonthVarGlycemia.setText(String
                .valueOf(service.getIndicador(TipoIndicador.VariabilidadeGlicemiaMes).getValor()));

        txtDayAvgCarbohydrates.setText(String
                .valueOf(service.getIndicador(TipoIndicador.MediaCarboidratosDia).getValor()));
        txtWeekAvgCarbohydrates.setText(String
                .valueOf(service.getIndicador(TipoIndicador.MediaCarboidratosSemana).getValor()));
        txtMonthAvgCarbohydrates.setText(String
                .valueOf(service.getIndicador(TipoIndicador.MediaCarboidratosMes).getValor()));
    }

    private void initComponents(View view) {
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
