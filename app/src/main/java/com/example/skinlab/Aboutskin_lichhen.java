package com.example.skinlab;

import static com.example.skinlab.Products.db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.adapters.AppointmentAdapter;
import com.example.models.Feedback;
import com.example.skinlab.databinding.ActivityAboutskinLichhenBinding;
import com.example.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class Aboutskin_lichhen extends AppCompatActivity {

    ActivityAboutskinLichhenBinding binding;
    List<Appointment> AppointmentList;

    public static final String DB_NAME = "Skinlab.db";

    public static final String TABLE_NAME_ABOUTSKIN_LICHTUVAN = "aboutskin_lichtuvan";


//    AppointmentHelper appointmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinLichhenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadLichhen();
        addEvents();

        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("login_pref", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        // Khởi tạo AppointmentHelper
//        appointmentHelper = new AppointmentHelper(this);

        // Lấy danh sách lịch hẹn cho userId và hiển thị lên RecyclerView
//        List<Appointment> appointments = appointmentHelper.getAppointmentsForUser(userId);
//        AppointmentAdapter adapter = new AppointmentAdapter(this, appointments);
//        binding.rcvlichhen.setLayoutManager(new LinearLayoutManager(this));
//        binding.rcvlichhen.setAdapter(adapter);
    }

    private void loadLichhen() {
        AppointmentList = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);


    }

    private void addEvents() {
        binding.btnDatlich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Activity Aboutskin_datlich
                Intent intent = new Intent(Aboutskin_lichhen.this, Aboutskin_datlich.class);
                startActivity(intent);
            }
        });
    }
}
