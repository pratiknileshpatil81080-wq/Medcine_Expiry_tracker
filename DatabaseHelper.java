package com.example.medexpiry;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "medicine.db";
    public static final String TABLE_NAME = "med_table";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "count INTEGER," +
                "purchase_date TEXT," +
                "expiry TEXT," +
                "image TEXT," +
                "dosage TEXT," +
                "info TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    // Insert Data
    public void insertData(String name,int count,String purchase,String expiry,String image,String dosage,String info){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("count",count);
        cv.put("purchase_date",purchase);
        cv.put("expiry",expiry);
        cv.put("image",image);
        cv.put("dosage",dosage);
        cv.put("info",info);

        db.insert(TABLE_NAME,null,cv);
    }

    // Fetch all data
    public Cursor getAllData(){

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);

    }

    // Delete row
    public void deleteData(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});

    }

}