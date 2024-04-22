//package com.example.skinlab;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.skinlab.databinding.ActivityDialogSaveBinding;
//import com.example.skinlab.databinding.ActivityMyaccountAdddiachiBinding;
//
//public class Myaccount_adddiachi extends AppCompatActivity {
//    ActivityMyaccountAdddiachiBinding binding;
//    DatabaseHelper databaseHelper;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMyaccountAdddiachiBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        addEvents();
//        getDataFromIntent();
//        databaseHelper = new DatabaseHelper(this);
//        // Lấy dữ liệu từ Intent
//        Intent intent = getIntent();
//        if (intent != null) {
//            String userName = intent.getStringExtra("userName");
//            String userPhone = intent.getStringExtra("userPhone");
//            // Kiểm tra xem Intent có chứa dữ liệu không
//            if (userName != null && userPhone != null) {
//                // Nếu có, gán dữ liệu vào các trường
//                binding.txtHoten.setText(userName);
//                binding.txtsdt.setText(userPhone);
//            }
//        }
//
//
//    }
//
//    private void getDataFromIntent() {
//            // Kiểm tra xem Intent có dữ liệu từ Myaccount_Profile không
//            Intent intent = getIntent();
//            if (intent != null && intent.hasExtra("fromProfile")) {
//                // Nếu có, lấy dữ liệu từ Intent và đặt vào các trường tương ứng
//                String hoTen = intent.getStringExtra("hoTen");
//                String sdt = intent.getStringExtra("sdt");
//                // Đặt dữ liệu vào các trường
//                binding.txtHoten.setText(hoTen);
//                binding.txtsdt.setText(sdt);
//            }
//        }
//
//
//    private void addEvents() {
//        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Myaccount_adddiachi.this,Myaccount_Diachi.class);
//                startActivity(intent);
//            }
//        });
//        binding.btnupdatediachi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateAddress();
//                showAlerDialog();
//
//            }
//
//            private void updateAddress() {
//                String userName = binding.txtHoten.getText().toString();
//                String userPhone = binding.txtsdt.getText().toString();
//                String address = binding.txtDiachicuthe.getText().toString();
//
//                String loggedInPhone = getLoggedInPhone();
//                if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
//                    // Kiểm tra xem người dùng đã có địa chỉ chưa
//                    boolean hasAddress = hasAddress(loggedInPhone);
//                    if (!hasAddress) {
//                        // Nếu chưa có địa chỉ, thêm vào address1
//                        addAddress1(userName, userPhone, address, loggedInPhone);
//                    } else {
//                        // Nếu đã có địa chỉ, thêm vào address2
//                        addAddress2(userName, userPhone, address, loggedInPhone);
//                    }
//                }
//            }
//
//            private boolean hasAddress(String loggedInPhone) {
//                    SQLiteDatabase db = databaseHelper.getReadableDatabase();
//                    Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.USER +
//                            " WHERE " + DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});
//                    boolean hasAddress = false;
//                    if (cursor.moveToFirst()) {
//                        String address1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS));
//                        if (address1 != null && !address1.isEmpty()) {
//                            hasAddress = true;
//                        }
//                    }
//                    cursor.close();
//                    db.close();
//                    return hasAddress;
//                }
//
//
//            private void addAddress2(String userName, String userPhone, String address, String loggedInPhone) {
//                SQLiteDatabase db = databaseHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put(DatabaseHelper.COLUMN_USER_NAME2FORADDRESS2, userName);
//                values.put(DatabaseHelper.COLUMN_USER_PHONE2FORADDRESS2, userPhone);
//                values.put(DatabaseHelper.COLUMN_USER_ADDRESS2, address);
//
//                String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
//                String[] selectionArgs = {loggedInPhone};
//
//                long newRowId = db.update(DatabaseHelper.USER, values, selection, selectionArgs);
//                db.close();
//                if (newRowId != -1) {
//                    showToast("Địa chỉ đã được cập nhật");
//                } else {
//                    showToast("Có lỗi xảy ra khi cập nhật địa chỉ");
//                }
//            }
//
//            private void addAddress1(String userName, String userPhone, String address, String loggedInPhone) {
//                SQLiteDatabase db = databaseHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
////                values.put(DatabaseHelper.COLUMN_USER_NAME, userName);
////                values.put(DatabaseHelper.COLUMN_USER_PHONE, userPhone);
//                values.put(DatabaseHelper.COLUMN_USER_ADDRESS, address);
//
//                String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
//                String[] selectionArgs = {loggedInPhone};
//
//                long newRowId = db.update(DatabaseHelper.USER, values, selection, selectionArgs);
//                db.close();
//                if (newRowId != -1) {
//                    showToast("Địa chỉ đã được cập nhật");
//                } else {
//                    showToast("Có lỗi xảy ra khi cập nhật địa chỉ");
//                }
//            }
//            private String getLoggedInPhone() {
//                SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
//                return sharedPreferences.getString("loggedInPhone", "");
//            }
//
//            private void showToast(String message) {
//                Toast.makeText(Myaccount_adddiachi.this, message, Toast.LENGTH_SHORT).show();
//            }
//
//
//            private void showAlerDialog() {
//                ActivityDialogSaveBinding dialogsaveBinding = ActivityDialogSaveBinding.inflate(LayoutInflater.from(Myaccount_adddiachi.this));
//                AlertDialog.Builder builder = new AlertDialog.Builder(Myaccount_adddiachi.this)
//                        .setView(dialogsaveBinding.getRoot())
//                        .setCancelable(true);
//
//                // Create dialog
//                final AlertDialog dialog = builder.create();
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().setLayout(200, 200);
//                dialog.show();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//                    }
//                }, 1000);
//            }
//        });
//    }
//}
package com.example.skinlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityMyaccountAdddiachiBinding;

