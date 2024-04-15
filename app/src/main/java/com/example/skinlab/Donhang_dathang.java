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

import com.example.skinlab.databinding.ActivityDialogDathangBinding;
import com.example.skinlab.databinding.ActivityDonhangDathangBinding;
import com.example.skinlab.databinding.ActivityForumDialogSendBinding;

public class Donhang_dathang extends AppCompatActivity {

    ActivityDonhangDathangBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonhangDathangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlerDialog();
                Intent intent = new Intent(Donhang_dathang.this, Donhang_Chitietdonhang.class);
                startActivity(intent);
            }
        });
    }

    private void showAlerDialog() {
        ActivityDialogDathangBinding dialogDathangBinding = ActivityDialogDathangBinding.inflate(LayoutInflater.from(Donhang_dathang.this));
        AlertDialog.Builder builder = new AlertDialog.Builder(Donhang_dathang.this)
                .setView(dialogDathangBinding.getRoot())
                .setCancelable(true);

        // Create dialog
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(200, 200);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }
}