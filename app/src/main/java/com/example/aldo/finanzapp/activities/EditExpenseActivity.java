package com.example.aldo.finanzapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aldo.finanzapp.R;
import com.example.aldo.finanzapp.models.Bills;
import com.example.aldo.finanzapp.models.BillsDAO;

/**
 * Created by Mathieu on 29/08/2016.
 */

public class EditExpenseActivity extends AppCompatActivity {

    private BillsDAO billsDAO;
    private Bills bills;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //creation of the view to add an expense
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_add_expense);
        android.support.v7.widget.Toolbar toolbar_expense = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_expense);
        setSupportActionBar(toolbar_expense);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewTitleToolbar = (TextView) findViewById(R.id.toolbar_title);
        textViewTitleToolbar.setText("Modificar cuenta");
    }
}

