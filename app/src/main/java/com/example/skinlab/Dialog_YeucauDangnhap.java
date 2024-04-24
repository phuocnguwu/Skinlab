package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.skinlab.databinding.ActivityDialogYeucauDangnhapBinding;

public class Dialog_YeucauDangnhap extends AppCompatActivity {

    ActivityDialogYeucauDangnhapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogYeucauDangnhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


}