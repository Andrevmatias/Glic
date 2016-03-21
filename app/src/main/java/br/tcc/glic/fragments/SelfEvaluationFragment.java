package br.tcc.glic.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.adapters.FeedbackListAdapter;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.domain.enums.QualidadeRegistro;
import br.tcc.glic.userconfiguration.ConfigUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfEvaluationFragment extends DialogFragment implements View.OnClickListener {
    private TextView txtGlycemia;
    private Button btnLow, btnGood, btnHigh;
    private QualidadeRegistro correctAnswer;
    private ArrayList<Registro> registros;

    public SelfEvaluationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auto_avaliation, container, false);

        initComponents(view);

        return view;
    }

    private void configureCurrentEntry(Glicemia glicemia) {
        correctAnswer = glicemia.getQualidade();
        txtGlycemia.setText(String.valueOf(glicemia.getValor()));
    }

    protected void initComponents(View view) {
        txtGlycemia = (EditText) view.findViewById(R.id.edt_glycemia);

        btnGood = (Button) view.findViewById(R.id.btn_good);
        btnGood.setOnClickListener(this);
        btnLow = (Button) view.findViewById(R.id.btn_low);
        btnLow.setOnClickListener(this);
        btnHigh = (Button) view.findViewById(R.id.btn_high);
        btnHigh.setOnClickListener(this);

        if(getArguments() != null) {
            registros =
                    (ArrayList<Registro>) getArguments().getSerializable(getString(R.string.registered_entries_argument));
            if(registros != null) {
                Glicemia glicemia = null;
                for (Registro registro : registros) {
                    if(registro instanceof Glicemia) {
                        glicemia = (Glicemia) registro;
                        break;
                    }
                }
                if(glicemia != null)
                    configureCurrentEntry(glicemia);
                else
                    showFeedback(registros);
            }
        } else {
            dismiss();
        }
    }

    private void showFeedback(List<Registro> registros) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        FeedbackListAdapter adapter =
                new FeedbackListAdapter(getActivity(), registros);
        builder
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.self_evaluation);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_low:
                evaluateAnswer(QualidadeRegistro.Baixo);
            case R.id.btn_high:
                evaluateAnswer(QualidadeRegistro.Alto);
            case R.id.btn_good:
                evaluateAnswer(QualidadeRegistro.Bom);
        }
    }

    private void evaluateAnswer(QualidadeRegistro answer) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        showFeedback(registros);
                    }
                });

        SharedPreferences userConfigurationFile = ConfigUtils.getUserConfigurationFile(getActivity());
        String min = userConfigurationFile.getString(getString(R.string.min_pre_glycemia_config), "80");
        String max = userConfigurationFile.getString(getString(R.string.max_pre_glycemia_config), "140");

        if(answer == correctAnswer) {
            alertDialogBuilder
                    .setTitle(R.string.self_evaluation_congrats_title)
                    .setIcon(R.drawable.ic_ok)
                    .setMessage(String.format(getString(R.string.self_evaluation_congrats_text),
                            answer.getDescription(getActivity())));
            ConfigUtils.incrementSelfEvaluationCorrectAnswers(getActivity());
        } else {
            alertDialogBuilder
                    .setTitle(R.string.self_evaluation_wrong_answer_title)
                    .setIcon(R.drawable.ic_high)
                    .setMessage(String.format(getString(R.string.self_evaluation_wrong_answer_text),
                            answer.getDescription(getActivity()), correctAnswer.getDescription(getActivity()),
                            min, min, max, max));
        }
        alertDialogBuilder.create().show();

        dismiss();
    }
}
