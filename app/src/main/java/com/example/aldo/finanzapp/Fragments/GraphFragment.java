package com.example.aldo.finanzapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aldo.finanzapp.R;
import com.example.aldo.finanzapp.models.Bills;
import com.example.aldo.finanzapp.models.BillsDAO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Mathieu on 19/11/2016.
 */

public class GraphFragment extends Fragment {

    private BillsDAO billsDAO;
    private ArrayList<Bills> billsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_view, container, false);
        GraphView graphView = (GraphView) view.findViewById(R.id.graph);
        initGraph(graphView);
        return view;
    }


    public void initGraph(GraphView graph){
        int amount;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        Log.d("Date", "d1 = " +d1+ " d2 = "+d2+" d3 = "+d3);
        billsDAO = new BillsDAO(getActivity());
        String period = "15";
        billsList = billsDAO.getBills(period);

        HashMap<Date,Integer> points = new HashMap<Date,Integer>();
        for (Iterator<Bills> it = billsList.iterator(); it.hasNext();){
            Bills itg = it.next();
            Date key  = null;
            try {
                Log.d("date ", itg.getFinishDate());
                key = formatter.parse(itg.getFinishDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (points.containsKey(key)){
                amount = points.get(key) + Integer.parseInt(itg.getAmount());
                points.put(key,amount);
                Log.d("cle existe ","cle : " +key + " amount " + amount);
            }
            else{
                amount = Integer.parseInt(itg.getAmount());
                points.put(key, amount);
                Log.d("cle existe pas ","cle : " +key + " amount " + amount);
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(d1, 200),
                new DataPoint(d2, 400),
                new DataPoint(d3, 1000),
        });
        series.setDrawBackground(true);
        series.setAnimated(true);
        series.setDrawDataPoints(true);

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);

        // set manual x and y bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setYAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not nessecary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }

}
