package com.example.aldo.finanzapp.activities;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aldo.finanzapp.Fragments.DatePickerFragment;
import com.example.aldo.finanzapp.R;
import com.example.aldo.finanzapp.Models.Bills;
import com.example.aldo.finanzapp.Models.BillsDAO;

/**
 * Created by Mathieu on 29/08/2016.
 */

public class AddExpenseActivity extends AppCompatActivity {

    private BillsDAO billsDAO;

    @Override
    public void onCreate(Bundle savedInstanceState){
        //creation of the view to add an expense
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_add_expense);
        android.support.v7.widget.Toolbar toolbar_expense = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_expense);
        setSupportActionBar(toolbar_expense);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.activity_add_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if (id == R.id.nav_valid){
            EditText editTitle = (EditText) findViewById(R.id.input_text_expense_title);
            String title = editTitle.getText().toString();
            EditText editAmount = (EditText) findViewById(R.id.input_text_amount);
            String amount = editAmount.getText().toString();
            EditText editDescription = (EditText) findViewById(R.id.input_text_description);
            String description = editDescription.getText().toString();

            // If the title is empty
            if (title.equals("")){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, " entrar un titulo ", duration);
                toast.show();
            }
            else{
                billsDAO = new BillsDAO(this);
                billsDAO.open();
                Button finishDateButton = (Button) findViewById(R.id.button_calendar2);
                Bills bills = new Bills(title,amount, finishDateButton.getText().toString(), description);
                billsDAO.createBill(bills);
                Intent intent = new Intent(AddExpenseActivity.this,ListarCuentas.class);
                startActivity(intent);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // TODO : create fragment to display a calendarView

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");


        //calendarButton.setText(DialogFragment.);
    }
}
