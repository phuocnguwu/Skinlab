package com.example.skinlab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class Databases extends SQLiteOpenHelper {
    public static final String DB_NAME = "skinlab.db";
    public static final String DB_FOLDER = "databases";
    public static SQLiteDatabase db = null;

    public static final int DB_VERSION = 1;
    private final Context context;

//
//    //Bảng USER
//    public static final String TBL_USER = "user";
//    public static final String USER_COL_ID = "user_id";
//    public static final String USER_COL_NAME = "user_name";
//    public static final String USER_COL_AVATAR = "user_avatar";
//    public static final String USER_COL_EMAIL = "user_email";
//    public static final String USER_COL_PHONE = "user_phone";
//    public static final String USER_COL_GENDER = "user_gender";
//    public static final String USER_COL_DOB = "user_dob";
//    public static final String USER_COL_PROVINCE = "user_province";
//    public static final String USER_COL_DISTRICT = "user_district";
//    public static final String USER_COL_ADDRESS = "user_address";
//    public static final String USER_COL_PROVINCE2 = "user_province2";
//    public static final String USER_COL_DISTRICT2 = "user_district2";
//    public static final String USER_COL_ADDRESS2 = "user_address2";
//    public static final String USER_COL_PASSWORD = "user_password";
//    public static final String USER_COL_SKIN = "user_skin";
//    //Bảng ORDER
//    public static final String TBL_ORDERS = "orders";
//    //Bảng PRODUCT

//    //Bảng FORUM
//    public static final String TBL_FORUM = "forum";
//
//
//    //Bảng ABOUTSKIN
//    public static final String TBL_ABOUTSKIN = "about_skin";
//
//
//
    //Constructor
    public Databases(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
   @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER );
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_ORDERS);
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_PRODUCT);
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_FORUM);
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_ABOUTSKIN);
//        onCreate(db);
    }

    public void copyDatabaseFromAssets() {
        String dbPath = context.getApplicationInfo().dataDir + "/" + DB_FOLDER + "/" + DB_NAME;
        if (!checkDatabaseExists(dbPath)) {
        try {
            InputStream inputStream = context.getAssets().open(DB_NAME);
            File f = new File(context.getApplicationInfo().dataDir + DB_FOLDER);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}

    private boolean checkDatabaseExists(String dbPath) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Database does not exist.");
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }


//    //THUC HIEN SELECT
//    public Cursor queryData(String sql){
//        SQLiteDatabase db = getReadableDatabase();
//        return db.rawQuery(sql, null);
//    }
//    //INSERT
//    public boolean insertAdressData(String name, String phone, String address1, String address2) {
//        SQLiteDatabase database = getWritableDatabase();
//        String sql = "INSERT INTO " + TBL_USER + "(" +
//                USER_COL_NAME + ", " +
//                USER_COL_PHONE + ", " +
//                USER_COL_ADDRESS + ", " +
//                USER_COL_ADDRESS2  +
//                ") VALUES(?, ?, ?, ?)";
//
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();
//        statement.bindString(1, name);
//        statement.bindString(2, phone);
//        statement.bindString(3, address1);
//        statement.bindString(4, address2);
//        statement.executeInsert();
//        return true;
//    }
//
//
//    //UPDATE
//
//    //DELETE
//
//    //Create Sample Data
//
//
//    //Convert Photo
//    private byte[] convertPhoto(Context context, int resourcedId) {
//        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(resourcedId);
//        Bitmap bitmap = drawable.getBitmap();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//        return outputStream.toByteArray();
//    }
//
//    public void createAddressSampleData(Myaccount_Diachi myaccountDiachi) {
//        SQLiteDatabase database = getWritableDatabase();
//        database.execSQL("DELETE FROM " + TBL_USER);
//        insertAdressData("Nguyễn Thuỳ Linh","0943049504","122, đường Điện Biên Phủ, phường 9","34, đường Nguyễn Văn Cừ, phường 4");
//        database.close();
//    }
}
