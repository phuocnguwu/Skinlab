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
    EditText edtOTP1, edtOTP2, edtOTP3, edtOTP4;
    String loggedInPhone; // Số điện thoại đăng nhập


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuenmatkhauNhapOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        edtOTP1 = findViewById(R.id.edtOTP1);
        edtOTP2 = findViewById(R.id.edtOTP2);
        edtOTP3 = findViewById(R.id.edtOTP3);
        edtOTP4 = findViewById(R.id.edtOTP4);
        loggedInPhone = getIntent().getStringExtra("soDienThoai");


        Button btntieptuc = findViewById(R.id.btntieptuc);
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraOTP();
            }
        });
    }

    private void kiemTraOTP() {
        String otp1 = edtOTP1.getText().toString().trim();
        String otp2 = edtOTP2.getText().toString().trim();
        String otp3 = edtOTP3.getText().toString().trim();
        String otp4 = edtOTP4.getText().toString().trim();

        // Kiểm tra xem tất cả các ô nhập OTP đã được điền đầy đủ chưa
        if (otp1.isEmpty() || otp2.isEmpty() || otp3.isEmpty() || otp4.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nếu đã nhập đủ OTP, chuyển qua trang Quenmatkhau_Matkhaumoi và truyền số điện thoại
        Intent intent = new Intent(Quenmatkhau_nhapOTP.this, Quenmatkhau_Matkhaumoi.class);
        intent.putExtra("soDienThoai", loggedInPhone);
        startActivity(intent);
    }
}