public class Myaccount_adddiachi extends AppCompatActivity {
    ActivityMyaccountAdddiachiBinding binding;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountAdddiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        getDataFromIntent();
        databaseHelper = new DatabaseHelper(this);
        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String userName = intent.getStringExtra("userName");
            String userPhone = intent.getStringExtra("userPhone");
            // Kiểm tra xem Intent có chứa dữ liệu không
            if (userName != null && userPhone != null) {
                // Nếu có, gán dữ liệu vào các trường
                binding.txtHoten.setText(userName);
                binding.txtsdt.setText(userPhone);
            }
        }


    }

    private void getDataFromIntent() {
        // Kiểm tra xem Intent có dữ liệu từ Myaccount_Profile không
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fromProfile")) {
            // Nếu có, lấy dữ liệu từ Intent và đặt vào các trường tương ứng
            String hoTen = intent.getStringExtra("hoTen");
            String sdt = intent.getStringExtra("sdt");
            // Đặt dữ liệu vào các trường
            binding.txtHoten.setText(hoTen);
            binding.txtsdt.setText(sdt);
        }
    }


    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount_adddiachi.this,Myaccount_Diachi.class);
                startActivity(intent);
            }
        });
        binding.btnupdatediachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
                showAlerDialog();

            }

            private void updateAddress() {
                String userName = binding.txtHoten.getText().toString();
                String userPhone = binding.txtsdt.getText().toString();
                String address = binding.txtDiachicuthe.getText().toString();

                String loggedInPhone = getLoggedInPhone();
                if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
                    // Kiểm tra xem người dùng đã có địa chỉ chưa
                    boolean hasAddress = hasAddress(loggedInPhone);
                    if (!hasAddress) {
                        // Nếu chưa có địa chỉ, thêm vào address1
                        addAddress1(userName, userPhone, address, loggedInPhone);
                    } else {
                        // Nếu đã có địa chỉ, thêm vào address2
                        addAddress2(userName, userPhone, address, loggedInPhone);
                    }
                }
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


            private void addAddress2(String userName, String userPhone, String address, String loggedInPhone) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_USER_NAME2FORADDRESS2, userName);
                values.put(DatabaseHelper.COLUMN_USER_PHONE2FORADDRESS2, userPhone);
                values.put(DatabaseHelper.COLUMN_USER_ADDRESS2, address);

                String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
                String[] selectionArgs = {loggedInPhone};

                long newRowId = db.update(DatabaseHelper.USER, values, selection, selectionArgs);
                db.close();
                if (newRowId != -1) {
                    showToast("Địa chỉ đã được cập nhật");
                } else {
                    showToast("Có lỗi xảy ra khi cập nhật địa chỉ");
                }
            }

            private void addAddress1(String userName, String userPhone, String address, String loggedInPhone) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
//                values.put(DatabaseHelper.COLUMN_USER_NAME, userName);
//                values.put(DatabaseHelper.COLUMN_USER_PHONE, userPhone);
                values.put(DatabaseHelper.COLUMN_USER_ADDRESS, address);

                String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
                String[] selectionArgs = {loggedInPhone};

                long newRowId = db.update(DatabaseHelper.USER, values, selection, selectionArgs);
                db.close();
                if (newRowId != -1) {
                    showToast("Địa chỉ đã được cập nhật");
                } else {
                    showToast("Có lỗi xảy ra khi cập nhật địa chỉ");
                }
            }
            private String getLoggedInPhone() {
                SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                return sharedPreferences.getString("loggedInPhone", "");
            }

            private void showToast(String message) {
                Toast.makeText(Myaccount_adddiachi.this, message, Toast.LENGTH_SHORT).show();
            }


            private void showAlerDialog() {
                ActivityDialogSaveBinding dialogsaveBinding = ActivityDialogSaveBinding.inflate(LayoutInflater.from(Myaccount_adddiachi.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Myaccount_adddiachi.this)
                        .setView(dialogsaveBinding.getRoot())
                        .setCancelable(true);

                // Create dialog
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(200, 200);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 1000);
            }
        });
    }
}