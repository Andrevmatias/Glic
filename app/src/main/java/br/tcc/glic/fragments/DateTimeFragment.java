package br.tcc.glic.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.tcc.glic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateTimeFragment extends Fragment
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private ImageButton btnPick;
    private TextView txtCurrentDateTime;

    private Calendar currentDate;
    private DateType type = DateType.DateTime;
    private DateFormat dateFormat;

    public DateTimeFragment() {
        setDateTimeFormat(type);
    }

    public Calendar getDateTime(){
        return currentDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        configure(savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_time, container, false);

        initComponents(view);

        return view;
    }

    private void configure(Bundle savedInstanceState) {
        if(savedInstanceState != null)
            currentDate = (Calendar) savedInstanceState.get(getString(R.string.calendar_bundle_argument));

        if(currentDate == null)
            currentDate = Calendar.getInstance();

        String type_key = getString(R.string.date_time_picker_type_argument);
        if(savedInstanceState != null && savedInstanceState.containsKey(type_key))
            type = (DateType) savedInstanceState.get(type_key);

        setDateTimeFormat(type);
    }

    private void setDateTimeFormat(DateType type) {
        switch (type){
            case DateTime:
                dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                break;
            case DateOnly:
                dateFormat = new SimpleDateFormat("dd/MM/yy");
                break;
            case TimeOnly:
                dateFormat = new SimpleDateFormat("HH:mm");
                break;
        }
    }

    private void initComponents(View view) {
        btnPick = (ImageButton) view.findViewById(R.id.btn_pick_date_time);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != DateType.TimeOnly)
                    pickDate();
                else
                    pickTime();
            }
        });

        txtCurrentDateTime = (TextView) view.findViewById(R.id.current_date_time);
        showCurrentDate();
    }

    private void pickDate() {
        DialogFragment datePicker = new DatePickerDialogFragment();
        datePicker.setTargetFragment(this, 0);
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.calendar_bundle_argument), currentDate);
        datePicker.setArguments(args);
        datePicker.show(getFragmentManager(), null);
    }

    private void pickTime() {
        DialogFragment timePicker = new TimePickerDialogFragment();
        timePicker.setTargetFragment(this, 0);
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.calendar_bundle_argument), currentDate);
        timePicker.setArguments(args);
        timePicker.show(getFragmentManager(), null);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        currentDate.set(Calendar.YEAR, year);
        currentDate.set(Calendar.MONTH, monthOfYear);
        currentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        showCurrentDate();

        if(type != DateType.DateOnly)
            pickTime();
    }

    private void showCurrentDate() {
        txtCurrentDateTime.setText(dateFormat.format(currentDate.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        currentDate.set(Calendar.MINUTE, minute);
        showCurrentDate();
    }

    public void reset() {
        currentDate = Calendar.getInstance();
    }

    public enum DateType {
        DateTime,
        DateOnly,
        TimeOnly
    }
}
