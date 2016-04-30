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
import java.util.Date;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.Registro;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterGlycemiaFragment extends EditEntryDialogFragment
{
    private EditText edtGlycemia;
    private DateTimeFragment fragmentDateTime;
    private Long currentEntryId = 0l;

    public RegisterGlycemiaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_glycemia, container, false);

        initComponents(view);

        return view;
    }

    private void configureCurrentEntry(Glicemia glicemia) {
        edtGlycemia.setText(String.valueOf(glicemia.getValor()));
        currentEntryId = glicemia.getCodigo();

        if(fragmentDateTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(glicemia.getHora());
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.calendar_bundle_argument), calendar);
            fragmentDateTime.setArguments(bundle);
        }
    }

    private void addFragmentDateTime() {
        fragmentDateTime = new DateTimeFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container_date_time_glycemia, fragmentDateTime)
                .commit();
    }

    @Override
    protected void initComponents(View view) {
        super.initComponents(view);

        edtGlycemia = (EditText) view.findViewById(R.id.edt_glycemia);

        if(getArguments() != null) {
            if (getArguments().getBoolean(getString(R.string.show_date_bundle_argument), false))
                addFragmentDateTime();

            Glicemia glicemia =
                    (Glicemia) getArguments().getSerializable(getString(R.string.entry_bundle_argument));
            if(glicemia != null)
                configureCurrentEntry(glicemia);
        }
    }

    @Override
    public Registro getRegistro() {
        return getGlycemia();
    }

    public Glicemia getGlycemia(){
        String valor = edtGlycemia.getText().toString();
        if(valor.length() == 0)
            return null;

        Glicemia glicemia = new Glicemia();
        if(currentEntryId != 0)
            glicemia.setCodigo(currentEntryId);

        glicemia.setValor(Integer.parseInt(valor));
        if(fragmentDateTime != null)
            glicemia.setHora(fragmentDateTime.getDateTime().getTime());
        else
            glicemia.setHora(new Date());

        return glicemia;
    }

    public void reset(){
        currentEntryId = 0l;
        if(fragmentDateTime != null)
            fragmentDateTime.reset();
        edtGlycemia.setText("");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.glycemia);
        return dialog;
    }
}
