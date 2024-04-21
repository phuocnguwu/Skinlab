//package com.example.skinlab;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.skinlab.databinding.ActivityQuenmatkhauMatkhaumoiBinding;
//public class Quenmatkhau_Matkhaumoi extends AppCompatActivity {
//    ActivityQuenmatkhauMatkhaumoiBinding binding;
//    EditText txtMatkhaumoi, txtNhaplaimatkhau;
//    Button btnluumatkhaumoi;
//    DatabaseHelper databaseHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityQuenmatkhauMatkhaumoiBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        databaseHelper = new DatabaseHelper(this);
//        txtMatkhaumoi = findViewById(R.id.txtMatkhaumoi);
//        txtNhaplaimatkhau = findViewById(R.id.txtNhaplaimatkhau);
//        btnluumatkhaumoi = findViewById(R.id.btnluumatkhaumoi);
//
//
//        btnluumatkhaumoi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveUserNewPassword();
//            }
//
//            private void saveUserNewPassword() {
//                String newPassword = txtMatkhaumoi.getText().toString().trim();
//
//                // Lấy số điện thoại của người dùng đang đăng nhập từ SharedPreferences
//                String loggedInPhone = getLoggedInPhone();
//
//                // Kiểm tra nếu số điện thoại đăng nhập không rỗng
//                if (!loggedInPhone.isEmpty()) {
//                    // Cập nhật mật khẩu mới cho người dùng trong cơ sở dữ liệu
//                    updateUserPasswordInDatabase(loggedInPhone, newPassword);
//                    showToast("Thông tin đã được cập nhật");
//                } else {
//                    showToast("Lỗi: Không thể lấy số điện thoại của người dùng");
//                }
//            }
//
//            private void updateUserPasswordInDatabase(String phone, String newPassword) {
//                // Get the writable database
//                SQLiteDatabase db = databaseHelper.getWritableDatabase();
//                // Create a ContentValues object to store the new values
//                ContentValues values = new ContentValues();
//                values.put(DatabaseHelper.COLUMN_USER_PASSWORD, newPassword);
//
//                // Define the selection clause to update the correct user
//                String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
//                String[] selectionArgs = {getLoggedInPhone()};
//
//                // Update the user information in the database
//                int rowsUpdated = db.update(DatabaseHelper.USER, values, selection, selectionArgs);
//
//                // Check if the update was successful
//                if (rowsUpdated > 0) {
//                    // Update successful, show a message to the user if needed
//                    binding.txtMatkhaumoi.setText(newPassword);
//                    // Log thông tin về số điện thoại được cập nhật mật khẩu mới
//                    Log.d("UpdatePassword", "Password updated for phone: " + getLoggedInPhone());
//                } else {
//                    // Update failed, show an error message to the user if needed
//                    Log.e("UpdatePassword", "Failed to update password for phone: " + getLoggedInPhone());
//                }
//
//                // Close the database
//                db.close();
//            }
//
//
//            private String getLoggedInPhone() {
//                SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
//                return sharedPreferences.getString("loggedInPhone", "");
//            }
//
//        });
//
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//}
//
////public class Quenmatkhau_Matkhaumoi extends AppCompatActivity {
////    ActivityQuenmatkhauMatkhaumoiBinding binding;
////    EditText txtMatkhaumoi, txtNhaplaimatkhau;
////    Button btnluumatkhaumoi;
////    DatabaseHelper databaseHelper;
////
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        binding = ActivityQuenmatkhauMatkhaumoiBinding.inflate(getLayoutInflater());
////        setContentView(binding.getRoot());
////        databaseHelper = new DatabaseHelper(this);
////        txtMatkhaumoi = findViewById(R.id.txtMatkhaumoi);
////        txtNhaplaimatkhau = findViewById(R.id.txtNhaplaimatkhau);
////        btnluumatkhaumoi = findViewById(R.id.btnluumatkhaumoi);
////        btnluumatkhaumoi.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                saveUserNewPassword();
////            }
////
////            private void saveUserNewPassword() {
////                String newPassword = binding.txtMatkhaumoi.getText().toString();
////
////                updateUserPasswordInDatabase(newPassword);
////                showToast("Thông tin đã được cập nhật");
////                // Gọi phương thức onUpdateUserInfo để thông báo rằng thông tin đã được cập nhật
//////                onUpdateUserPassword(newPassword);
////            }
////
//////            private void onUpdateUserPassword(String newPassword) {
//////
//////            }
////
////            private void updateUserPasswordInDatabase(String newPassword) {
////
////                // Get the writable database
////                SQLiteDatabase db = databaseHelper.getWritableDatabase();
////                // Create a ContentValues object to store the new values
////                ContentValues values = new ContentValues();
////                values.put(DatabaseHelper.COLUMN_USER_PASSWORD, newPassword);
////
////                // Define the selection clause to update the correct user
////                String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
////                String[] selectionArgs = {getLoggedInPhone()};
////
////                // Update the user information in the database
////                int rowsUpdated = db.update(DatabaseHelper.USER, values, selection, selectionArgs);
////
////                // Close the database
////                db.close();
////                // Check if the update was successful
////                if (rowsUpdated > 0) {
////                    // Update successful, show a message to the user if needed
////                    binding.txtMatkhaumoi.setText(newPassword);
////                } else {
////                    // Update failed, show an error message to the user if needed
////                }
////            }
////
////            private String getLoggedInPhone() {
////                SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
////                return sharedPreferences.getString("loggedInPhone", "");
////
////            }
////
////        });
////
////    }
////    private void showToast(String message) {
////        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
////    }
////
////
////
////}
package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skinlab.databinding.ActivityQuenmatkhauMatkhaumoiBinding;

