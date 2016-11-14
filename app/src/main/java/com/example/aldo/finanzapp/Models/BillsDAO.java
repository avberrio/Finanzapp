package com.example.aldo.finanzapp.models;

/**
 * Created by aldo on 13-11-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Mathieu on 30/08/2016.
 */

public class BillsDAO {
    public static final String BILL_TABLE_NAME = "bill";
    public static final String KEY = "id";
    public static final String BILLS_NAME = "billsName";
    public static final String VALUE = "value";
    public static final String FINISH_DATE = "finishDate";
    public static final String DESCRIPTION = "description";
    public static final String UPDATE_STATUS = "updateStatus";


    public static final String BILL_TABLE_CREATE = "CREATE TABLE " + BILL_TABLE_NAME + "(" + KEY +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + BILLS_NAME + " TEXT, " + VALUE + " INTEGER, " +
            FINISH_DATE + " TEXT, " + DESCRIPTION + " TEXT);";

    public static final String BILL_TABLE_DROP = "DROP TABLE IF EXISTS " + BILL_TABLE_NAME + ";";

    private String[] allColumns = {KEY, BILLS_NAME, VALUE, FINISH_DATE, DESCRIPTION};

    protected DBHelper dBHelper = null;
    protected SQLiteDatabase mDb = null;

    public BillsDAO (Context context) {
        this.dBHelper = new DBHelper(context);
    }

    public void open() {
        this.mDb = dBHelper.getWritableDatabase();
    }

    public void close() {
        dBHelper.close();
    }

    public SQLiteDatabase getDb() {
        return this.mDb;
    }

    public void createBill (Bills bill){
        ContentValues values = new ContentValues();
        values.put(BILLS_NAME, bill.getBillName());
        values.put(VALUE, bill.getAmount());
        values.put(FINISH_DATE, bill.getFinishDate());
        values.put(DESCRIPTION, bill.getDescription());
        long insertId = mDb.insert(BILL_TABLE_NAME, null, values);
        bill.setId(insertId);
    }


    public void updateTask (Bills bills, String oldTitle) {
        /* Aun queda pendiente el solucionar el problema de la ID */
        /*
        ContentValues values = new ContentValues();
        values.put(TITLE, task.getTitle());
        values.put(CATEGORY, task.getCategory());
        values.put(SHARE_WITH,task.getShareWith());
        values.put(TASK_PREREQUISITE,task.getTaskPrerequisite());
        values.put(DESCRIPTION,task.getDescription());
        String[] whereArgs = new String[] {String.valueOf(oldTitle)};
        /* Me must use KEY instead TITLE for sql update query. In same way, do to
           deleteTask method

        mDb.update(TASK_TABLE_NAME, values, TITLE + "=?", whereArgs);
        */
    }


    public void deleteTask (Bills bills){
        String[] selectionArgs = { bills.getBillName()};
        this.mDb.delete(BILL_TABLE_NAME, BILLS_NAME + " = ?",selectionArgs);
    }

    public ArrayList<Bills> getAllTasks(){
        ArrayList<Bills> bills_array = new ArrayList<Bills>();
        Cursor cursor = mDb.query(BILL_TABLE_NAME,allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Bills bill = new Bills(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));
            bill.setId(cursor.getInt(0));
            bills_array.add(bill);
            cursor.moveToNext();
        }
        cursor.close();
        return bills_array;
    }

}
