package br.tcc.glic.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.Calendar;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.domain.core.TipoInsulina;
import br.tcc.glic.domain.services.TipoInsulinaService;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterInsulinFragment extends EditEntryDialogFragment
{
    private EditText edtInsulin;
    private Spinner spnInsulinType;
    private DateTimeFragment fragmentDateTime;

    private Long currentEntryId;

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
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container_date_time_insulin, fragmentDateTime)
                .commit();
    }

    protected void initComponents(View view) {
        super.initComponents(view);

        edtInsulin = (EditText) view.findViewById(R.id.edt_insulin);
        spnInsulinType = (Spinner) view.findViewById(R.id.spn_insulin_type);
        SpinnerAdapter insulinTypeAdapter =
                new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        new TipoInsulinaService().getTiposInsulina());
        spnInsulinType.setAdapter(insulinTypeAdapter);

        if(getArguments() != null) {
            AplicacaoInsulina aplicacaoInsulina =
                    (AplicacaoInsulina) getArguments().getSerializable(getString(R.string.entry_bundle_argument));
            if(aplicacaoInsulina != null)
                configureCurrentEntry(aplicacaoInsulina);
        }
    }

    private void configureCurrentEntry(AplicacaoInsulina aplicacaoInsulina) {
        edtInsulin.setText(String.valueOf(aplicacaoInsulina.getQuantidade()));
        spnInsulinType.setSelection(getInsulinTypePosition(aplicacaoInsulina.getTipo()));
        currentEntryId = aplicacaoInsulina.getCodigo();

        if(fragmentDateTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(aplicacaoInsulina.getHora());
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.calendar_bundle_argument), calendar);
            fragmentDateTime.setArguments(bundle);
        }

    }

    private int getInsulinTypePosition(TipoInsulina tipo) {
        Long codigo = tipo.getCodigo();
        for(int i = 0; i < spnInsulinType.getCount(); i++) {
            TipoInsulina item = (TipoInsulina) spnInsulinType.getAdapter().getItem(i);
            if(item.getCodigo().equals(codigo))
                return i;
        }

        return 0;
    }

    @Override
    public Registro getRegistro() {
        return getInsulin();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.injected_insulin);
        return dialog;
    }

    public AplicacaoInsulina getInsulin(){
        String valor = edtInsulin.getText().toString();
        if(valor.length() == 0)
            return null;

        AplicacaoInsulina aplicacaoInsulina = new AplicacaoInsulina();
        aplicacaoInsulina.setCodigo(currentEntryId);
        aplicacaoInsulina.setQuantidade(Double.parseDouble(valor));
        aplicacaoInsulina.setHora(fragmentDateTime.getDateTime().getTime());
        aplicacaoInsulina.setTipo((TipoInsulina)spnInsulinType.getSelectedItem());

        return aplicacaoInsulina;
    }

    public void reset(){
        currentEntryId = 0l;
        if(fragmentDateTime != null)
            fragmentDateTime.reset();
        edtInsulin.setText("");
        spnInsulinType.setSelection(0);
    }
}
