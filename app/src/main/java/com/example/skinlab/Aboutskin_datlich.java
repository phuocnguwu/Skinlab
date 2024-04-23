package com.example.skinlab;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.models.Account;
import com.example.models.Appointment;
import com.example.skinlab.databinding.ActivityAboutskinDatlichBinding;
import com.example.skinlab.databinding.ActivityDialogSaveBinding;

public class Aboutskin_datlich extends AppCompatActivity {

    ActivityAboutskinDatlichBinding binding;
    SQLiteDatabase db;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinDatlichBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        addEvents();
//        binding.btnDatlich.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String hoTen = binding.txtHoten.getText().toString();
//                String sdt = binding.txtSdt.getText().toString();
//                String diaChi = binding.txtDiachi.getText().toString();
//                String ngayHen = binding.txtNgayhen.getText().toString();
//                String gioHen = binding.txtGiohen.getText().toString();
//                String noiDung = binding.txtNoidungtuvan.getText().toString();
//
//                databaseHelper.addAppointment(hoTen, sdt, diaChi, ngayHen, gioHen, noiDung);
//                finish();  // Kết thúc Activity và quay về màn hình trước
//            }
//        });
    }

    private void addEvents() {
        binding.btnDatlich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid()) {
                    showSuccessDialog();
                } else {
                    showToast("Vui lòng điền đầy đủ thông tin");
                }
            }


            private void showSuccessDialog() {
                String hoTen = binding.txtHoten.getText().toString().trim();
                String sdt = binding.txtSdt.getText().toString().trim();
                String diachi = binding.txtDiachi.getText().toString().trim();
                String ngayhen = binding.txtNgayhen.getText().toString().trim();
                String giohen = binding.txtGiohen.getText().toString().trim();
                String tuvan = binding.txtNoidungtuvan.getText().toString().trim();

                Appointment appointment = new Appointment(hoTen, sdt, diachi, ngayhen, giohen, tuvan);
                databaseHelper.addAppointment(appointment);

                ActivityDialogSaveBinding dialogsaveBinding = ActivityDialogSaveBinding.inflate(LayoutInflater.from(Aboutskin_datlich.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Aboutskin_datlich.this)
                        .setView(dialogsaveBinding.getRoot())
                        .setCancelable(true);

                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(200, 200);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        navigateTo();
                    }

                    private void navigateTo() {
                        Intent intent = new Intent(Aboutskin_datlich.this, Aboutskin_lichhen.class);
                        startActivity(intent);
                    }
                }, 1000);

            }

            private boolean isInputValid() {
                String hoTen = binding.txtHoten.getText().toString().trim();
                String sdt = binding.txtSdt.getText().toString().trim();
                String diachi = binding.txtDiachi.getText().toString().trim();
                String ngayhen = binding.txtNgayhen.getText().toString().trim();
                String giohen = binding.txtGiohen.getText().toString().trim();
                String tuvan = binding.txtNoidungtuvan.getText().toString().trim();

                // Kiểm tra xem các trường thông tin có rỗng không
                if (hoTen.isEmpty() || sdt.isEmpty() || diachi.isEmpty() || ngayhen.isEmpty() || giohen.isEmpty() || tuvan.isEmpty()) {
                    showToast("Vui lòng điền đầy đủ thông tin");
                    return false;
                }
                return true;
            }


            private void showToast(String message) {
                Toast.makeText(Aboutskin_datlich.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
