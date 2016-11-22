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
import java.util.Calendar;

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
    public static final String URI = "uri";
    public static final String UPDATE_STATUS = "updateStatus";


    public static final String BILL_TABLE_CREATE = "CREATE TABLE " + BILL_TABLE_NAME + "(" + KEY +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + BILLS_NAME + " TEXT, " + AMOUNT + " INTEGER, " +
            FINISH_DATE + " DATE, " + DESCRIPTION + " TEXT, " + URI + " TEXT);";

    public static final String BILL_TABLE_DROP = "DROP TABLE IF EXISTS " + BILL_TABLE_NAME + ";";

    private String[] allColumns = {KEY, BILLS_NAME, AMOUNT, FINISH_DATE, DESCRIPTION, URI};

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
        values.put(URI,bill.getSelectedImage());
        long insertId = mDb.insert(BILL_TABLE_NAME, null, values);
        bill.setId(insertId);
    }


    public void updateBill (Bills bill, String oldTitle, String oldAmount, String oldDate) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(BILLS_NAME, bill.getBillName());
        values.put(AMOUNT, bill.getAmount());
        values.put(FINISH_DATE,bill.getFinishDate());
        values.put(DESCRIPTION,bill.getDescription());
        values.put(URI,bill.getSelectedImage());
        String id = getBillId(oldTitle,oldAmount,oldDate);
        String[] whereArgs = new String[] {String.valueOf(id)};

        mDb.update(BILL_TABLE_NAME, values, KEY + "=?", whereArgs);

    }

    public void deleteBill (Bills bills){
        this.open();
        String id = getBillId(bills.getBillName(),bills.getAmount(),bills.getFinishDate());
        String[] selectionArgs = { String.valueOf(id)};
        this.mDb.delete(BILL_TABLE_NAME, KEY + " = ?",selectionArgs);
    }

    public ArrayList<Bills> getAllBills(){
        ArrayList<Bills> bills_array = new ArrayList<Bills>();
        Cursor cursor = mDb.query(BILL_TABLE_NAME,allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Bills bill = new Bills(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
            bill.setId(cursor.getInt(0));
            bills_array.add(bill);
            cursor.moveToNext();
        }
        cursor.close();
        return bills_array;
    }

    /**
     * @param period number of days of the period to pick the expenses
     * @return List with the expenses
     */
    public ArrayList<Bills> getBills(String period){
        ArrayList<Bills> bills_array = new ArrayList<Bills>();
        String queryGetBillsByDate = "SELECT * FROM " + BILL_TABLE_NAME + " WHERE " + FINISH_DATE +
                " <= date('now','+" + period + " days');";
        this.open();
        Cursor cursor = mDb.rawQuery(queryGetBillsByDate,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Log.d("Bills"," finish date = " + cursor.getString(3));
            Bills bill = new Bills(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
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

    /* not usefull
    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String year  = String.valueOf(calendar.get(Calendar.YEAR));
        String date  = year + "-" + month + "-" + day;
        return date;
    }*/

    public String changeDateFormat(String date){
        String day = date.substring(0,2);
        String month = date.substring(3,5);
        String year  = date.substring(6,10);
        date = year + "-" + month + "-" + day;
        Log.d("date ",date);
        return date;
    }

}
