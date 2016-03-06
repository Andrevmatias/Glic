package br.tcc.glic.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Date;

import br.tcc.glic.R;
import br.tcc.glic.dialogs.EditEntryDialogListener;
import br.tcc.glic.domain.core.Glicemia;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterGlycemiaFragment extends DialogFragment
{
    private EditText edtGlycemia;
    private DateTimeFragment fragmentDateTime;
    private Long currentEntryId = 0l;
    private ImageView btnCancel;
    private ImageView btnApply;
    private ImageView btnDelete;
    private EditEntryDialogListener listener;

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

    private void initComponents(View view) {
        edtGlycemia = (EditText) view.findViewById(R.id.edt_glycemia);
        btnCancel = (ImageView) view.findViewById(R.id.btn_cancel_glycemia);
        btnApply = (ImageView) view.findViewById(R.id.btn_apply_changes_glycemia);
        btnDelete = (ImageView) view.findViewById(R.id.btn_delete_glycemia);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyApply();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDelete();
            }
        });

        if(!getShowsDialog()) {
            View buttonsContainer = view.findViewById(R.id.container_buttons_dialog_glycemia);
            buttonsContainer.setVisibility(View.GONE);
        }

        if(getArguments() != null) {
            if (getArguments().getBoolean(getString(R.string.show_date_bundle_argument), false))
                addFragmentDateTime();

            Glicemia glicemia =
                    (Glicemia) getArguments().getSerializable(getString(R.string.entry_bundle_argument));
            if(glicemia != null)
                configureCurrentEntry(glicemia);
        }
    }

    private void notifyDelete() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete)
                .setMessage(R.string.are_you_sure_delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener != null)
                            listener.onDelete(getGlycemia());
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .create()
                .show();
    }

    private void notifyApply() {
        if(listener != null)
            listener.onApply(getGlycemia());
        dismiss();
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
        if(fragmentDateTime != null)
            fragmentDateTime.reset();
        edtGlycemia.setText("");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.glycemia);
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof EditEntryDialogListener)
            listener = (EditEntryDialogListener) activity;
    }
}
