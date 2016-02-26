package br.tcc.glic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.TipoInsulina;
import br.tcc.glic.domain.services.TipoInsulinaService;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterInsulinFragment extends Fragment
{
    private EditText edtInsulin;
    private Spinner spnInsulinType;
    private DateTimeFragment fragmentDateTime;

    public RegisterInsulinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_insulin, container, false);

        addFragmentDateTime();

        initComponents(view);

        return view;
    }

    private void addFragmentDateTime() {
        fragmentDateTime = new DateTimeFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container_date_time_insulin, fragmentDateTime)
                .commit();
    }

    private void initComponents(View view) {
        edtInsulin = (EditText) view.findViewById(R.id.edt_insulin);
        spnInsulinType = (Spinner) view.findViewById(R.id.spn_insulin_type);
        SpinnerAdapter insulinTypeAdapter =
                new ArrayAdapter<TipoInsulina>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        new TipoInsulinaService().getTiposInsulina());
        spnInsulinType.setAdapter(insulinTypeAdapter);
    }

    public AplicacaoInsulina getInsulin(){
        String valor = edtInsulin.getText().toString();
        if(valor.length() == 0)
            return null;

        AplicacaoInsulina aplicacaoInsulina = new AplicacaoInsulina();
        aplicacaoInsulina.setQuantidade(Double.parseDouble(valor));
        aplicacaoInsulina.setHora(fragmentDateTime.getDateTime().getTime());
        aplicacaoInsulina.setTipo((TipoInsulina)spnInsulinType.getSelectedItem());

        return aplicacaoInsulina;
    }

    public void reset(){
        if(fragmentDateTime != null)
            fragmentDateTime.reset();
        edtInsulin.setText("");
        spnInsulinType.setSelection(0);
    }
}
