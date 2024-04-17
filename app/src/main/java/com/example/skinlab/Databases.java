package com.example.skinlab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
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
    public static final String USER_COL_NAME = "user_name";
    public static final String USER_COL_AVATAR = "user_avatar";
    public static final String USER_COL_EMAIL = "user_email";
    public static final String USER_COL_PHONE = "user_phone";
    public static final String USER_COL_GENDER = "user_gender";
    public static final String USER_COL_DOB = "user_dob";
    public static final String USER_COL_PROVINCE = "user_province";
    public static final String USER_COL_DISTRICT = "user_district";
    public static final String USER_COL_ADDRESS = "user_address";

    public static final String USER_COL_PROVINCE2 = "user_province2";
    public static final String USER_COL_DISTRICT2 = "user_district2";
    public static final String USER_COL_ADDRESS2 = "user_address2";

    public static final String USER_COL_PASSWORD = "user_password";
    public static final String USER_COL_SKIN = "user_skin";

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
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + TBL_USER+ " (" +
                USER_COL_ID + " VARCHAR(50) PRIMARY KEY, " +
                USER_COL_NAME + " VARCHAR(50), " +
                USER_COL_AVATAR + " BLOB, " +
                USER_COL_EMAIL + " VARCHAR(50), " +
                USER_COL_PHONE + " REAL, " +
                USER_COL_GENDER + " TEXT, " +
                USER_COL_DOB + " TEXT, " +
                USER_COL_PROVINCE + " VARCHAR(50), " +
                USER_COL_DISTRICT + " VARCHAR(50), " +
                USER_COL_ADDRESS + " VARCHAR(50), " +
                USER_COL_PROVINCE2 + " VARCHAR(50), " +
                USER_COL_DISTRICT2 + " VARCHAR(50), " +
                USER_COL_ADDRESS2 + " VARCHAR(50), " +
                USER_COL_PASSWORD + " VARCHAR(50), " +
                USER_COL_SKIN + " VARCHAR(50) "
                + ")";

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
        db.execSQL(sql);
//
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER );
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_ORDERS);
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_PRODUCT);
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_FORUM);
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_ABOUTSKIN);
        onCreate(db);

    }

    //THUC HIEN SELECT
    public Cursor queryData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    //INSERT
    public boolean insertUser(String userId, String userName, byte[] userAvatar, String userEmail, double userPhone, String userGender, String userDob, String userProvince, String userDistrict, String userAddress, String userProvince2, String userDistrict2, String userAddress2, String userPassword, String userSkin) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + TBL_USER + "(" +
                USER_COL_ID + ", " +
                USER_COL_NAME + ", " +
                USER_COL_AVATAR + ", " +
                USER_COL_EMAIL + ", " +
                USER_COL_PHONE + ", " +
                USER_COL_GENDER + ", " +
                USER_COL_DOB + ", " +
                USER_COL_PROVINCE + ", " +
                USER_COL_DISTRICT + ", " +
                USER_COL_ADDRESS + ", " +
                USER_COL_PROVINCE2 + ", " +
                USER_COL_DISTRICT2 + ", " +
                USER_COL_ADDRESS2 + ", " +
                USER_COL_PASSWORD + ", " +
                USER_COL_SKIN +
                ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, userId);
        statement.bindString(2, userName);
        statement.bindBlob(3, userAvatar);
        statement.bindString(4, userEmail);
        statement.bindDouble(5, userPhone);
        statement.bindString(6, userGender);
        statement.bindString(7, userDob);
        statement.bindString(8, userProvince);
        statement.bindString(9, userDistrict);
        statement.bindString(10, userAddress);
        statement.bindString(11, userProvince2);
        statement.bindString(12, userDistrict2);
        statement.bindString(13, userAddress2);
        statement.bindString(14, userPassword);
        statement.bindString(15, userSkin);

        statement.executeInsert();
        return true;
    }

    //UPDATE
    public boolean updateUser(String userId, String userName, byte[] userAvatar, String userEmail, double userPhone, String userGender, String userDob, String userProvince, String userDistrict, String userAddress, String userProvince2, String userDistrict2, String userAddress2, String userPassword, String userSkin) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE " + TBL_USER + " SET " +
                USER_COL_NAME + " = ?, " +
                USER_COL_AVATAR + " = ?, " +
                USER_COL_EMAIL + " = ?, " +
                USER_COL_PHONE + " = ?, " +
                USER_COL_GENDER + " = ?, " +
                USER_COL_DOB + " = ?, " +
                USER_COL_PROVINCE + " = ?, " +
                USER_COL_DISTRICT + " = ?, " +
                USER_COL_ADDRESS + " = ?, " +
                USER_COL_PROVINCE2 + " = ?, " +
                USER_COL_DISTRICT2 + " = ?, " +
                USER_COL_ADDRESS2 + " = ?, " +
                USER_COL_PASSWORD + " = ?, " +
                USER_COL_SKIN + " = ? " +
                "WHERE " + USER_COL_ID + " = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, userName);
        statement.bindBlob(2, userAvatar);
        statement.bindString(3, userEmail);
        statement.bindDouble(4, userPhone);
        statement.bindString(5, userGender);
        statement.bindString(6, userDob);
        statement.bindString(7, userProvince);
        statement.bindString(8, userDistrict);
        statement.bindString(9, userAddress);
        statement.bindString(10, userProvince2);
        statement.bindString(11, userDistrict2);
        statement.bindString(12, userAddress2);
        statement.bindString(13, userPassword);
        statement.bindString(14, userSkin);
        statement.bindString(15, userId);

        statement.executeUpdateDelete();
        return true;
    }

    //DELETE
    public boolean deleteUser(String userId) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + TBL_USER + " WHERE " + USER_COL_ID + " = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, userId);
        statement.executeUpdateDelete();
        return true;
    }
    //Create Sample Data
    public void createSampleUserData(Context context) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM " + TBL_USER);
        insertUser("001", "John Doe", convertPhoto(context, R.drawable.avatar1), "john.doe@example.com", Double.parseDouble("0919509105"), "Male", "1990-01-01", "New York", "Manhattan", "123 Main St", "California", "Los Angeles", "456 Broadway", "password123", "normal");
        insertUser("002", "Jane Smith", convertPhoto(context, R.drawable.avatar2), "jane.smith@example.com", Double.parseDouble("0876543210"), "Female", "1995-05-05", "California", "Los Angeles", "456 Broadway", "New York", "Manhattan", "123 Main St", "password456", "sensitive");
        insertUser("003", "Alex Johnson", convertPhoto(context, R.drawable.avatar3), "alex.johnson@example.com", Double.parseDouble("5555555555"), "Male", "1985-10-10", "Texas", "Houston", "789 Elm St", "Florida", "Miami", "321 Oak St", "password789", "normal");
        database.close();
    }
    //Convert Photo
    private byte[] convertPhoto(Context context, int resourcedId) {
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(resourcedId);
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
