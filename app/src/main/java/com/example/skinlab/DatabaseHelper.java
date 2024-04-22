package com.example.skinlab;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public boolean userHasAddress1(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ADDRESS}; // Assuming COLUMN_USER_ADDRESS represents the address1 field
        String selection = COLUMN_USER_PHONE + " = ?";
        String[] selectionArgs = {phone};
        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);
        boolean hasAddress1 = false;
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_USER_ADDRESS);
            if (columnIndex != -1) {
                hasAddress1 = !cursor.isNull(columnIndex);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return hasAddress1;
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
    String[] columns = {COLUMN_USER_ID, COLUMN_USER_PHONE, COLUMN_USER_EMAIL};
    String selection = "(" + COLUMN_USER_PHONE + " = ? OR " + COLUMN_USER_EMAIL + " = ?) AND " + COLUMN_USER_PASSWORD + " = ?";
    String[] selectionArgs = {emailPhone, emailPhone, password};
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


    public boolean checkSoDienThoaiTonTai(String soDienThoai) {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {COLUMN_USER_PHONE};
            String selection = COLUMN_USER_PHONE + " = ?";
            String[] selectionArgs = {soDienThoai};
            Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();
            cursor.close();
            return count > 0;
        }
    public void updateMatKhau(String phone, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASSWORD, newPassword);
        String[] selectionArgs = {phone};

        // Cập nhật mật khẩu mới
        db.update(USER, values, COLUMN_USER_PHONE + " = ?", new String[]{phone});
        db.close();
    }
    public boolean checkEmailTonTai(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_EMAIL};
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public String getUserPhone(String emailPhone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userPhone = "";

        String[] columns = {COLUMN_USER_PHONE};
        String selection = COLUMN_USER_PHONE + " = ? OR " + COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {emailPhone, emailPhone};

        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userPhoneColumnIndex = cursor.getColumnIndex(COLUMN_USER_PHONE);
            if (userPhoneColumnIndex != -1) {
                userPhone = cursor.getString(userPhoneColumnIndex);
            } else {
                Log.e("Error", "Column 'user_phone' does not exist in the result set");
            }
            cursor.close();
        } else {
            Log.e("Error", "No data found in the result set");
        }

        return userPhone;
    }


    public void updateUserSkin(String phone, String newSkinType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_SKINTYPE, newSkinType);

        // Cập nhật thông tin user_skin của người dùng
        db.update(USER, values, COLUMN_USER_PHONE + " = ?", new String[]{phone});
        db.close();
    }

//    public String getUserSkin(String phone) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String userSkinType = "";
//
//        String[] columns = {COLUMN_USER_SKINTYPE};
//        String selection = COLUMN_USER_PHONE + " = ?";
//        String[] selectionArgs = {phone};
//
//        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            int userSkinColumnIndex = cursor.getColumnIndex(COLUMN_USER_SKINTYPE);
//            if (userSkinColumnIndex != -1) {
//                userSkinType = cursor.getString(userSkinColumnIndex);
//            } else {
//                Log.e("Error", "Column 'user_skin' does not exist in the result set");
//            }
//            cursor.close();
//        } else {
//            Log.e("Error", "No data found in the result set");
//        }
//
//        return userSkinType;
//    }


}
