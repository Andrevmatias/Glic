package br.tcc.glic.fragments.configuration;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.tcc.glic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgeConfigurationFragment extends Fragment {


    private OnConfigurationSelectedListener selectionListener;
    private EditText edtAge;

    public AgeConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment =  inflater.inflate(R.layout.fragment_age_configuration, container, false);

        initComponents(fragment);

        return fragment;
    }

    private void initComponents(View fragment) {
        edtAge = (EditText) fragment.findViewById(R.id.edt_age_configuration);

        Button btnSave = (Button) fragment.findViewById(R.id.btn_age_configuration_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifySelection();
            }
        });
    }

    private void notifySelection() {
        if(selectionListener != null)
        {
            selectionListener.onConfigurationSelected(getString(R.string.age_config),
                    Integer.parseInt(edtAge.getText().toString()));
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
