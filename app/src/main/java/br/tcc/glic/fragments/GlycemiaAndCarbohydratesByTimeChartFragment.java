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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.domain.services.AnalizadorRegistros;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.domain.utils.MathUtils;

/**
 * Fragment para exibição de gráfico de glicemias
 * Created by André on 27/03/2016.
 */
public class GlycemiaAndCarbohydratesByTimeChartFragment extends Fragment {
    private LineChart chart;
    private int daysScope = 30;

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public GlycemiaAndCarbohydratesByTimeChartFragment() {
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
        List<Registro> entries = getEntries();
        LineData data = getChartData(entries);
        chart.setData(data);
    }

    private LineData getChartData(List<Registro> entries) {
        List<Entry> glycemiasAvg = new ArrayList<>(entries.size());
        List<Entry> carbohydratesAvg = new ArrayList<>(entries.size());

        AnalizadorRegistros analizadorRegistros = AnalizadorRegistros.getInstance(entries);
        List<Calendar> commonTimes = analizadorRegistros.identificarHorariosDeRegistroComuns();
        List<String> xAxisValues = new ArrayList<>(commonTimes.size());
        Collections.sort(commonTimes, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar lhs, Calendar rhs) {
                return compareTime(lhs, rhs);
            }
        });
        for(Calendar commonTime : commonTimes)
            xAxisValues.add(dateFormat.format(commonTime.getTime()));

        Collections.sort(entries, new Comparator<Registro>() {
            @Override
            public int compare(Registro lhs, Registro rhs) {
                Calendar timeFirst = Calendar.getInstance();
                Calendar timeSecond = Calendar.getInstance();

                timeFirst.setTime(lhs.getHora());
                timeSecond.setTime(rhs.getHora());

                return compareTime(timeFirst, timeSecond);
            }
        });

        int xIndex = 0;
        List<Registro> currentGlycemiaList = new ArrayList<>();
        List<Registro> currentCarbohydratesList = new ArrayList<>();
        for(Registro entry : entries) {
            Calendar xValue = Calendar.getInstance();
            xValue.setTime(entry.getHora());
            int comparation = compareTime(xValue, commonTimes.get(xIndex));
            if(comparation > 0
                    && commonTimes.size() > xIndex + 1
                    && Math.abs(compareTime(commonTimes.get(xIndex + 1), xValue)) < comparation) {

                if (!currentGlycemiaList.isEmpty())
                    glycemiasAvg.add(new Entry((float) MathUtils.calcularMedia(getValores(currentGlycemiaList)),
                            xIndex));

                if (!currentCarbohydratesList.isEmpty())
                    carbohydratesAvg.add(new Entry((float) MathUtils.calcularMedia(getValores(currentCarbohydratesList)),
                            xIndex));

                xIndex++;
                currentGlycemiaList.clear();
                currentCarbohydratesList.clear();
            }

            if(entry instanceof Glicemia)
                currentGlycemiaList.add(entry);
            else
                currentCarbohydratesList.add(entry);
        }

        if (!currentGlycemiaList.isEmpty())
            glycemiasAvg.add(new Entry((float) MathUtils.calcularMedia(getValores(currentGlycemiaList)),
                    xIndex));

        if (!currentCarbohydratesList.isEmpty())
            carbohydratesAvg.add(new Entry((float) MathUtils.calcularMedia(getValores(currentCarbohydratesList)),
                    xIndex));

        List<ILineDataSet> dataSets = new ArrayList<>();

        LineDataSet dataSetGlycemiasAvg = new LineDataSet(glycemiasAvg, getString(R.string.glycemias_average));
        dataSetGlycemiasAvg.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        dataSetGlycemiasAvg.setCircleColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        dataSets.add(dataSetGlycemiasAvg);

        LineDataSet dataSetCarbohydratesAvg = new LineDataSet(carbohydratesAvg, getString(R.string.carbohydrates_average));
        dataSetCarbohydratesAvg.setColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
        dataSetCarbohydratesAvg.setCircleColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
        dataSets.add(dataSetCarbohydratesAvg);

        return  new LineData(xAxisValues,dataSets);
    }

    private Collection<Double> getValores(List<Registro> registros) {
        List<Double> result = new ArrayList<>(registros.size());

        for (Registro registro : registros) {
            if(registro instanceof Glicemia)
                result.add((double) ((Glicemia)registro).getValor());
            else
                result.add((double) ((CarboidratoIngerido)registro).getQuantidade());
        }

        return result;
    }

    private List<Registro> getEntries() {
        RegistrosService service = new RegistrosService(getActivity());

        Calendar from = Calendar.getInstance();
        from.add(Calendar.DATE, -daysScope);
        return service.listGlicemiasECarboidratos(from.getTime(), new Date());
    }

    private int compareTime(Calendar timeFirst, Calendar timeSecond) {
        int hourFirst = timeFirst.get(Calendar.HOUR_OF_DAY);
        int hourSecond = timeSecond.get(Calendar.HOUR_OF_DAY);

        if(hourFirst != hourSecond)
            return hourFirst - hourSecond;

        int minuteFirst = timeFirst.get(Calendar.MINUTE);
        int minuteSecond = timeSecond.get(Calendar.MINUTE);

        return minuteFirst - minuteSecond;
    }

    private void initComponents(View view) {
        chart = (LineChart) view.findViewById(R.id.chart);

        chart.setDescription(getString(R.string.chart_glycemia_and_carbohydrate_by_hour_description));
    }
}
