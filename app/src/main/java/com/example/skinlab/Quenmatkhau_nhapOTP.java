package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.skinlab.databinding.ActivityQuenmatkhauNhapOtpBinding;

public class Quenmatkhau_nhapOTP extends AppCompatActivity {
    ActivityQuenmatkhauNhapOtpBinding binding;
    String loggedInPhone; // Số điện thoại đăng nhập


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuenmatkhauNhapOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        loggedInPhone = getIntent().getStringExtra("soDienThoai");


    }

    private void addEvents() {
        binding.btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraOTP();
            }
        });
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void kiemTraOTP() {
        String otp1 = binding.edtOTP1.getText().toString().trim();
        String otp2 = binding.edtOTP2.getText().toString().trim();
        String otp3 = binding.edtOTP3.getText().toString().trim();
        String otp4 = binding.edtOTP4.getText().toString().trim();
        // Kiểm tra xem tất cả các ô nhập OTP đã được điền đầy đủ chưa
        if (otp1.isEmpty() || otp2.isEmpty() || otp3.isEmpty() || otp4.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập OTP", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Quenmatkhau_nhapOTP.this, Quenmatkhau_Matkhaumoi.class);
        intent.putExtra("soDienThoai", loggedInPhone);
        startActivity(intent);
    }
}
