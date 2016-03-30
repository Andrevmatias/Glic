package br.tcc.glic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.services.RegistrosService;

/**
 * Fragment para exibição de gráfico de glicemias
 * Created by André on 27/03/2016.
 */
public class GlycemiaChartFragment extends Fragment {
    private LineChart chart;
    private int daysScope = 30;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public GlycemiaChartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart_glycemia, container, false);

        initComponents(view);
        fillData();

        return view;
    }

    private void fillData() {
        List<Glicemia> glycemias = getSortedGlycemias();
        LineData data = getChartData(glycemias);
        chart.setData(data);
    }

    private LineData getChartData(List<Glicemia> glycemias) {
        List<String> xAxisValues = new ArrayList<>();
        List<Entry> entriesAvg = new ArrayList<>(glycemias.size());
        List<Entry> entriesVar = new ArrayList<>(glycemias.size());

        String currentX = null;
        int xIndex = -1;
        List<Glicemia> currentList = new ArrayList<>();
        for(Glicemia glycemia : glycemias) {
            String xValue = dateFormat.format(glycemia.getHora());
            if(!xValue.equals(currentX)) {
                currentX = xValue;
                xAxisValues.add(xValue);

                if (!currentList.isEmpty()){
                    entriesAvg.add(new Entry(calcAverage(currentList), xIndex));
                    entriesVar.add(new Entry(calcStandardDeviation(currentList), xIndex));
                }

                xIndex++;
                currentList.clear();
            }

            currentList.add(glycemia);
        }

        if (!currentList.isEmpty()){
            entriesAvg.add(new Entry(calcAverage(currentList), xIndex));
            entriesVar.add(new Entry(calcStandardDeviation(currentList), xIndex));
        }

        List<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet dataSetAvg = new LineDataSet(entriesAvg, getString(R.string.average));
        dataSetAvg.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        dataSetAvg.setCircleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        dataSets.add(dataSetAvg);
        LineDataSet dataSetVar = new LineDataSet(entriesVar, getString(R.string.variability));
        dataSets.add(dataSetVar);

        return  new LineData(xAxisValues,dataSets);
    }

    private float calcStandardDeviation(List<Glicemia> glicemias) {
        double media = calcAverage(glicemias);
        double acumulador = 0l;

        for (Glicemia glicemia :
                glicemias) {
            acumulador += Math.pow(glicemia.getValor() - media, 2);
        }

        return (float) Math.sqrt(acumulador / (double)glicemias.size());
    }

    private float calcAverage(List<Glicemia> glicemias) {
        float acumulador = 0l;
        for (Glicemia glicemia :
                glicemias) {
            acumulador += glicemia.getValor();
        }
        return acumulador / (float)glicemias.size();
    }

    private List<Glicemia> getSortedGlycemias() {
        RegistrosService service = new RegistrosService(getActivity());

        Calendar from = Calendar.getInstance();
        from.add(Calendar.DATE, -daysScope);
        return service.listGlicemias(from.getTime(), new Date());
    }

    private void initComponents(View view) {
        chart = (LineChart) view.findViewById(R.id.chart_glycemia);

        chart.setDescription(getString(R.string.chart_glycemia_description));
    }
}
