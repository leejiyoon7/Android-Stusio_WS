package com.example.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DiaryDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    private DiaryDAO(Context ctx) {
        dbHelper = new DiaryDBHelper(ctx);
    }

    public static DiaryDAO open(Context ctx) {
        return new DiaryDAO(ctx);
    }

    public void addDiary(Diary d) {
        Log.i("DiaryDAO","addDiary() called");
        db = dbHelper.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put("title",d.getTitle());
        v.put("date",d.getDate());
        v.put("weather",d.getWeather());
        v.put("picturelink",d.getPicturelink());
        v.put("content",d.getContent());

        db.insert("diarydb",null, v);
    }

    public List<Diary> getAll() {
        List<Diary> diarys = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from diarydb",null);

        while (cursor.moveToNext()) {
            Diary d = new Diary();
            d.setTitle(cursor.getString(1));
            d.setDate(cursor.getString(2));
            d.setWeather(cursor.getString(3));
            d.setPicturelink(cursor.getString(4));
            d.setContent(cursor.getString(5));

            diarys.add(d);
        }
        return diarys;
    }

    //db 전체 삭체
    public void deleteAll() {
        db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM diarydb;");
    }

    //행 삭제
    public void delete (String a, String b) {
        db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM diarydb WHERE title = '"+ a +"' AND date = '"+ b + "';");
    }

    public void search(String a, String b) {
        db = dbHelper.getReadableDatabase();
        db.rawQuery("SELECT picturelink, content FROM diarydb WHERE title = '"+ a +"' AND date = '"+ b + "';",null);
    }

    public String emotionLast (){
        String result = "";
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM diarydb ORDER BY _id DESC LIMIT 1",null);

        while (cursor.moveToNext()) {
            result = cursor.getString(3);
        }
        return result;
    }

}
