package com.example.skinlab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class Databases extends SQLiteOpenHelper {
    public static final String DB_NAME = "skinlab.sqlite";
    public static final int DB_VERSION = 1;

    //Bảng USER
    public static final String TBL_USER = "user";
    public static final String USER_COL_ID = "user_id";


    //Bảng ORDER
    public static final String TBL_ORDERS = "orders";


    //Bảng PRODUCT
    public static final String TBL_PRODUCT = "product";


    //Bảng FORUM
    public static final String TBL_FORUM = "forum";


    //Bảng ABOUTSKIN
    public static final String TBL_ABOUTSKIN = "about_skin";



    //Constructor
    public Databases(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE IF NOT EXISTS "
//                + TBL_USER+ " (" +
//             USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT )";

//                  "CREATE TABLE IF NOT EXISTS "
//                + TBL_ORDERS + " ()"; " +
//
//
//                "CREATE TABLE IF NOT EXISTS "
//                + TBL_PRODUCT+ " ()"; " +
//
//                "CREATE TABLE IF NOT EXISTS "
//                + TBL_FORUM + " ()"; " +
//
//                sql = "CREATE TABLE IF NOT EXISTS "
//                + TBL_ABOUTSKIN + " ();";
//
//        db.execSQL(sql);
//
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_FORUM);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ABOUTSKIN);
        onCreate(db);

    }

    //THUC HIEN SELECT
    public Cursor queryData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    //INSERT

    //UPDATE

    //DELETE

    //Create Sample Data


    //Convert Photo
    private byte[] convertPhoto(Context context, int resourcedId) {
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(resourcedId);
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
