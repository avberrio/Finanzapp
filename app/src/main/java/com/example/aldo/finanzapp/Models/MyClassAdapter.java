package com.example.aldo.finanzapp.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.ArrayAdapter;

import com.example.aldo.finanzapp.Models.Bills;
import com.example.aldo.finanzapp.R;

import java.util.ArrayList;

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
        billValueLabel.setText("Valor: $" + items.get(position).getValue());

        TextView billFinisDateLabel = (TextView) rowView.findViewById(R.id.finish_date);
        billFinisDateLabel.setText("Fecha: " + items.get(position).getFinishDate());
        // do the same with second and third
        return rowView;
    }


}