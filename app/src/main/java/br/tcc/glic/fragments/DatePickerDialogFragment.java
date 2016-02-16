package br.tcc.glic.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import java.util.Calendar;

import br.tcc.glic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerDialogFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener mListener;

    public DatePickerDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            if(getTargetFragment() != null)
                mListener = (DatePickerDialog.OnDateSetListener) getTargetFragment();
            else
                mListener = (DatePickerDialog.OnDateSetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    (getTargetFragment() == null ? activity : getTargetFragment()).toString()
                            + " must implement TimePickerDialog.OnTimeSetListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = (Calendar) getArguments().get(getString(R.string.calendar_bundle_argument));

        if(c == null)
            c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), mListener, year, month, day);
    }
}
