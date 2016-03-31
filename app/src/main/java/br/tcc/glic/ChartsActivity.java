package br.tcc.glic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.tcc.glic.fragments.ChooseChartFragment;
import br.tcc.glic.fragments.GlycemiaByTimeChartFragment;
import br.tcc.glic.fragments.GlycemiaChartFragment;

public class ChartsActivity extends AppCompatActivity
    implements ChooseChartFragment.ChartChosenListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        showChooseChartFragment();
    }

    private void showChooseChartFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.charts_container, new ChooseChartFragment())
                .commit();
    }

    @Override
    public void onChartChosen(ChooseChartFragment.ChartType type) {
        switch (type)
        {
            case Glycemia:
                showGlycemiaChart();
                break;
            case GlycemiaByHour:
                showGlycemiaByHouChart();
                break;
            case Carbohydrates:
                showCarbohydratesChart();
                break;
        }
    }

    private void showCarbohydratesChart() {

    }

    private void showGlycemiaByHouChart() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.charts_container, new GlycemiaByTimeChartFragment())
                .commit();
    }

    private void showGlycemiaChart() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.charts_container, new GlycemiaChartFragment())
                .commit();
    }
}
