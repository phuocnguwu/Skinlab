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
import com.example.skinlab.databinding.ActivityMyaccountAdddiachiBinding;

public class Myaccount_adddiachi extends AppCompatActivity {
    ActivityMyaccountAdddiachiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountAdddiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount_adddiachi.this,Myaccount_Diachi.class);
                startActivity(intent);
            }
        });
        binding.btnadddiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlerDialog();
                addInfo();

            }

            private void addInfo() {
                // Lấy thông tin từ các trường EditText
                String hoten = binding.txtHoten.getText().toString();
                String sdt = binding.txtsdt.getText().toString();
                String diachi = binding.txtDiachicuthe.getText().toString();

                // Lấy thông tin từ Spinner
//                String tinh = binding.txtTinh.getSelectedItem().toString();
//                String thanhpho = binding.txtThanhpho.getSelectedItem().toString();
//                String huyen = binding.huyenxa.getSelectedItem().toString();

                // Kiểm tra nếu Checkbox được chọn
//                boolean diachimacdinh = binding.cbdiachimacdinh.isChecked();


                binding.txtHoten.setText(hoten);
                binding.txtsdt.setText(sdt);
                binding.txtDiachicuthe.setText(diachi);
//                binding.txtTinh.setSelected(Boolean.parseBoolean(tinh));
//                binding.txtThanhpho.setSelected(Boolean.parseBoolean(thanhpho));
//                binding.huyenxa.setSelected(Boolean.parseBoolean(huyen));
//                binding.cbdiachimacdinh.setChecked(diachimacdinh);

            }

            private void showAlerDialog() {
                ActivityDialogSaveBinding dialogsaveBinding = ActivityDialogSaveBinding.inflate(LayoutInflater.from(Myaccount_adddiachi.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Myaccount_adddiachi.this)
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