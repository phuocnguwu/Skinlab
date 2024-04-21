package com.example.skinlab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Skinlab.db";
    private static final int DATABASE_VERSION = 1;

    public AppointmentHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Appointment> getAppointmentsForUser(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"user_name", "user_phone", "user_address", "user_date"};
        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("ABOUTSKIN_LICHTUVAN", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                @SuppressLint("Range") String userPhone = cursor.getString(cursor.getColumnIndex("user_phone"));
                @SuppressLint("Range") String userAddress = cursor.getString(cursor.getColumnIndex("user_address"));
                @SuppressLint("Range") String userDate = cursor.getString(cursor.getColumnIndex("user_date"));

                Appointment appointment = new Appointment(userName, userPhone, userAddress, userDate);
                appointments.add(appointment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return appointments;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade table query
    }
}
