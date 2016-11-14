package com.example.aldo.finanzapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.aldo.finanzapp.R;

/**
 * Created by Mathieu on 29/08/2016.
 */

public class AddExpenseActivity extends AppCompatActivity {

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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // TODO : create fragment to display a calendarView
    public boolean chooseDate(View view){
        return true;
    }
}
