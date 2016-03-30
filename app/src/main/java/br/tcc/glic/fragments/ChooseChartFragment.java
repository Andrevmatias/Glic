package br.tcc.glic.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.tcc.glic.R;

/**
 * Fragment para escolha de gráfico
 */
public class ChooseChartFragment extends Fragment {

    private ChartChosenListener listener;

    public ChooseChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_chart, container, false);
        
        initComponents(view);
        
        return view;
    }

    private void initComponents(View view) {
        view.findViewById(R.id.glycemia_chart_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onChartChosen(ChartType.Glycemia);
            }
        });

        view.findViewById(R.id.glycemia_hour_chart_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onChartChosen(ChartType.GlycemiaByHour);
            }
        });

        view.findViewById(R.id.carbohydrate_chart_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onChartChosen(ChartType.Carbohydrates);
            }
        });
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if(activity instanceof ChartChosenListener)
            listener = (ChartChosenListener) activity;
    }

    public interface ChartChosenListener {
        void onChartChosen(ChartType type);
    }

    public enum ChartType {
        Glycemia,
        GlycemiaByHour,
        Carbohydrates
    }
}
