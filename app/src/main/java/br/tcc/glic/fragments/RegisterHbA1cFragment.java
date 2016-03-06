package br.tcc.glic.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Calendar;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.core.Registro;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterHbA1cFragment extends EditEntryDialogFragment
{
    private EditText edtHbA1c;
    private DateTimeFragment fragmentDateTime;

    private Long currentEntryId;

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
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container_date_time_hba1c, fragmentDateTime)
                .commit();
    }

    @Override
    protected void initComponents(View view) {
        super.initComponents(view);

        edtHbA1c = (EditText) view.findViewById(R.id.edt_hba1c);

        if(getArguments() != null) {
            HemoglobinaGlicada hba1c =
                    (HemoglobinaGlicada) getArguments().getSerializable(getString(R.string.entry_bundle_argument));
            if(hba1c != null)
                configureCurrentEntry(hba1c);
        }
    }

    private void configureCurrentEntry(HemoglobinaGlicada hemoglobinaGlicada) {
        edtHbA1c.setText(String.valueOf(hemoglobinaGlicada.getValor()));
        currentEntryId = hemoglobinaGlicada.getCodigo();

        if(fragmentDateTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(hemoglobinaGlicada.getHora());
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.calendar_bundle_argument), calendar);
            fragmentDateTime.setArguments(bundle);
        }

    }

    @Override
    public Registro getRegistro() {
        return getHbA1c();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.hba1c);
        return dialog;
    }

    public HemoglobinaGlicada getHbA1c(){
        String valor = edtHbA1c.getText().toString();
        if(valor.length() == 0)
            return null;

        HemoglobinaGlicada hba1c = new HemoglobinaGlicada();
        hba1c.setCodigo(currentEntryId);
        hba1c.setValor(Double.parseDouble(valor));
        hba1c.setHora(fragmentDateTime.getDateTime().getTime());

        return hba1c;
    }

    public void reset(){
        currentEntryId = 0l;
        if(fragmentDateTime != null)
            fragmentDateTime.reset();
        edtHbA1c.setText("");
    }
}