public class Quenmatkhau_Matkhaumoi extends AppCompatActivity {
    ActivityQuenmatkhauMatkhaumoiBinding binding;
    EditText txtMatkhaumoi, txtNhaplaimatkhau;
    Button btnluumatkhaumoi;
    DatabaseHelper databaseHelper;
    String loggedInPhone; // Số điện thoại đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuenmatkhauMatkhaumoiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        txtMatkhaumoi = binding.txtMatkhaumoi;
        txtNhaplaimatkhau = binding.txtNhaplaimatkhau;
        btnluumatkhaumoi = binding.btnluumatkhaumoi;

        // Nhận số điện thoại từ Intent
        loggedInPhone = getIntent().getStringExtra("soDienThoai");

        btnluumatkhaumoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserNewPassword();
            }
        });
    }

    private void saveUserNewPassword() {
        String newPassword = txtMatkhaumoi.getText().toString().trim();

        // Kiểm tra xem mật khẩu mới có hợp lệ không
        if (isValidPassword(newPassword)) {
            // Cập nhật mật khẩu mới cho người dùng trong cơ sở dữ liệu
            updateUserPasswordInDatabase(loggedInPhone, newPassword);
            showToast("Mật khẩu đã được cập nhật");
        } else {
            showToast("Mật khẩu không hợp lệ");
        }
    }

    private boolean isValidPassword(String password) {
        // Thực hiện kiểm tra độ dài, kí tự đặc biệt, hoa/thường,...
        // Đây là ví dụ đơn giản, bạn có thể thay đổi theo yêu cầu của bạn
        return password.length() >= 6;
    }

    private void updateUserPasswordInDatabase(String phone, String newPassword) {
        // Get the writable database
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Create a ContentValues object to store the new values
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, newPassword);

        // Define the selection clause to update the correct user
        String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
        String[] selectionArgs = {phone};

        // Update the user information in the database
        int rowsUpdated = db.update(DatabaseHelper.USER, values, selection, selectionArgs);

        // Check if the update was successful
        if (rowsUpdated > 0) {
            // Update successful, show a message to the user if needed
            txtMatkhaumoi.setText(newPassword);
            // Log thông tin về số điện thoại được cập nhật mật khẩu mới
            Log.d("UpdatePassword", "Password updated for phone: " + phone);
        } else {
            // Update failed, show an error message to the user if needed
            Log.e("UpdatePassword", "Failed to update password for phone: " + phone);
        }

        // Close the database
        db.close();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
