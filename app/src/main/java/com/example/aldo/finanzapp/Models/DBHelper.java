package com.example.aldo.finanzapp.models;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.aldo.finanzapp.models.BillsDAO.BILL_TABLE_CREATE;
import static com.example.aldo.finanzapp.models.BillsDAO.BILL_TABLE_DROP;

/**
 * Created by Mathieu on 30/08/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "database.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BILL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BILL_TABLE_DROP);
        onCreate(db);
    }
}
