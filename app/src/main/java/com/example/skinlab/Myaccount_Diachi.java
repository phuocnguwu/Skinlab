package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.adapters.AddressRecyclerAdapter;
import com.example.models.Address;
import com.example.skinlab.databinding.ActivityMyaccountDiachiBinding;
import com.example.skinlab.databinding.FragmentMyAccountBinding;

import java.util.ArrayList;

public class Myaccount_Diachi extends AppCompatActivity {
    ActivityMyaccountDiachiBinding binding;
//    Databases db;
    AddressRecyclerAdapter adapter;
    ArrayList<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountDiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        createDb();
    }

    private void createDb() {
//        db = new Databases(this,Databases.DB_NAME, null, Databases.DB_VERSION);
//        db.createAddressSampleData(Myaccount_Diachi.this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDb();
    }

    private void loadDb() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(Myaccount_Diachi.this, LinearLayoutManager.VERTICAL, false);
//        binding.rcvdiachi.setLayoutManager(layoutManager);
//        addresses = new ArrayList<>();
//        Cursor cursor = db.queryData("SELECT * FROM " + Databases.TBL_USER);
//        while (cursor.moveToNext()) {
//            addresses.add(new Address(
//                    cursor.getString(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getString(3)));
//        }
//        cursor.close();
//        adapter = new AddressRecyclerAdapter(Myaccount_Diachi.this, addresses);
//        binding.rcvdiachi.setAdapter(adapter);
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