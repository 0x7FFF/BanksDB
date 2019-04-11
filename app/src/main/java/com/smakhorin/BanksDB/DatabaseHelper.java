package com.smakhorin.BanksDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Banks.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "banks_table";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_ADDRESS = "ADDRESS";
    public static final String COL_PHONE = "PHONE";
    public static final String COL_EMPOYEECOUNT = "EMPLOYEECOUNT";
    public static final String COL_WORKTIME = "WORKTIME";
    public static final String COL_EMAIL = "EMAIL";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ADDRESS TEXT,PHONE TEXT,EMPLOYEECOUNT INTEGER,WORKTIME TEXT, EMAIL TEXT)");
        //Log.d("From onCreate : ", " successfully created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        //Log.d("From onUpgrade : ","DONE");
    }

    public void addBank(Bank bank) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME,bank.getName());
        values.put(COL_ADDRESS,bank.getAddress());
        values.put(COL_PHONE,bank.getPhone());
        values.put(COL_EMPOYEECOUNT,bank.getEmployeeCount());
        values.put(COL_WORKTIME,bank.getWorkTime());
        values.put(COL_EMAIL,bank.getEmail());
        SQLiteDatabase db = this.getWritableDatabase();
        //db.update(TABLE_NAME, values, COL_ID + " = ?", new String[] { String.valueOf(bank.get_id())});
        db.insert(TABLE_NAME,null,values);
    }

    public void updateProduct(Bank bank){
        ContentValues values = new ContentValues();
        values.put(COL_NAME,bank.getName());
        values.put(COL_ADDRESS,bank.getAddress());
        values.put(COL_PHONE,bank.getPhone());
        values.put(COL_EMPOYEECOUNT,bank.getEmployeeCount());
        values.put(COL_WORKTIME,bank.getWorkTime());
        values.put(COL_EMAIL,bank.getEmail());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, values, COL_ID + "=" + bank.getId(),null);
    }

    public List<Bank> listBanks() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Bank> banks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String phone = cursor.getString(3);
                int employeeCount = Integer.parseInt(cursor.getString(4));
                String workTime = cursor.getString(5);
                String email = cursor.getString(6);
                banks.add(new Bank(id, name, address,phone,employeeCount,workTime,email));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return banks;
    }

    public void deleteBank(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = " + id,null);
    }

}
