//package com.example.skinlab;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.adapters.AddressRecyclerAdapter;
//import com.example.models.Address;
//import com.example.skinlab.databinding.ActivityMyaccountDiachiBinding;
//import com.example.skinlab.databinding.FragmentMyAccountBinding;
//
//import java.util.ArrayList;
//import android.content.SharedPreferences;
//
//public class Myaccount_Diachi extends AppCompatActivity {
//    ActivityMyaccountDiachiBinding binding;
//    //    Databases db;
//    AddressRecyclerAdapter adapter;
//    ArrayList<Address> addresses;
//    DatabaseHelper databaseHelper;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMyaccountDiachiBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        addEvents();
//        createDb();
//        databaseHelper = new DatabaseHelper(this);
//
//
//    }
//
//    private void createDb() {
////        db = new Databases(this,Databases.DB_NAME, null, Databases.DB_VERSION);
////        db.createAddressSampleData(Myaccount_Diachi.this);
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadUserAddresses();
//    }
//
//    private void loadUserAddresses() {
//        String loggedInPhone = getLoggedInPhone();
//        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
//            addresses = new ArrayList<>();
//            SQLiteDatabase db = databaseHelper.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.USER +
//                    " WHERE " + DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});
//            if (cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME));
//                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE));
//                    String address1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS));
//                    String address2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS2));
//                    String name2ForAddress2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME2FORADDRESS2));
//                    String phone2ForAddress2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE2FORADDRESS2));
//
//                    // Kiểm tra xem cả address1 và address2 đều có giá trị
//                    if (address1 != null && !address1.isEmpty()) {
//                        Address address = new Address(name, phone, address1, null);
//                        addresses.add(address);
//                    }
//                    // Nếu address2 cũng có giá trị, thêm một địa chỉ mới với address2
//                    if (address2 != null && !address2.isEmpty()) {
//                        Address address2Item = new Address(name2ForAddress2, phone2ForAddress2, address2, null);
//                        addresses.add(address2Item);
//                    }
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//            db.close();
//            // Kiểm tra xem danh sách địa chỉ có dữ liệu hay không
//            if (addresses.isEmpty()) {
//                // Nếu danh sách địa chỉ trống, ẩn RecyclerView và hiển thị thông báo
//                binding.rcvdiachi.setVisibility(View.GONE);
//                Toast.makeText(this, "Không có địa chỉ", Toast.LENGTH_SHORT).show();
//            } else {
//                // Nếu danh sách địa chỉ có dữ liệu, hiển thị RecyclerView và hiển thị các địa chỉ
//                binding.rcvdiachi.setVisibility(View.VISIBLE);
//                displayAddresses();
//            }
//        } else {
//            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//
//
//    private void displayAddresses() {
//            AddressRecyclerAdapter adapter = new AddressRecyclerAdapter(this, addresses);
//            binding.rcvdiachi.setLayoutManager(new LinearLayoutManager(this));
//            binding.rcvdiachi.setAdapter(adapter);
//        }
//
//
//    private String getLoggedInPhone() {
//        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
//        return sharedPreferences.getString("loggedInPhone", "");
//    }
//
//
//    private void addEvents() {
//        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//            }
//        });
//        binding.addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Myaccount_Diachi.this, Myaccount_adddiachi.class);
//                // Kiểm tra xem người dùng đã có địa chỉ hay chưa
//                String loggedInPhone = getLoggedInPhone();
//                if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
//                    boolean hasAddress = hasAddress(loggedInPhone);
//                    // Truyền dữ liệu nếu người dùng chưa có địa chỉ
//                    if (!hasAddress) {
//                        String userName = getUserName(loggedInPhone);
//                        String userPhone = getUserPhone(loggedInPhone);
//                        intent.putExtra("userName", userName);
//                        intent.putExtra("userPhone", userPhone);
//                    }
//                }
//                startActivity(intent);
//            }
//
//            private String getUserPhone(String loggedInPhone) {
//                SQLiteDatabase db = databaseHelper.getReadableDatabase();
//                String userPhone = "";
//
//                Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_PHONE},
//                        DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    int userPhoneColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PHONE);
//                    if (userPhoneColumnIndex != -1) {
//                        userPhone = cursor.getString(userPhoneColumnIndex);
//                    } else {
//                        Log.e("Error", "Column 'user_phone' does not exist in the result set");
//                    }
//                    cursor.close();
//                } else {
//                    Log.e("Error", "No data found in the result set");
//                }
//                return userPhone;
//            }
//
//            public String getUserName(String loggedInPhone) {
//                SQLiteDatabase db = databaseHelper.getReadableDatabase();
//                String userName = "";
//
//                Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_NAME},
//                        DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    int userNameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NAME);
//                    if (userNameColumnIndex != -1) {
//                        userName = cursor.getString(userNameColumnIndex);
//                    } else {
//                        Log.e("Error", "Column 'user_name' does not exist in the result set");
//                    }
//                    cursor.close();
//                } else {
//                    Log.e("Error", "No data found in the result set");
//                }
//                return userName;
//            }
//
//
//            private boolean hasAddress(String loggedInPhone) {
//                SQLiteDatabase db = databaseHelper.getReadableDatabase();
//                Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.USER +
//                        " WHERE " + DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});
//                boolean hasAddress = false;
//                if (cursor.moveToFirst()) {
//                    String address1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS));
//                    if (address1 != null && !address1.isEmpty()) {
//                        hasAddress = true;
//                    }
//                }
//                cursor.close();
//                db.close();
//                return hasAddress;
//            }
//        });
//
//    }
//}
package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.adapters.AddressRecyclerAdapter;
import com.example.models.Address;
import com.example.skinlab.databinding.ActivityMyaccountDiachiBinding;

