package com.example.aldo.finanzapp.Models;

/**
 * Created by aldo on 13-11-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;





import java.util.ArrayList;
import java.util.HashMap;

import static android.provider.MediaStore.Video.VideoColumns.CATEGORY;

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
        values.put(VALUE, bill.getValue());
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


    /**
     * Compose JSON out of SQLite records
     *
     */
    /*
    public String composeTaskJSONfromSQLite(String mail, String name){
        ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " +TASK_TABLE_NAME+" WHERE "+UPDATE_STATUS+" = '"+"no"+"'";
        this.open();
        //mDb = dBHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("mail", mail);
                map.put("name", name);
                map.put(KEY, cursor.getString(0));
                map.put(BILLS_NAME, cursor.getString(1));
                if (cursor.getString(2) != null){
                    map.put(VALUE, cursor.getString(2));
                }
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        //this.close();
        cursor.close();

        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }
    */

    /*
    public String composeMailJSON(String mail){
        ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mail", mail);
        wordList.add(map);
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }
    */

    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync needed\n";
        }
        return msg;
    }

    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM "+BILL_TABLE_NAME+ " where " +UPDATE_STATUS+" = '"+"no"+"'";
        this.open();
        //SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        count = cursor.getCount();
        //this.close();
        return count;
    }

    /**
     * Update Sync status against each User ID
     * @param id
     * @param status
     */
    /*
    public void updateSyncStatus(String id, String status, String idExternDB){
        //SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        String updateQuery = "Update " + BILL_TABLE_NAME+" set " +UPDATE_STATUS+" = '"+ status +"' where "+KEY+"="+"'"+ id +"'";
        String updateQuery2 = "Update " + BILL_TABLE_NAME+" set " +KEY_EXTERN_DB+" = '"+ idExternDB +"' where "+KEY+"="+"'"+ id +"'";
        Log.d("query update status : ",updateQuery);
        Log.d("query update id_ext : ",updateQuery2);
        mDb.execSQL(updateQuery);
        mDb.execSQL(updateQuery2);
        //this.close();
    }
    */

}
