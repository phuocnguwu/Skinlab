package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.skinlab.databinding.ActivityMainAboutSkinBinding;

public class Main_AboutSkin extends AppCompatActivity {

    ActivityMainAboutSkinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainAboutSkinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(Main_AboutSkin.this, Aboutskin_intro_test.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });

        binding.ibtnChutrinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(Main_AboutSkin.this, Aboutskin_chutrinh.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });

        binding.ibtnLichhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(Main_AboutSkin.this, Aboutskin_lichhen.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });

    }
}
