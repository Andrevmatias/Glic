package br.tcc.glic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.HemoglobinaGlicada;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterHbA1cFragment extends Fragment
{
    private EditText edtHbA1c;
    private DateTimeFragment fragmentDateTime;

    public RegisterHbA1cFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_hba1c, container, false);

        addFragmentDateTime();

        initComponents(view);

        return view;
    }

    private void addFragmentDateTime() {
        fragmentDateTime = new DateTimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.date_time_picker_type_argument), DateTimeFragment.DateType.DateOnly);
        fragmentDateTime.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container_date_time_hba1c, fragmentDateTime)
                .commit();
    }

    private void initComponents(View view) {
        edtHbA1c = (EditText) view.findViewById(R.id.edt_hba1c);
    }

    public HemoglobinaGlicada getHbA1c(){
        String valor = edtHbA1c.getText().toString();
        if(valor.length() == 0)
            return null;

        HemoglobinaGlicada hba1c = new HemoglobinaGlicada();
        hba1c.setValor(Double.parseDouble(valor));
        hba1c.setHora(fragmentDateTime.getDateTime().getTime());

        return hba1c;
    }

    public void reset(){
        if(fragmentDateTime != null)
            fragmentDateTime.reset();
        edtHbA1c.setText("");
    }
}
