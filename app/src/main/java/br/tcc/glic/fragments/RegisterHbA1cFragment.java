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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.Glicemia;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterHbA1cFragment extends Fragment
    implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{
    private EditText edtGlycemia;
    private ImageButton btnExamTime;
    private Calendar examDate = Calendar.getInstance();

    public RegisterHbA1cFragment() {
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

    private void initComponents(View view) {
        edtGlycemia = (EditText) view.findViewById(R.id.edt_glycemia);

    }

    private void pickExamDate() {
        DialogFragment datePicker = new DatePickerDialogFragment();
        datePicker.setTargetFragment(this, 0);
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.calendar_bundle_argument), examDate);
        datePicker.setArguments(args);
        datePicker.show(getFragmentManager(), null);
    }

    private void pickExamTime() {
        DialogFragment timePicker = new TimePickerDialogFragment();
        timePicker.setTargetFragment(this, 0);
        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.calendar_bundle_argument), examDate);
        timePicker.setArguments(args);
        timePicker.show(getFragmentManager(), null);
    }

    public Glicemia getGlycemia(){
        Glicemia glicemia = new Glicemia();
        glicemia.setValor(Integer.parseInt(edtGlycemia.getText().toString()));
        glicemia.setHora(new Date(examDate.getTimeInMillis()));
        return glicemia;
    }

    public void reset(){
        examDate = Calendar.getInstance();
        edtGlycemia.setText("");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        examDate.set(Calendar.YEAR, year);
        examDate.set(Calendar.MONTH, monthOfYear);
        examDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        pickExamTime();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        examDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        examDate.set(Calendar.MINUTE, minute);
    }
}
