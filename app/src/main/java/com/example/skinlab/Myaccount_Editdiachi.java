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

import com.example.models.Address;
import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityMyaccountEditdiachiBinding;

public class Myaccount_Editdiachi extends AppCompatActivity {
    ActivityMyaccountEditdiachiBinding binding;
    private Address selectedAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountEditdiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();

        // Nhận selectedAddress từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            selectedAddress = (Address) intent.getSerializableExtra("selectedAddress");
            if (selectedAddress != null) {
                // Đặt giá trị cho các EditText
                binding.txtHoten.setText(selectedAddress.getName());
                binding.txtsdt.setText(selectedAddress.getPhone());
                binding.txtDiachicuthe.setText(selectedAddress.getAddress());
            }
        }

    }

    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnupdatediachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newhoten = binding.txtHoten.getText().toString();
                String newsdt = binding.txtsdt.getText().toString();
                String newdiachi = binding.txtDiachicuthe.getText().toString();
                binding.txtHoten.setText(newhoten);
                binding.txtsdt.setText(newsdt);
                binding.txtDiachicuthe.setText(newdiachi);

                // Cập nhật thông tin vào cơ sở dữ liệu
                DatabaseHelper dbHelper = new DatabaseHelper(Myaccount_Editdiachi.this);
                dbHelper.updateUserAdress(selectedAddress.getPhone(), newhoten, null, newsdt, null, null, newdiachi);


                showAlerDialog();
                finish();
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