
package com.example.skinlab;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.models.Account;
import com.example.models.Appointment;
import com.example.models.Order;

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


        public static final String TBL_SKIN = "ABOUTSKIN_DACDIEM";
    public static final String COLUMN_DACDIEM1 = "skin_dacdiem1";
    public static final String COLUMN_DACDIEM2 = "skin_dacdiem2";
    public static final String COLUMN_DACDIEM3 = "skin_dacdiem3";
    public static final String COLUMN_MOTA1 = "skin_mota";
    public static final String COLUMN_MOTA2 = "skin_mota2";
    public static final String COLUMN_USERSKIN = "user_skin";


    public static final String TBL_NAME = "ABOUTSKIN_LICHTUVAN";
    public static final String COLUMN_USER_NAME2 = "user_name";
    public static final String COLUMN_USER_PHONE2 = "user_phone";
    public static final String COLUMN_USER_ADDRESS3 = "user_address";
    public static final String COLUMN_USER_DATE = "user_date";
    public static final String COLUMN_USER_TIME = "user_time";
    public static final String COLUMN_USER_CONTENT = "user_content";



    public static final String COLUMN_USER_SKINTYPE = "user_skin";



    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_AVA = "user_avatar";


    public static final String COLUMN_USER_PASSWORD = "user_password";

    public static final String TBL_ORDER = "ORDERS";
    public static final String COLUMN_PRODUCT1_ID = "product_order1";
    public static final String COLUMN_PRODUCT1_QUANTITY = "quantity_product1";
    public static final String COLUMN_PRODUCT2_ID = "product_order2";
    public static final String COLUMN_PRODUCT2_QUANTITY = "quantity_product2";
    public static final String COLUMN_PRODUCT3_ID = "product_order3";
    public static final String COLUMN_PRODUCT3_QUANTITY = "quantity_product3";
    public static final String COLUMN_STATUS = "status";



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

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " (" +
                COLUMN_USER_NAME2 + " TEXT, " +
                COLUMN_USER_PHONE2 + " TEXT, " +
                COLUMN_USER_ADDRESS3 + " TEXT, " +
                COLUMN_USER_DATE + " TEXT, " +
                COLUMN_USER_TIME + " TEXT, " +
                COLUMN_USER_CONTENT + " TEXT)";
        db.execSQL(createTableQuery);

        String createTableQuery2 = "CREATE TABLE IF NOT EXISTS " + TBL_ORDER + " (" +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_PRODUCT1_ID + " TEXT, " +
                COLUMN_PRODUCT1_QUANTITY + " INT, " +
                COLUMN_PRODUCT2_ID + " TEXT, " +
                COLUMN_PRODUCT2_QUANTITY + " INT, " +
                COLUMN_PRODUCT3_ID + " TEXT, " +
                COLUMN_PRODUCT3_QUANTITY + " INT, " +
                COLUMN_STATUS + " TEXT)";
        db.execSQL(createTableQuery2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ORDER);
        onCreate(db);
    }

    public boolean insertOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", "5");
        contentValues.put("product_order1", order.getProduct_order1());
        contentValues.put("quantity_product1", order.getQuantity_product1());
        contentValues.put("product_order2", order.getProduct_order2());
        contentValues.put("quantity_product2", order.getQuantity_product2());
        contentValues.put("product_order3", order.getProduct_order3());
        contentValues.put("quantity_product3", order.getQuantity_product3());
        contentValues.put("status", "Đang xử lý");
        long result = db.insert("\"ORDER\"", null, contentValues);
        return result != -1;
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

    public String getUserSkinType(String userPhone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userSkinType = "";

        String[] columns = {COLUMN_USER_SKINTYPE};
        String selection = COLUMN_USER_PHONE + " = ?";
        String[] selectionArgs = {userPhone};

        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userSkinTypeColumnIndex = cursor.getColumnIndex(COLUMN_USER_SKINTYPE);
            if (userSkinTypeColumnIndex != -1) {
                userSkinType = cursor.getString(userSkinTypeColumnIndex);
            } else {
                Log.e("Error", "Column 'user_skin' does not exist in the result set");
            }
            cursor.close();
        } else {
            Log.e("Error", "No data found in the result set");
        }
        userSkinType = userSkinType.trim();

        return userSkinType;
    }


    public void deleteAddress(String loggedInPhone, int addressNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        String addressColumn;
        if (addressNumber == 1) {
            addressColumn = DatabaseHelper.COLUMN_USER_ADDRESS;
        } else {
            addressColumn = DatabaseHelper.COLUMN_USER_ADDRESS2;
        }
        db.execSQL("UPDATE " + DatabaseHelper.USER + " SET " + addressColumn + " = NULL WHERE " +
                DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});
        db.close();
    }


    public void updateAddressFields(String phone, String name, String phone2, String address, int addressNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        String addressColumn = null;

        if (addressNumber == 1) {
            // Nếu là địa chỉ 1, cập nhật user_address thành null
            addressColumn = COLUMN_USER_ADDRESS;
        } else if (addressNumber == 2) {
            // Nếu là địa chỉ 2, cập nhật các trường của địa chỉ 2 thành null
            addressColumn = COLUMN_USER_ADDRESS2;
        }

        // Xây dựng câu lệnh SQL
        String sqlQuery;
        if (addressColumn.equals(COLUMN_USER_ADDRESS)) {
            // Nếu cột được chọn là COLUMN_USER_ADDRESS, cập nhật chỉ user_address
            sqlQuery = "UPDATE " + USER + " SET " + COLUMN_USER_ADDRESS + " = NULL " +
                    "WHERE " + COLUMN_USER_PHONE + " = ?";
        } else if (addressColumn.equals(COLUMN_USER_ADDRESS2)) {
            // Nếu cột được chọn là COLUMN_USER_ADDRESS2, cập nhật user_name2foraddress2, user_phone2foraddress2, và user_address2
            sqlQuery = "UPDATE " + USER + " SET " + COLUMN_USER_NAME2FORADDRESS2 + " = NULL, " +
                    COLUMN_USER_PHONE2FORADDRESS2 + " = NULL, " + COLUMN_USER_ADDRESS2 + " = NULL " +
                    "WHERE " + COLUMN_USER_PHONE + " = ? ";
        } else {
            // Trường hợp cột không được xác định, không thực hiện gì cả
            return;
        }

        // Thực thi câu lệnh SQL
        db.execSQL(sqlQuery, new String[]{phone});

        // Đóng kết nối tới database
        db.close();
    }
        public Cursor getSkinDataByType(String skinType) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_DACDIEM1, COLUMN_DACDIEM2, COLUMN_DACDIEM3, COLUMN_MOTA1, COLUMN_MOTA2};
        String selection = COLUMN_USERSKIN + " = ?";
        String[] selectionArgs = {skinType};
        return db.query(TBL_SKIN, columns, selection, selectionArgs, null, null, null);
    }


    public void addAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME2, appointment.getUserName());
        values.put(COLUMN_USER_PHONE2, appointment.getUserPhone());
        values.put(COLUMN_USER_ADDRESS3, appointment.getUserAddress());
        values.put(COLUMN_USER_DATE, appointment.getUserDate());
        values.put(COLUMN_USER_TIME, appointment.getUserTime());
        values.put(COLUMN_USER_CONTENT, appointment.getUserContent());

        db.insert(TBL_NAME, null, values);
        db.close();
    }



}
