package com.example.skinlab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Skinlab.db";
    private static final int DB_VERSION = 1;

    public static final String USER = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_PHONE = "user_phone";
    public static final String COLUMN_USER_NAME = "user_name";

    public static final String COLUMN_USER_PASSWORD = "user_password";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + USER);
        onCreate(db);
    }
    // Phương thức lấy tên người dùng dựa trên ID người dùng
    public String getUserName(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_NAME};
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);
        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
        }
        cursor.close();
        return userName;
    }


    // Phương thức kiểm tra thông tin đăng nhập
    public boolean checkLogin(String emailPhone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USER_PHONE + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {emailPhone, password};
        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
