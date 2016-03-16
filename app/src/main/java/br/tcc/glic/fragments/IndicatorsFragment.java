package br.tcc.glic.fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.Indicador;
import br.tcc.glic.domain.enums.QualidadeRegistro;
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

    private ImageView imgDayAvgGlycemiaQuality;
    private ImageView imgWeekAvgGlycemiaQuality;
    private ImageView imgWeekVarGlycemiaQuality;
    private ImageView imgMonthAvgGlycemiaQuality;
    private ImageView imgMonthVarGlycemiaQuality;



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
        IndicadoresService service = new IndicadoresService(getActivity());

        Indicador avgDayGlycemia = service.getIndicador(TipoIndicador.MediaGlicemicaDia);
        Indicador avgWeekGlycemia = service.getIndicador(TipoIndicador.MediaGlicemicaSemana);
        Indicador avgMonthGlycemia = service.getIndicador(TipoIndicador.MediaGlicemicaMes);

        Indicador varWeekGlycemia = service.getIndicador(TipoIndicador.VariabilidadeGlicemiaSemana);
        Indicador varMonthGlycemia = service.getIndicador(TipoIndicador.VariabilidadeGlicemiaMes);

        Indicador avgDayCarbohydrates = service.getIndicador(TipoIndicador.MediaCarboidratosDia);
        Indicador avgWeekCarbohydrates = service.getIndicador(TipoIndicador.MediaCarboidratosSemana);
        Indicador avgMonthCarbohydrates = service.getIndicador(TipoIndicador.MediaCarboidratosMes);

        txtDayAvgGlycemia
                .setText(Double.isNaN(avgDayGlycemia.getValor()) ? "-"
                        : String.valueOf((int) Math.floor(avgDayGlycemia.getValor())));
        txtDayAvgGlycemia.setTextColor(getQualityColor(avgDayGlycemia.getQualidade()));

        imgDayAvgGlycemiaQuality.setImageDrawable(getQualityDrawable(avgDayGlycemia.getQualidade()));
        txtWeekAvgGlycemia
                .setText(Double.isNaN(avgWeekGlycemia.getValor()) ? "-"
                        : String.valueOf((int) Math.floor(avgWeekGlycemia.getValor())));
        txtWeekAvgGlycemia.setTextColor(getQualityColor(avgWeekGlycemia.getQualidade()));
        imgWeekAvgGlycemiaQuality.setImageDrawable(getQualityDrawable(avgWeekGlycemia.getQualidade()));


        txtMonthAvgGlycemia
                .setText(Double.isNaN(avgMonthGlycemia.getValor()) ? "-"
                        : String.valueOf((int) Math.floor(avgMonthGlycemia.getValor())));
        txtMonthAvgGlycemia.setTextColor(getQualityColor(avgMonthGlycemia.getQualidade()));
        imgMonthAvgGlycemiaQuality.setImageDrawable(getQualityDrawable(avgMonthGlycemia.getQualidade()));


        txtWeekVarGlycemia
                .setText(Double.isNaN(varWeekGlycemia.getValor()) ? "-"
                        : String.valueOf((int) Math.floor(varWeekGlycemia.getValor())));
        txtWeekVarGlycemia.setTextColor(getQualityColor(varWeekGlycemia.getQualidade()));
        imgWeekVarGlycemiaQuality.setImageDrawable(getQualityDrawable(varWeekGlycemia.getQualidade()));

        txtMonthVarGlycemia
                .setText(Double.isNaN(varMonthGlycemia.getValor()) ? "-"
                        : String.valueOf((int) Math.floor(varMonthGlycemia.getValor())));
        txtMonthVarGlycemia.setTextColor(getQualityColor(varMonthGlycemia.getQualidade()));
        imgMonthVarGlycemiaQuality.setImageDrawable(getQualityDrawable(varMonthGlycemia.getQualidade()));

        txtDayAvgCarbohydrates
                .setText(Double.isNaN(avgDayCarbohydrates.getValor()) ? "-"
                        : String.valueOf((int)Math.floor(avgDayCarbohydrates.getValor())));
        txtWeekAvgCarbohydrates
                .setText(Double.isNaN(avgWeekCarbohydrates.getValor()) ? "-"
                        : String.valueOf((int)Math.floor(avgWeekCarbohydrates.getValor())));
        txtMonthAvgCarbohydrates
                .setText(Double.isNaN(avgMonthCarbohydrates.getValor()) ? "-"
                        : String.valueOf((int)Math.floor(avgMonthCarbohydrates.getValor())));

        if(Double.isNaN(avgDayCarbohydrates.getValor())
                && Double.isNaN(avgWeekCarbohydrates.getValor())
                && Double.isNaN(avgMonthCarbohydrates.getValor())) {
            containerCarbohydrateIndicators.setVisibility(View.GONE);
        } else {
            containerCarbohydrateIndicators.setVisibility(View.VISIBLE);
        }
    }

    private int getQualityColor(QualidadeRegistro qualidadeRegistro) {
        Context context = getActivity();

        switch (qualidadeRegistro)
        {
            case Baixo:
                return ContextCompat.getColor(context, R.color.colorLow);
            case Bom:
                return ContextCompat.getColor(context, R.color.colorGood);
            case Alto:
                return ContextCompat.getColor(context, R.color.colorHigh);
        }

        throw new RuntimeException("QualidadeRegistro not recognized");
    }

    private Drawable getQualityDrawable(QualidadeRegistro qualidadeRegistro) {
        Context context = getActivity();

        switch (qualidadeRegistro)
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

    private void initComponents(View view) {
        containerCarbohydrateIndicators = view.findViewById(R.id.carbohydrates_indicators_container);

        txtDayAvgGlycemia = (TextView) view.findViewById(R.id.day_average_glycemia_indicator);
        txtWeekAvgGlycemia = (TextView) view.findViewById(R.id.week_average_glycemia_indicator);
        txtMonthAvgGlycemia = (TextView) view.findViewById(R.id.month_average_glycemia_indicator);

        imgDayAvgGlycemiaQuality = (ImageView) view.findViewById(R.id.img_day_average_glycemia_indicator_quality);
        imgWeekAvgGlycemiaQuality = (ImageView) view.findViewById(R.id.img_week_average_glycemia_indicator_quality);
        imgMonthAvgGlycemiaQuality = (ImageView) view.findViewById(R.id.img_month_average_glycemia_indicator_quality);

        txtWeekVarGlycemia = (TextView) view.findViewById(R.id.week_glycemia_variability_indicator);
        txtMonthVarGlycemia = (TextView) view.findViewById(R.id.month_glycemia_variability_indicator);

        imgWeekVarGlycemiaQuality = (ImageView) view.findViewById(R.id.img_week_glycemia_variability_indicator_quality);
        imgMonthVarGlycemiaQuality = (ImageView) view.findViewById(R.id.img_month_glycemia_variability_indicator_quality);

        txtDayAvgCarbohydrates = (TextView) view.findViewById(R.id.day_average_carbohydrates_indicator);
        txtWeekAvgCarbohydrates = (TextView) view.findViewById(R.id.week_average_carbohydrates_indicator);
        txtMonthAvgCarbohydrates = (TextView) view.findViewById(R.id.month_average_carbohydrates_indicator);

        txtDayAvgGlycemia = (TextView) view.findViewById(R.id.day_average_glycemia_indicator);
        txtWeekAvgGlycemia = (TextView) view.findViewById(R.id.week_average_glycemia_indicator);
        txtMonthAvgGlycemia = (TextView) view.findViewById(R.id.month_average_glycemia_indicator);
    }

}
