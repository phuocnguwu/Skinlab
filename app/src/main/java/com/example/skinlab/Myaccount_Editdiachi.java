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

import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityMyaccountEditdiachiBinding;

public class Myaccount_Editdiachi extends AppCompatActivity {
    ActivityMyaccountEditdiachiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountEditdiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount_Editdiachi.this,Myaccount_Diachi.class);
                startActivity(intent);
            }
        });
        binding.btneditdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newhoten = binding.txtHoten.getText().toString();
                String newsdt = binding.txtsdt.getText().toString();
                String newdiachi = binding.txtDiachicuthe.getText().toString();
                binding.txtHoten.setText(newhoten);
                binding.txtsdt.setText(newsdt);
                binding.txtDiachicuthe.setText(newdiachi);

                showAlerDialog();
            }

            private void showAlerDialog() {
                ActivityDialogSaveBinding dialogsaveBinding = ActivityDialogSaveBinding.inflate(LayoutInflater.from(Myaccount_Editdiachi.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Myaccount_Editdiachi.this)
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