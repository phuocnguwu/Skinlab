package com.example.skinlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.adapters.AppointmentAdapter;
import com.example.skinlab.databinding.ActivityAboutskinLichhenBinding;
import com.example.models.Appointment;
import com.example.skinlab.databinding.ActivityDialogYeucauDangnhapBinding;

import java.util.ArrayList;
import java.util.List;

public class Aboutskin_lichhen extends AppCompatActivity {
    DatabaseHelper databaseHelper;

    ActivityAboutskinLichhenBinding binding;
    public static final String DB_NAME = "Skinlab.db";

    public static final String TBL_NAME = "ABOUTSKIN_LICHTUVAN";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PHONE = "user_phone";
    public static final String COLUMN_USER_ADDRESS = "user_address";
    public static final String COLUMN_USER_DATE = "user_date";
    public static final String COLUMN_USER_TIME = "user_time";
    public static final String COLUMN_USER_CONTENT = "user_content";




    public static SQLiteDatabase db = null;


    ArrayList<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinLichhenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

        addEvents();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateDb();
        loadLichhen();

    }

    private void addEvents() {
        binding.btnDatlich.setOnClickListener(v -> {
            Intent intent = new Intent(Aboutskin_lichhen.this, Aboutskin_datlich.class);
            startActivity(intent);
        });

        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void loadLichhen() {
        String loggedInPhone = getLoggedInPhone();
        boolean loggedIn = Boolean.parseBoolean(isLoggedIn());
        if (loggedIn) {
            appointments = new ArrayList<>();
            db = SQLiteDatabase.openDatabase(getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME +
                    " WHERE " + DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});

//            Cursor cursor = db.query(TBL_NAME, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexName = cursor.getColumnIndex(COLUMN_USER_NAME);
                    int columnIndexPhone = cursor.getColumnIndex(COLUMN_USER_PHONE);
                    int columnIndexAddress = cursor.getColumnIndex(COLUMN_USER_ADDRESS);
                    int columnIndexDate = cursor.getColumnIndex(COLUMN_USER_DATE);
                    int columnIndexTime = cursor.getColumnIndex(COLUMN_USER_TIME);
                    int columnIndexContent = cursor.getColumnIndex(COLUMN_USER_CONTENT);


                    if (columnIndexName != -1 && columnIndexPhone != -1 &&
                            columnIndexAddress != -1 && columnIndexDate != -1
                            && columnIndexTime != -1 && columnIndexContent != -1){

                        String Name = cursor.getString(columnIndexName);
                        String Phone = cursor.getString(columnIndexPhone);
                        String Address = cursor.getString(columnIndexAddress);
                        String Date = cursor.getString(columnIndexDate);
                        String Time = cursor.getString(columnIndexTime);
                        String Content = cursor.getString(columnIndexContent);


                        Appointment appointment = new Appointment(Name, Phone, Address, Date, Time, Content);
                        appointments.add(appointment);
                    }
                } while (cursor.moveToNext());

                cursor.close();
            }
            AppointmentAdapter adapter = new AppointmentAdapter(this, appointments); // Sử dụng constructor với Context
            binding.rcvlichhen.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvlichhen.setAdapter(adapter);
        }

    }

    private void updateDb(){
        String loggedInPhone = getLoggedInPhone(); // Lấy user_phone từ SharedPreferences
        String userSkinType = databaseHelper.getUserSkinType(loggedInPhone);


// Kiểm tra xem có đăng nhập hay không
        boolean loggedIn = Boolean.parseBoolean(isLoggedIn());
        if (loggedIn) {
            if (userSkinType != null && !userSkinType.isEmpty()) {
                // Hiển thị dữ liệu từ cột user_skin nếu có
                binding.txtTinhtrangda.setText(userSkinType);
            } else {
                // Hiển thị "Không có" nếu cột user_skin trống hoặc dữ liệu là null
                binding.txtTinhtrangda.setText("Không có");
            }
        } else {
            // Hiển thị "Không có" nếu không có đăng nhập
            binding.txtTinhtrangda.setText("Không có");
            showAlerDialog();

        }
    }


    private String isLoggedIn() {
        // Sử dụng requireContext() để lấy Context của Fragment
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        return String.valueOf(loggedIn);
    }

    private String getLoggedInPhone() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }

    private void showAlerDialog() {
        ActivityDialogYeucauDangnhapBinding yeucauDangnhapBinding = ActivityDialogYeucauDangnhapBinding.inflate(LayoutInflater.from(Aboutskin_lichhen.this));
        AlertDialog.Builder builder = new AlertDialog.Builder(Aboutskin_lichhen.this)
                .setView(yeucauDangnhapBinding.getRoot())
                .setCancelable(true);

        // Create dialog
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(200, 200);
        dialog.show();
    }



}
