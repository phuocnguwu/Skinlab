package com.example.skinlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityMyaccountBinding;
import com.example.skinlab.databinding.ActivityMyaccountProfileBinding;

public class Myaccount_Profile extends AppCompatActivity {
    ActivityMyaccountProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.btneditavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btnsavepf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlerDialog();
            }

            private void showAlerDialog() {
                ActivityDialogSaveBinding dialogsaveBinding = ActivityDialogSaveBinding.inflate(LayoutInflater.from(Myaccount_Profile.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Myaccount_Profile.this)
                        .setView(dialogsaveBinding.getRoot())
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




        });
    }
}