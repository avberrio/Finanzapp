package com.example.aldo.finanzapp.models;

/**
 * Created by aldo on 13-11-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.aldo.finanzapp.models.DBHelper;

import java.util.ArrayList;

/**
 * Created by Mathieu on 30/08/2016.
 */

public class BillsDAO {
    public static final String BILL_TABLE_NAME = "bill";
    public static final String KEY = "id";
    public static final String BILLS_NAME = "billsName";
    public static final String AMOUNT = "amount";
    public static final String FINISH_DATE = "finishDate";
    public static final String DESCRIPTION = "description";
    public static final String UPDATE_STATUS = "updateStatus";


    public static final String BILL_TABLE_CREATE = "CREATE TABLE " + BILL_TABLE_NAME + "(" + KEY +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + BILLS_NAME + " TEXT, " + AMOUNT + " INTEGER, " +
            FINISH_DATE + " TEXT, " + DESCRIPTION + " TEXT);";

    public static final String BILL_TABLE_DROP = "DROP TABLE IF EXISTS " + BILL_TABLE_NAME + ";";

    private String[] allColumns = {KEY, BILLS_NAME, AMOUNT, FINISH_DATE, DESCRIPTION};

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
        values.put(AMOUNT, bill.getAmount());
        values.put(FINISH_DATE, bill.getFinishDate());
        values.put(DESCRIPTION, bill.getDescription());
        long insertId = mDb.insert(BILL_TABLE_NAME, null, values);
        bill.setId(insertId);
    }


    public void updateBill (Bills bill, String oldTitle, String oldAmount, String oldDate) {
        ContentValues values = new ContentValues();
        values.put(BILLS_NAME, bill.getBillName());
        values.put(AMOUNT, bill.getAmount());
        values.put(FINISH_DATE,bill.getFinishDate());
        values.put(DESCRIPTION,bill.getDescription());
        String id = getBillId(oldTitle,oldAmount,oldDate);
        String[] whereArgs = new String[] {String.valueOf(id)};

        mDb.update(BILL_TABLE_NAME, values, KEY + "=?", whereArgs);

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

    public String getBillId (String title, String amount, String date ){
        String id;
        String queryGetId = "SELECT " + KEY + " FROM " + BILL_TABLE_NAME + " WHERE " +
                BILLS_NAME + " = '" + title + "' AND " + FINISH_DATE + " = '" + date + "' AND " +
                AMOUNT + " = '" + amount + "';";
        this.open();
        Cursor cursor = mDb.rawQuery(queryGetId,null);
        cursor.moveToFirst();
        id = cursor.getString(0);
        return id;
    }

}
