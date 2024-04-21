//package com.example.skinlab;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.skinlab.databinding.ActivityQuenmatkhauMatkhaumoiBinding;
//
//public class Quenmatkhau_Matkhaumoi extends AppCompatActivity {
//    ActivityQuenmatkhauMatkhaumoiBinding binding;
//    EditText txtMatkhaumoi, txtNhaplaimatkhau;
//    DatabaseHelper databaseHelper;
//    String loggedInPhone; // Số điện thoại đăng nhập
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityQuenmatkhauMatkhaumoiBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        addEvents();
//        databaseHelper = new DatabaseHelper(this);
//        txtNhaplaimatkhau = binding.txtNhaplaimatkhau;
//        // Nhận số điện thoại từ Intent
//        loggedInPhone = getIntent().getStringExtra("soDienThoai");
//    }
//
//    private void addEvents() {
//        binding.btnluumatkhaumoi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveUserNewPassword();
//            }
//        });
//    }
//
//    private void saveUserNewPassword() {
//        String newPassword = binding.txtMatkhaumoi.getText().toString().trim();
//        String confirmPassword = binding.txtNhaplaimatkhau.getText().toString().trim();
//
//        // Kiểm tra xem mật khẩu mới và xác nhận mật khẩu có trùng khớp không
//        if (!newPassword.equals(confirmPassword)) {
//            showToast("Mật khẩu không trùng khớp");
//            return; // Kết thúc phương thức nếu mật khẩu không trùng khớp
//        }
//        // Kiểm tra xem mật khẩu mới có hợp lệ không
//        if (isValidPassword(newPassword)) {
//            // Cập nhật mật khẩu mới cho người dùng trong cơ sở dữ liệu
//            updateUserPasswordInDatabase(loggedInPhone, newPassword);
//            showToast("Mật khẩu đã được cập nhật");
//        } else {
//            showToast("Mật khẩu không hợp lệ");
//        }
//    }
//    private boolean isValidPassword(String password) {
//        // Kiểm tra độ dài mật khẩu
//        if (password.length() < 6) {
//            return false;
//        }
//
//        // Kiểm tra sự có mặt của ít nhất một ký tự đặc biệt
//        String specialCharacters = "!@#$%^&*()_+{}:<>?|[]\\;'/,.";
//        boolean hasSpecialCharacter = false;
//        for (char c : password.toCharArray()) {
//            if (specialCharacters.contains(String.valueOf(c))) {
//                hasSpecialCharacter = true;
//                break;
//            }
//        }
//
//        return hasSpecialCharacter;
//    }
//
//
//    private void updateUserPasswordInDatabase(String phone, String newPassword) {
//        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, newPassword);
//        String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
//        String[] selectionArgs = {phone};
//        int rowsUpdated = db.update(DatabaseHelper.USER, values, selection, selectionArgs);
//        // Check if the update was successful
//        if (rowsUpdated > 0) {
//            // Update successful, show a message to the user if needed
//            txtMatkhaumoi.setText(newPassword);
//            // Log thông tin về số điện thoại được cập nhật mật khẩu mới
//            Log.d("UpdatePassword", "Password updated for phone: " + phone);
//        } else {
//            // Update failed, show an error message to the user if needed
//            Log.e("UpdatePassword", "Failed to update password for phone: " + phone);
//        }
//        // Close the database
//        db.close();
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//}
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
    DatabaseHelper databaseHelper;
    String loggedInPhone; // Số điện thoại đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuenmatkhauMatkhaumoiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        databaseHelper = new DatabaseHelper(this);
        txtMatkhaumoi = binding.txtMatkhaumoi;
        txtNhaplaimatkhau = binding.txtNhaplaimatkhau;
        // Nhận số điện thoại từ Intent
        loggedInPhone = getIntent().getStringExtra("soDienThoai");
    }

    private void addEvents() {
        binding.btnluumatkhaumoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserNewPassword();
            }
        });
    }

    private void saveUserNewPassword() {
        String newPassword = binding.txtMatkhaumoi.getText().toString().trim();
        String confirmPassword = binding.txtNhaplaimatkhau.getText().toString().trim();

        // Kiểm tra xem mật khẩu mới và xác nhận mật khẩu có trùng khớp không
        if (!newPassword.equals(confirmPassword)) {
            showToast("Mật khẩu không trùng khớp");
            return; // Kết thúc phương thức nếu mật khẩu không trùng khớp
        }

        // Kiểm tra độ dài mật khẩu
        if (newPassword.length() <= 6) {
            showToast("Mật khẩu phải nhiều hơn 6 ký tự");
            return;
        }

        // Kiểm tra xem mật khẩu mới có chứa ít nhất một ký tự đặc biệt không
        if (!isValidPassword(newPassword)) {
            showToast("Mật khẩu phải có ít nhất 1 ký tự đặc biệt");
            return;
        }

        // Cập nhật mật khẩu mới cho người dùng trong cơ sở dữ liệu
        updateUserPasswordInDatabase(loggedInPhone, newPassword);
        showToast("Mật khẩu đã được cập nhật");
    }

    private boolean isValidPassword(String password) {
        // Kiểm tra độ dài mật khẩu
        if (password.length() <= 6) {
            return false;
        }

        // Kiểm tra sự có mặt của ít nhất một ký tự đặc biệt
        String specialCharacters = "!@#$%^&*()_+{}:<>?|[]\\;'/,.";
        boolean hasSpecialCharacter = false;
        for (char c : password.toCharArray()) {
            if (specialCharacters.contains(String.valueOf(c))) {
                hasSpecialCharacter = true;
                break;
            }
        }

        return hasSpecialCharacter;
    }

    private void updateUserPasswordInDatabase(String phone, String newPassword) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, newPassword);
        String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
        String[] selectionArgs = {phone};
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
