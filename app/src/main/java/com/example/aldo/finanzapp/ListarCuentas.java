package com.example.aldo.finanzapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListarCuentas extends AppCompatActivity {
    ArrayList<Bills> billsList;
    MyClassAdapter newAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cuentas);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);   /* Back button in left of action bar */
        setSupportActionBar(myToolbar);

        billsList = new ArrayList<Bills>();
        billsList.add(new Bills("Electricidad", 20000, "Viernes 28 de Octubre de 2016"));
        billsList.add(new Bills("Agua", 15758, "Sabado 30 de Octubre de 2016"));
        billsList.add(new Bills("Arriendo", 200000, "Viernes 4 de Noviembre de 2016"));
        billsList.add(new Bills("Plan de Teléfono", 15990, "Viernes 11 de Noviembre de 2016"));
        billsList.add(new Bills("Otros", 20000, "Viernes 28 de Octubre de 2016"));
        billsList.add(new Bills("Más", 15758, "Sabado 30 de Octubre de 2016"));

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
