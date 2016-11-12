package com.example.aldo.finanzapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyClassAdapter extends ArrayAdapter {

    private ArrayList<Bills> items;

    public MyClassAdapter(Context context, ArrayList<Bills> items){
        super(context, R.layout.list_item, items);
        this.items = items;
    }

    public void addAdapterItem(Bills item) {
        items.add(item);
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        //View rowView = getLayoutInflater().inflate(R.layout.activity_listar_cuentas);
        TextView billNameLabel = (TextView) rowView.findViewById(R.id.billsName);
        billNameLabel.setText("Nombre: " + items.get(position).getBillName());

        TextView billValueLabel = (TextView) rowView.findViewById(R.id.value);
        billValueLabel.setText("Valor: $" + Integer.toString(items.get(position).getValue()));

        TextView billFinisDateLabel = (TextView) rowView.findViewById(R.id.finish_date);
        billFinisDateLabel.setText("Fecha: " + items.get(position).getFinishDate());
        // do the same with second and third
        return rowView;
    }


}