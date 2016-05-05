package br.tcc.glic.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.Indicador;
import br.tcc.glic.domain.enums.EstadoPersonagem;
import br.tcc.glic.domain.enums.QualidadeRegistro;
import br.tcc.glic.domain.enums.TipoIndicador;
import br.tcc.glic.domain.services.EstadoPersonagemService;

/**
 * Created by André on 21/04/2016.
 */
public class CharacterStateDialogFragment extends DialogFragment {

    private ViewGroup firstContainer, secondContainer;
    private TextView txtHowToImprove, txtBut;
    private EstadoPersonagemService estadoPersonagemService;

    public CharacterStateDialogFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_state, container, false);

        initComponents(view);

        return view;
    }

    public void initComponents(View view){
        estadoPersonagemService = new EstadoPersonagemService();

        firstContainer = (ViewGroup) view.findViewById(R.id.first_container);
        secondContainer = (ViewGroup) view.findViewById(R.id.second_container);

        txtHowToImprove = (TextView) view.findViewById(R.id.txt_how_to_improve);
        txtBut = (TextView) view.findViewById(R.id.txt_but);

        if(getArguments() != null) {
            List<Indicador> indicadores =
                    (ArrayList<Indicador>) getArguments().getSerializable(getString(R.string.indicators_argument));
            EstadoPersonagem characterState =
                    (EstadoPersonagem) getArguments().getSerializable(getString(R.string.character_state_argument));

            configureIndications(characterState, indicadores);
            setHowToImproveState(indicadores);
        }
    }

    private void setHowToImproveState(List<Indicador> indicators) {
        int numIndicatorsToImprove = estadoPersonagemService.getNumeroIndicadoresParaMelhorar(indicators);

        if(numIndicatorsToImprove == 0)
            txtHowToImprove.setText(R.string.perfect_character_state_improvement);
        else
            txtHowToImprove.setText(String.format(getString(R.string.how_to_improve_character_state),
                    numIndicatorsToImprove));
    }

    private void configureIndications(EstadoPersonagem characterState, List<Indicador> indicators) {
        ViewGroup positiveContainer, negativeContainer;
        if(characterState == EstadoPersonagem.Mal || characterState == EstadoPersonagem.MuitoMal) {
            positiveContainer = secondContainer;
            negativeContainer = firstContainer;
        } else {
            positiveContainer = firstContainer;
            negativeContainer = secondContainer;
        }

        for(Indicador indicator : indicators){
            if(indicator.getQualidade() == QualidadeRegistro.Bom)
                addIndication(positiveContainer, indicator);
            else
                addIndication(negativeContainer, indicator);
        }

        if(negativeContainer.getChildCount() == 0 || positiveContainer.getChildCount() == 0)
            txtBut.setVisibility(View.GONE);


    }

    private void addIndication(ViewGroup container, Indicador indicator) {
        RelativeLayout indicationContainer = new RelativeLayout(getContext());
        indicationContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        indicationContainer.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.list_border));
        indicationContainer.setPadding(10, 10, 10, 10);

        ImageView imgQuality = new ImageView(getContext());
        imgQuality.setId(R.id.img_quality);
        imgQuality.setImageDrawable(indicator.getQualidade().getDrawable(getContext()));
        RelativeLayout.LayoutParams imgLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        imgLayoutParams.setMargins(5, 0, 15, 0);
        indicationContainer.addView(imgQuality, imgLayoutParams);


        TextView txtIndication = new TextView(getContext());
        txtIndication.setText(String.format(getString(R.string.character_state_indication),
                indicator.getTipo().toString(getContext()), indicator.getQualidade().toString(getContext())));
        RelativeLayout.LayoutParams txtIndicatorLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtIndicatorLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.img_quality);
        indicationContainer.addView(txtIndication, txtIndicatorLayoutParams);

        container.addView(indicationContainer);

        if(indicator.getTipo() == TipoIndicador.MediaGlicemicaDia
                || indicator.getTipo() == TipoIndicador.MediaGlicemicaMes
                || indicator.getTipo() == TipoIndicador.MediaGlicemicaSemana)
        indicationContainer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAverageExplanationDialog();
            }
        });

        if(indicator.getTipo() == TipoIndicador.VariabilidadeGlicemiaMes
                || indicator.getTipo() == TipoIndicador.VariabilidadeGlicemiaSemana)
            indicationContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVariabilityExplanationDialog();
                }
            });
    }

    private void showAverageExplanationDialog() {
        DialogFragment dialog = new IndicatorsExplanationDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.indicators_explanation_argument), IndicatorsExplanationDialogFragment.IndicatorsExplanationMode.Average);
        dialog.setArguments(args);

        dialog.show(getFragmentManager(), null);
    }

    private void showVariabilityExplanationDialog() {
        DialogFragment dialog = new IndicatorsExplanationDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.indicators_explanation_argument), IndicatorsExplanationDialogFragment.IndicatorsExplanationMode.Variability);
        dialog.setArguments(args);

        dialog.show(getFragmentManager(), null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        if(getArguments() != null) {
            EstadoPersonagem characterState =
                    (EstadoPersonagem) getArguments().getSerializable(getString(R.string.character_state_argument));

            setTitle(dialog, characterState);
        }
        return dialog;
    }

    private void setTitle(Dialog dialog, EstadoPersonagem characterState) {
        dialog.setTitle(String.format(getString(R.string.character_state_title), characterState.toString(getContext())));
    }
}
