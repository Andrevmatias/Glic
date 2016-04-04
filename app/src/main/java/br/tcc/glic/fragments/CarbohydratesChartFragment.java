package br.tcc.glic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.domain.utils.MathUtils;

/**
 * Fragment para exibição de gráfico de glicemias
 * Created by André on 27/03/2016.
 */
public class CarbohydratesChartFragment extends Fragment {
    private LineChart chart;
    private int daysScope = 30;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy(EEE)");

    public CarbohydratesChartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        initComponents(view);
        fillData();

        return view;
    }

    private void fillData() {
        List<CarboidratoIngerido> carbohydrates = getSortedCarbohydrates();
        LineData data = getChartData(carbohydrates);
        chart.setData(data);
    }

    private LineData getChartData(List<CarboidratoIngerido> carbohydrates) {
        List<String> xAxisValues = new ArrayList<>();
        final List<Entry> entriesAvg = new ArrayList<>(carbohydrates.size());

        String currentX = null;
        int xIndex = -1;
        List<CarboidratoIngerido> currentList = new ArrayList<>();
        for(CarboidratoIngerido glycemia : carbohydrates) {
            String xValue = dateFormat.format(glycemia.getHora());
            if(!xValue.equals(currentX)) {
                currentX = xValue;
                xAxisValues.add(xValue);

                if (!currentList.isEmpty()){
                    entriesAvg.add(new Entry(calcAverage(currentList), xIndex, currentList.size()));
                }

                xIndex++;
                currentList.clear();
            }

            currentList.add(glycemia);
        }

        if (!currentList.isEmpty()){
            entriesAvg.add(new Entry(calcAverage(currentList), xIndex, currentList.size()));
        }

        List<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet dataSetAvg = new LineDataSet(entriesAvg, getString(R.string.carbohydrates_average));
        dataSetAvg.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf(value) + "(" + String.valueOf((int)entry.getData()) + ")";
            }
        });
        dataSetAvg.setColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
        dataSetAvg.setCircleColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
        dataSets.add(dataSetAvg);

        return  new LineData(xAxisValues,dataSets);
    }

    private float calcStandardDeviation(List<CarboidratoIngerido> carboidratosIngeridos) {
        return (float) MathUtils.calcularDesvioPadrao(getValues(carboidratosIngeridos));
    }

    private float calcAverage(List<CarboidratoIngerido> carboidratosIngeridos) {
        return (float) MathUtils.calcularMedia(getValues(carboidratosIngeridos));
    }

    @NonNull
    private List<Double> getValues(List<CarboidratoIngerido> carboidratosIngeridos) {
        List<Double> valores = new ArrayList<>(carboidratosIngeridos.size());
        for (CarboidratoIngerido carboidratoIngerido :
                carboidratosIngeridos) {
            valores.add((double) carboidratoIngerido.getQuantidade());
        }
        return valores;
    }

    private List<CarboidratoIngerido> getSortedCarbohydrates() {
        RegistrosService service = new RegistrosService(getActivity());

        Calendar from = Calendar.getInstance();
        from.add(Calendar.DATE, -daysScope);
        return service.listCarboidratos(from.getTime(), new Date());
    }

    private void initComponents(View view) {
        chart = (LineChart) view.findViewById(R.id.chart);

        chart.setDescription(getString(R.string.chart_carbohydrates_description));
    }
}
