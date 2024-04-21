package com.example.skinlab;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.models.Account;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Skinlab.db";
    private static final int DB_VERSION = 1;

    public static final String USER = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_PHONE = "user_phone";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_GENDER = "user_gender";
    public static final String COLUMN_USER_DOB = "user_DOB";
    public static final String COLUMN_USER_ADDRESS = "user_address";
    public static final String COLUMN_USER_ADDRESS2 = "user_address2";
    public static final String COLUMN_USER_NAME2FORADDRESS2 = "user_name2foraddress2";
    public static final String COLUMN_USER_PHONE2FORADDRESS2 = "user_phone2foraddress2";


    public static final String COLUMN_USER_SKINTYPE = "user_skin";



    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_AVA = "user_avatar";


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


    public boolean checkLogin(String emailPhone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_PHONE}; // Thêm COLUMN_USER_PHONE vào đây
        String selection = COLUMN_USER_PHONE + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {emailPhone, password};
        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public void saveUserToDatabase(Account user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getHoTen());
        values.put(COLUMN_USER_PHONE, user.getSdt());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getMatKhau());
        values.put(COLUMN_USER_DOB, user.getDob());
        values.put(COLUMN_USER_GENDER, user.getGioiTinh());

        // Insert dữ liệu vào bảng người dùng
        db.insert(USER, null, values);
        db.close();
    }
    public void updateUserProfile(String phone, String newName, String newEmail, String newPhone, String newGender, String newDOB, String newAvatarUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, newName);
        values.put(COLUMN_USER_EMAIL, newEmail);
        values.put(COLUMN_USER_PHONE, newPhone);
        values.put(COLUMN_USER_GENDER, newGender);
        values.put(COLUMN_USER_DOB, newDOB);
        values.put(COLUMN_USER_AVA, newAvatarUrl);

        // Cập nhật thông tin người dùng
        db.update(USER, values, COLUMN_USER_PHONE + " = ?", new String[]{phone});
        db.close();
    }
    public void updateUserAdress(String phone, String newName, String newEmail, String newPhone, String newGender, String newDOB, String newAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Chỉ cập nhật các cột có giá trị không rỗng
        if (newName != null) {
            values.put(COLUMN_USER_NAME, newName);
        }
        if (newEmail != null) {
            values.put(COLUMN_USER_EMAIL, newEmail);
        }
        if (newPhone != null) {
            values.put(COLUMN_USER_PHONE, newPhone);
        }
        if (newGender != null) {
            values.put(COLUMN_USER_GENDER, newGender);
        }
        if (newDOB != null) {
            values.put(COLUMN_USER_DOB, newDOB);
        }
        if (newAddress != null) {
            values.put(COLUMN_USER_ADDRESS, newAddress);
        }

        // Cập nhật thông tin người dùng
        db.update(USER, values, COLUMN_USER_PHONE + " = ?", new String[]{phone});
        db.close();
    }


}
