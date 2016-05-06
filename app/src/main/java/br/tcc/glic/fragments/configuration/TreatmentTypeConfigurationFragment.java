package br.tcc.glic.fragments.configuration;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import br.tcc.glic.R;
import br.tcc.glic.domain.enums.TipoTerapia;

/**
 * A simple {@link Fragment} subclass.
 */
public class TreatmentTypeConfigurationFragment extends Fragment {

    private OnConfigurationSelectedListener selectionListener;
    private ToggleButton btnConventional;
    private ToggleButton btnIntensive;

    public TreatmentTypeConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_treatment_type_configuration, container, false);

        initComponents(fragment);

        return fragment;
    }

    private void initComponents(View fragment) {
        btnIntensive = (ToggleButton) fragment.findViewById(R.id.btn_select_intensive);
        btnConventional = (ToggleButton) fragment.findViewById(R.id.btn_select_conventional);
        Button btnContinue = (Button) fragment.findViewById(R.id.btn_treatment_type_configuration_continue);

        btnIntensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConventional.setChecked(false);
                btnIntensive.setChecked(true);
            }
        });

        btnConventional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIntensive.setChecked(false);
                btnConventional.setChecked(true);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifySelection();
            }
        });
    }

    private void notifySelection() {
        if(selectionListener != null)
        {
            TipoTerapia tipoTerapia =
                    btnIntensive.isChecked() ? TipoTerapia.Intensiva : TipoTerapia.Convencional;

            selectionListener.onConfigurationSelected(getString(R.string.therapy_type_config), tipoTerapia);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            selectionListener = (OnConfigurationSelectedListener) activity;
        } catch (ClassCastException e) {
            //Just ignore
        }
    }
}
