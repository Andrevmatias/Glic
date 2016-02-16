package br.tcc.glic.fragments;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;

import java.util.Calendar;

import br.tcc.glic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerDialogFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener mListener;

    public TimePickerDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            if(getTargetFragment() != null)
                mListener = (TimePickerDialog.OnTimeSetListener) getTargetFragment();
            else
                mListener = (TimePickerDialog.OnTimeSetListener) activity;
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

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), mListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
