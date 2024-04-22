package com.example.skinlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.models.Account;
import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;
    boolean isPasswordVisible = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        addEvents();
    }

    private void addEvents() {



        binding.btnsavepf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid()) {
                    showSuccessDialog();
                } else {
                    showToast("Vui lòng điền đầy đủ thông tin");
                }
            }


            private void showSuccessDialog() {
                String hoTen = binding.edtHoten.getText().toString().trim();
                String sdt = binding.edtSdt.getText().toString().trim();
                String email = binding.edtEmail.getText().toString().trim();
                String matKhau = binding.edtMatkhau.getText().toString().trim();
                String dob = binding.edtDOB.getText().toString().trim();
                String gioiTinh = binding.edtGioitinh.getText().toString().trim();

                Account user = new Account(hoTen, sdt, email, matKhau, dob, gioiTinh);
                databaseHelper.saveUserToDatabase(user);

                ActivityDialogSaveBinding dialogsaveBinding = ActivityDialogSaveBinding.inflate(LayoutInflater.from(Signup.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this)
                        .setView(dialogsaveBinding.getRoot())
                        .setCancelable(true);

                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(200, 200);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        navigateToLoginFragment();
                    }

                    private void navigateToLoginFragment() {
                        Intent intent = new Intent(Signup.this, MainActivity_containtFragment.class);
                        startActivity(intent);
                    }
                }, 1000);


            }

            private boolean isValidPhoneNumber(String phoneNumber) {
                // Kiểm tra số điện thoại bắt đầu từ số 0 và có từ 10 đến 13 ký tự
                if (!phoneNumber.matches("^0[0-9]{9,12}$")) {
                    showToast("Số điện thoại sai định dạng");
                    return false;
                }
                if (databaseHelper.checkSoDienThoaiTonTai(phoneNumber)) {
                    showToast("Số điện thoại đã được đăng ký");
                    return false;
                }
                return true;
            }

            private boolean isValidEmail(String email) {
                // Kiểm tra định dạng email
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showToast("Email sai định dạng");
                    return false;
                }
                if (databaseHelper.checkEmailTonTai(email)) {
                    showToast("Email đã được đăng ký");
                    return false;
                }
                return true;
            }

            private boolean isValidPassword(String password) {
                // Kiểm tra mật khẩu có ít nhất 6 ký tự và chứa ít nhất một ký tự đặc biệt
                if (password.length() < 6) {
                    showToast("Mật khẩu phải dài hơn 6 ký tự");
                    return false;
                }
                if (!password.matches(".*[!@#$%^&*()_+{}:<>?|\\[\\]\\\\;'\\/,.].*")) {
                    showToast("Mật khẩu phải có ít nhất 1 ký tự đặc biệt");
                    return false;
                }
                return true;
            }

            private boolean isValidDOB(String dob) {
                // Kiểm tra định dạng ngày sinh theo mẫu dd/mm/yyyy
                if (!dob.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                    showToast("Ngày sinh sai định dạng");
                    return false;
                }
                return true;
            }

            private boolean isInputValid() {
                String hoTen = binding.edtHoten.getText().toString().trim();
                String sdt = binding.edtSdt.getText().toString().trim();
                String email = binding.edtEmail.getText().toString().trim();
                String matKhau = binding.edtMatkhau.getText().toString().trim();
                String dob = binding.edtDOB.getText().toString().trim();
                String gioiTinh = binding.edtGioitinh.getText().toString().trim();

                // Kiểm tra xem các trường thông tin có rỗng không
                if (hoTen.isEmpty() || sdt.isEmpty() || email.isEmpty() || matKhau.isEmpty() || dob.isEmpty() || gioiTinh.isEmpty()) {
                    showToast("Vui lòng điền đầy đủ thông tin");
                    return false;
                }
                // Kiểm tra định dạng số điện thoại
                if (!isValidPhoneNumber(sdt)) {
                    return false;
                }

                // Kiểm tra email
                if (!isValidEmail(email)) {
                    return false;
                }

                // Kiểm tra mật khẩu
                if (matKhau.length() < 6 || !isValidPassword(matKhau)) {
                    return false;
                }
                // Kiểm tra định dạng ngày sinh
                if (!isValidDOB(dob)) {
                    return false;
                }
                return true;
            }


            private void showToast(String message) {
                Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
            }

        });
    }

}
