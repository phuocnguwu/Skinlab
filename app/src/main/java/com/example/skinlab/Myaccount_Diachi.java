package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.adapters.AddressRecyclerAdapter;
import com.example.models.Address;
import com.example.skinlab.databinding.ActivityMyaccountDiachiBinding;
import com.example.skinlab.databinding.FragmentMyAccountBinding;

import java.util.ArrayList;
import android.content.SharedPreferences;

public class Myaccount_Diachi extends AppCompatActivity {
    ActivityMyaccountDiachiBinding binding;
//    Databases db;
    AddressRecyclerAdapter adapter;
    ArrayList<Address> addresses;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountDiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        createDb();
        databaseHelper = new DatabaseHelper(this);

    }

    private void createDb() {
//        db = new Databases(this,Databases.DB_NAME, null, Databases.DB_VERSION);
//        db.createAddressSampleData(Myaccount_Diachi.this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadUserAddresses();
    }

    private void loadUserAddresses() {
        String loggedInPhone = getLoggedInPhone();
        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
            addresses = new ArrayList<>();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.USER +
                    " WHERE " + DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone});
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PHONE));
                    String address1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS));
                    String address2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ADDRESS2));
                    Address address = new Address(name, phone, address1, address2);
                    // Add the address to the list
                    addresses.add(address);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            // Display the addresses in the RecyclerView
            displayAddresses();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

        private void displayAddresses() {
            AddressRecyclerAdapter adapter = new AddressRecyclerAdapter(this, addresses);
            binding.rcvdiachi.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvdiachi.setAdapter(adapter);
        }


    private String getLoggedInPhone() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }


    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount_Diachi.this,Myaccount_adddiachi.class);
                startActivity(intent);
            }
        });

    }
}