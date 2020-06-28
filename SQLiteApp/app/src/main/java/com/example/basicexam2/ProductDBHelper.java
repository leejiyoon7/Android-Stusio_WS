package com.example.basicexam2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public ProductDBHelper(Context context) {
        super(context,"productdb",null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table productdb "+
                "(_id integer primary key autoincrement,"+
                "name text, maker text ,price integer)";
        db.execSQL(sql);

        // insert sample data
        db.execSQL("insert into productdb (name, maker, price) values ('Galaxy Note 10','Saumsung', 1500000)");
        db.execSQL("insert into productdb (name, maker, price) values ('Galaxy S 11','Saumsung', 1300000)");
        db.execSQL("insert into productdb (name, maker, price) values ('iPhone 11','Apple', 1400000)");
        db.execSQL("insert into productdb (name, maker, price) values ('iPhone 11 Max','Apple', 1700000)");
        db.execSQL("insert into productdb (name, maker, price) values ('iPhone 11 R','Apple', 1200000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION) {
            db.execSQL("drop table productdb");
            onCreate(db);
        }
    }
}
