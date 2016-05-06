package br.tcc.glic.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.tcc.glic.R;
import br.tcc.glic.userconfiguration.ConfigUtils;

/**
 * Created by André on 21/04/2016.
 */
public class IndicatorsExplanationDialogFragment extends DialogFragment {

    private ImageView imgExplanation;
    private TextView txtAverage, txtVariability;
    private Button btnDispose;

    public IndicatorsExplanationDialogFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indicators_explanation, container, false);

        initComponents(view);

        return view;
    }

    public void initComponents(View view){
        imgExplanation = (ImageView) view.findViewById(R.id.img_indicators_explanation);
        txtAverage = (TextView) view.findViewById(R.id.txt_average_explanation);
        txtVariability = (TextView) view.findViewById(R.id.txt_variability_explanation);
        btnDispose = (Button) view.findViewById(R.id.btn_dismiss);
        btnDispose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        IndicatorsExplanationMode mode;
        if(getArguments() != null) {
            mode = (IndicatorsExplanationMode) getArguments()
                            .getSerializable(getString(R.string.indicators_explanation_argument));
        } else {
            mode = IndicatorsExplanationMode.Complete;
        }

        configureExplanations(mode);
    }

    private void configureExplanations(IndicatorsExplanationMode mode) {
        switch (mode){
            case Variability:
                imgExplanation.setVisibility(View.GONE);
                txtAverage.setVisibility(View.GONE);
                txtVariability.setVisibility(View.VISIBLE);
                break;
            case Average:
                imgExplanation.setVisibility(View.GONE);
                txtVariability.setVisibility(View.GONE);
                txtAverage.setVisibility(View.VISIBLE);
                initAverageText();
                break;
            default:
                imgExplanation.setVisibility(View.VISIBLE);
                txtAverage.setVisibility(View.VISIBLE);
                txtVariability.setVisibility(View.VISIBLE);
                initAverageText();
                break;
        }
    }

    private void initAverageText() {
        SharedPreferences userConfigurationFile = ConfigUtils.getUserConfigurationFile(getActivity());
        String min = userConfigurationFile.getString(getString(R.string.min_pre_glycemia_config), "80");
        String max = userConfigurationFile.getString(getString(R.string.max_pre_glycemia_config), "140");

        //noinspection ResourceType
        String text = String.format(getString(R.string.average_indicator_explanation),
                min,
                max,
                "#" + getString(R.color.colorLow).substring(3),
                "#" + getString(R.color.colorGood).substring(3),
                "#" + getString(R.color.colorHigh).substring(3));

        txtAverage.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(getString(R.string.indicators_explanation_title));
        return dialog;
    }

    public enum IndicatorsExplanationMode {
        Complete,
        Variability,
        Average
    }
}
