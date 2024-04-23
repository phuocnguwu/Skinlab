package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.skinlab.databinding.ActivityAboutskinDatlichBinding;
import com.example.skinlab.databinding.ActivityAboutskinLichhenBinding;

public class Aboutskin_datlich extends AppCompatActivity {

    ActivityAboutskinDatlichBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinDatlichBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}