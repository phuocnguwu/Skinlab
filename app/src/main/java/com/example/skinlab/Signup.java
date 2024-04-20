package com.example.skinlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.models.Account;
import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;


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



            private boolean isInputValid() {
                String hoTen = binding.edtHoten.getText().toString().trim();
                String sdt = binding.edtSdt.getText().toString().trim();
                String email = binding.edtEmail.getText().toString().trim();
                String matKhau = binding.edtMatkhau.getText().toString().trim();
                String dob = binding.edtDOB.getText().toString().trim();
                String gioiTinh = binding.edtGioitinh.getText().toString().trim();

                // Kiểm tra xem các trường thông tin có rỗng không
                return !hoTen.isEmpty() && !sdt.isEmpty() && !email.isEmpty() && !matKhau.isEmpty() && !dob.isEmpty() && !gioiTinh.isEmpty();
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


            private void showToast(String message) {
                Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
