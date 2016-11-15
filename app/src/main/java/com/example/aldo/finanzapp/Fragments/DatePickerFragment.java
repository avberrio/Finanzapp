package com.example.aldo.finanzapp.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ShareCompat;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.aldo.finanzapp.R;

import java.util.Calendar;

/**
 * Created by aldo on 14-11-16.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Button activityButton = (Button)getActivity().findViewById(R.id.button_calendar2);
        String stringDay = Integer.toString(day);
        String stringMonth = Integer.toString(month);
        String stringYear = Integer.toString(year);
        activityButton.setText(stringDay + "/" + stringMonth + "/" + stringYear);
    }
}