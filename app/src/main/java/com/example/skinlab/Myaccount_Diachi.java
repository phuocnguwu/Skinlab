package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.skinlab.databinding.ActivityMyaccountDiachiBinding;

public class Myaccount_Diachi extends AppCompatActivity {
    ActivityMyaccountDiachiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountDiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount_Diachi.this,Myaccount_adddiachi.class);
                startActivity(intent);
            }
        });
    }
}