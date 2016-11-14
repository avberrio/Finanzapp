package com.example.aldo.finanzapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.aldo.finanzapp.Models.Bills;
import com.example.aldo.finanzapp.Models.BillsDAO;
import com.example.aldo.finanzapp.Models.MyClassAdapter;
import com.example.aldo.finanzapp.R;

import java.util.ArrayList;

public class ListarCuentas extends AppCompatActivity {
    ArrayList<Bills> billsList;
    MyClassAdapter newAdapter;
    BillsDAO billsDAO;
    Bills bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cuentas);
        android.support.v7.widget.Toolbar toolbar_list = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*
        billsList = new ArrayList<Bills>();
        billsList.add(new Bills("Electricidad", 20000, "Viernes 28 de Octubre de 2016"));
        billsList.add(new Bills("Agua", 15758, "Sabado 30 de Octubre de 2016"));
        billsList.add(new Bills("Arriendo", 200000, "Viernes 4 de Noviembre de 2016"));
        billsList.add(new Bills("Plan de Teléfono", 15990, "Viernes 11 de Noviembre de 2016"));
        billsList.add(new Bills("Otros", 20000, "Viernes 28 de Octubre de 2016"));
        billsList.add(new Bills("Más", 15758, "Sabado 30 de Octubre de 2016"));
        */
        bill = new Bills("Más", "15758", "Sabado 30 de Octubre de 2016", "Hola mundo");
        billsDAO = new BillsDAO(this);
        billsDAO.open();
        billsDAO.createBill(bill);
        billsList = billsDAO.getAllTasks();

        newAdapter = new MyClassAdapter(this, billsList);
        ListView listView = (ListView) findViewById(R.id.myListView);
        listView.setAdapter(newAdapter);
        newAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


}
