package br.tcc.glic.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import br.tcc.glic.R;
import br.tcc.glic.dialogs.EditEntryDialogListener;
import br.tcc.glic.domain.core.Registro;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class EditEntryDialogFragment extends DialogFragment
{
    private ImageView btnCancel;
    private ImageView btnApply;
    private ImageView btnDelete;

    private EditEntryDialogListener listener;

    public EditEntryDialogFragment() {
        // Required empty public constructor
    }


    protected void initComponents(View view) {
        btnCancel = (ImageView) view.findViewById(R.id.btn_cancel);
        btnApply = (ImageView) view.findViewById(R.id.btn_apply_changes);
        btnDelete = (ImageView) view.findViewById(R.id.btn_delete);

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
            View buttonsContainer = view.findViewById(R.id.container_buttons_edit_dialog);
            buttonsContainer.setVisibility(View.GONE);
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
                            listener.onDelete(getRegistro());
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
            listener.onApply(getRegistro());
        dismiss();
    }

    public abstract Registro getRegistro();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof EditEntryDialogListener)
            listener = (EditEntryDialogListener) activity;
    }
}
