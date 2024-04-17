package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.skinlab.databinding.ActivityAboutskinChutrinhBinding;

public class Aboutskin_chutrinh extends AppCompatActivity {

    ActivityAboutskinChutrinhBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinChutrinhBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void addEvents() {
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(Aboutskin_chutrinh.this, Aboutskin_intro_test.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });
    }
}