package com.example.aldo.finanzapp.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aldo.finanzapp.R;
import com.example.aldo.finanzapp.models.Bills;
import com.example.aldo.finanzapp.models.BillsDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Mathieu on 19/11/2016.
 */

public class GraphFragment extends Fragment implements View.OnClickListener {

    private BillsDAO billsDAO;
    private ArrayList<Bills> billsList;
    private GraphView graphView;
    private  static String period = "15";
    private BarGraphSeries<DataPoint> series;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_view, container, false);
        graphView = (GraphView) view.findViewById(R.id.graph);
        Button button15j = (Button) view.findViewById(R.id.button_15j);
        Button button30j = (Button) view.findViewById(R.id.button_30j);
        button15j.setOnClickListener(this);
        button30j.setOnClickListener(this);
        billsDAO = new BillsDAO(getActivity());
        initGraph(graphView,period);

        return view;
    }

    public void initGraph(GraphView graph, String period){
        int amount;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date dateNow = calendar.getTime();
        calendar.add(Calendar.DATE, Integer.valueOf(period));
        Date dateEndOfPeriod = calendar.getTime();

        billsList = billsDAO.getBills(period);

        HashMap<Date,Integer> points = new HashMap<Date, Integer>();
        for (Iterator<Bills> it = billsList.iterator(); it.hasNext();){
            Bills itg = it.next();
            Date key  = null;
            try {
                key = formatter.parse(itg.getFinishDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (points.containsKey(key)){
                amount = points.get(key) + Integer.parseInt(itg.getAmount());
                points.put(key,amount);
            }
            else{
                amount = Integer.parseInt(itg.getAmount());
                points.put(key, amount);
            }
        }

        DataPoint[] dataPoints = new DataPoint[points.size()];
        int i = 0;
        for (Iterator<Date> it = points.keySet().iterator(); it.hasNext();){
            Date key = it.next();
            dataPoints[i] = new DataPoint(key,points.get(key));
            i++;
        }

        series = new BarGraphSeries<DataPoint>(dataPoints);

        series.setSpacing(20);
        series.setAnimated(true);
        series.setColor(Color.rgb(8,174,158));

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);

        // set manual x and y bounds to have nice steps
        graph.getViewport().setMinX(dateNow.getTime());
        graph.getViewport().setMaxX(dateEndOfPeriod.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setYAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not nessecary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_15j :
                graphView.removeAllSeries();
                period = "15";
                initGraph(graphView, "15");
                Log.d("button ", " 15j");
                break;
            case R.id.button_30j :
                graphView.removeAllSeries();
                graphView.onDataChanged(true,true);
                period  = "30";
                initGraph(graphView,"30");
                Log.d("button ", " 30j");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        graphView.removeAllSeries();
    }

}
