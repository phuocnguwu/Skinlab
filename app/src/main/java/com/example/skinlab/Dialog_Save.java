package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.skinlab.databinding.ActivityDialogSaveBinding;

public class Dialog_Save extends AppCompatActivity {
    ActivityDialogSaveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogSaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}