package com.example.basicexam2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    private ProductDAO(Context ctx) {
        dbHelper = new ProductDBHelper(ctx);
    }

    public static ProductDAO open(Context ctx) {
        return new ProductDAO(ctx);
    }

    public void addProduct(Product p) {
        Log.i("ProductDAO","addProduct() called");
        db = dbHelper.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put("name", p.getName());
        v.put("maker",p.getMaker());
        v.put("price",p.getPrice());


        db.insert("productdb",null,v);
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from productdb", null);

        while(cursor.moveToNext()) {
            Product p = new Product();
            p.setName(cursor.getString(1));
            p.setMaker(cursor.getString(2));
            p.setPrice(cursor.getInt(3));

            products.add(p);
        }
        return products;
    }

    public void delProduct(String id) {
        db = dbHelper.getWritableDatabase();
        db.delete("productdb","_id=?",new String[]{id});
    }

    public Product getProduct(String id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from productdb where _id=?",new String[]{id});
        cursor.moveToNext();

        Product p = new Product();
        p.setName(cursor.getString(1));
        p.setMaker(cursor.getString(2));
        p.setPrice(cursor.getInt(3));

        return p;
    }
}