package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.adapters.AppointmentAdapter;
import com.example.skinlab.databinding.ActivityAboutskinLichhenBinding;
import com.example.models.Appointment;

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


    public static SQLiteDatabase db = null;


    ArrayList<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinLichhenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

        addEvents();
        loadLichhen();

    }

    private void loadLichhen() {
        String loggedInPhone = getLoggedInPhone();
        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
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

                    if (columnIndexName != -1 && columnIndexPhone != -1 &&
                            columnIndexAddress != -1 && columnIndexDate != -1){

                        String Name = cursor.getString(columnIndexName);
                        String Phone = cursor.getString(columnIndexPhone);
                        String Address = cursor.getString(columnIndexAddress);
                        String Date = cursor.getString(columnIndexDate);

                        Appointment appointment = new Appointment(Name, Phone, Address, Date);
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

    private String getLoggedInPhone() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }


    private void addEvents() {
        binding.btnDatlich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aboutskin_lichhen.this, Aboutskin_datlich.class);
                startActivity(intent);
            }
        });
    }
}