import java.util.ArrayList;

public class Myaccount_Diachi extends AppCompatActivity {
    ActivityMyaccountDiachiBinding binding;
    ArrayList<Address> addresses;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountDiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserAddresses();
    }

    public void loadUserAddresses() {
        String loggedInPhone = getLoggedInPhone();
        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
            addresses = new ArrayList<>();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.USER +
                    " WHERE " + DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE));
                    String address1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS));
                    String address2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS2));
                    String name2ForAddress2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME2FORADDRESS2));
                    String phone2ForAddress2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE2FORADDRESS2));

                    // Kiểm tra xem cả address1 và address2 đều có giá trị
                    if (address1 != null && !address1.isEmpty()) {
                        Address address = new Address(name, phone, address1, null);
                        addresses.add(address);
                    }
                    // Nếu address2 cũng có giá trị, thêm một địa chỉ mới với address2
                    if (address2 != null && !address2.isEmpty()) {
                        Address address2Item = new Address(name2ForAddress2, phone2ForAddress2, address2, null);
                        addresses.add(address2Item);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            // Kiểm tra xem danh sách địa chỉ có dữ liệu hay không
            if (addresses.isEmpty()) {
                // Nếu danh sách địa chỉ trống, ẩn RecyclerView và hiển thị thông báo
                binding.rcvdiachi.setVisibility(View.GONE);
                Toast.makeText(this, "Không có địa chỉ", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu danh sách địa chỉ có dữ liệu, hiển thị RecyclerView và hiển thị các địa chỉ
                binding.rcvdiachi.setVisibility(View.VISIBLE);
                displayAddresses();
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayAddresses() {
        AddressRecyclerAdapter adapter = new AddressRecyclerAdapter(this, addresses, databaseHelper);
        binding.rcvdiachi.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvdiachi.setAdapter(adapter);
    }

    public String getLoggedInPhone() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }

    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount_Diachi.this, Myaccount_adddiachi.class);
                // Kiểm tra xem người dùng đã có địa chỉ hay chưa
                String loggedInPhone = getLoggedInPhone();
                if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
                    boolean hasAddress = hasAddress(loggedInPhone);
                    // Truyền dữ liệu nếu người dùng chưa có địa chỉ
                    if (!hasAddress) {
                        String userName = getUserName(loggedInPhone);
                        String userPhone = getUserPhone(loggedInPhone);
                        intent.putExtra("userName", userName);
                        intent.putExtra("userPhone", userPhone);
                    }
                }
                startActivity(intent);
            }

            private String getUserPhone(String loggedInPhone) {
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                String userPhone = "";

                Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_PHONE},
                        DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int userPhoneColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PHONE);
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

            private String getUserName(String loggedInPhone) {
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                String userName = "";

                Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_NAME},
                        DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int userNameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NAME);
                    if (userNameColumnIndex != -1) {
                        userName = cursor.getString(userNameColumnIndex);
                    } else {
                        Log.e("Error", "Column 'user_name' does not exist in the result set");
                    }
                    cursor.close();
                } else {
                    Log.e("Error", "No data found in the result set");
                }
                return userName;
            }

            private boolean hasAddress(String loggedInPhone) {
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.USER +
                        " WHERE " + DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});
                boolean hasAddress = false;
                if (cursor.moveToFirst()) {
                    String address1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS));
                    if (address1 != null && !address1.isEmpty()) {
                        hasAddress = true;
                    }
                }
                cursor.close();
                db.close();
                return hasAddress;
            }
        });

    }
}
