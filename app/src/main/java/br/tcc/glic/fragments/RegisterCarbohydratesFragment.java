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
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Registro;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCarbohydratesFragment extends EditEntryDialogFragment
{
    private EditText edtCarbohydrates;
    private DateTimeFragment fragmentDateTime;

    private Long currentEntryId;

    public RegisterCarbohydratesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_carbohydrates, container, false);

        addFragmentDateTime();

        initComponents(view);

        return view;
    }

    private void addFragmentDateTime() {
        fragmentDateTime = new DateTimeFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container_date_time_carbohydrates, fragmentDateTime)
                .commit();
    }

    @Override
    protected void initComponents(View view) {
        super.initComponents(view);

        edtCarbohydrates = (EditText) view.findViewById(R.id.edt_carbohydrates);

        if(getArguments() != null) {
            CarboidratoIngerido carboidratoIngerido =
                    (CarboidratoIngerido) getArguments().getSerializable(getString(R.string.entry_bundle_argument));
            if(carboidratoIngerido != null)
                configureCurrentEntry(carboidratoIngerido);
        }
    }

    private void configureCurrentEntry(CarboidratoIngerido carboidratoIngerido) {
        edtCarbohydrates.setText(String.valueOf(carboidratoIngerido.getQuantidade()));
        currentEntryId = carboidratoIngerido.getCodigo();


        if(fragmentDateTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(carboidratoIngerido.getHora());
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.calendar_bundle_argument), calendar);
            fragmentDateTime.setArguments(bundle);
        }
    }

    @Override
    public Registro getRegistro() {
        return getCarbohydrate();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.ingested_carbohydrates);
        return dialog;
    }

    public CarboidratoIngerido getCarbohydrate(){
        String quantidade = edtCarbohydrates.getText().toString();
        if(quantidade.length() == 0)
            return null;

        CarboidratoIngerido carboidratoIngerido = new CarboidratoIngerido();
        carboidratoIngerido.setCodigo(currentEntryId);
        carboidratoIngerido.setQuantidade(Integer.parseInt(quantidade));
        carboidratoIngerido.setHora(fragmentDateTime.getDateTime().getTime());

        return carboidratoIngerido;
    }

    public void reset(){
        currentEntryId = 0l;
        fragmentDateTime.reset();
        edtCarbohydrates.setText("");
    }
}
