package com.example.mydiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public DiaryDBHelper(Context context) {
        super(context,"diarydb",null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table diarydb "+ "(_id integer primary key autoincrement,"+ "title text, date text, weather text, picturelink text, content text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION) {
            db.execSQL("drop table diarydb");
            onCreate(db);
        }
    }
}
