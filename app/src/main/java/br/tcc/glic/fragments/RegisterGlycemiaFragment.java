package br.tcc.glic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.Glicemia;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterGlycemiaFragment extends Fragment
{
    private EditText edtGlycemia;
    private DateTimeFragment fragmentDateTime;

    public RegisterGlycemiaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_glycemia, container, false);

        if(getArguments() != null && getArguments().getBoolean(getString(R.string.show_date_bundle_argument), false))
            addFragmentDateTime();

        initComponents(view);

        return view;
    }

    private void addFragmentDateTime() {
        fragmentDateTime = new DateTimeFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container_date_time_glycemia, fragmentDateTime)
                .commit();
    }

    private void initComponents(View view) {
        edtGlycemia = (EditText) view.findViewById(R.id.edt_glycemia);
    }

    public Glicemia getGlycemia(){
        Glicemia glicemia = new Glicemia();
        glicemia.setValor(Integer.parseInt(edtGlycemia.getText().toString()));
        if(fragmentDateTime != null)
            glicemia.setHora(new Date(fragmentDateTime.getDateTime().getTimeInMillis()));
        else
            glicemia.setHora(new Date());
        return glicemia;
    }

    public void reset(){
        if(fragmentDateTime != null)
            fragmentDateTime.reset();
        edtGlycemia.setText("");
    }
}
