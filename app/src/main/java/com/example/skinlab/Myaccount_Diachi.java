package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.adapters.AddressRecyclerAdapter;
import com.example.models.Address;
import com.example.skinlab.databinding.ActivityMyaccountDiachiBinding;
import com.example.skinlab.databinding.FragmentMyAccountBinding;

import java.util.ArrayList;

public class Myaccount_Diachi extends AppCompatActivity {
    ActivityMyaccountDiachiBinding binding;
    Databases db;
    AddressRecyclerAdapter adapter;
    ArrayList<Address> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountDiachiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        createdb();
    }

    private void createdb() {


    }

    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount_Diachi.this, FragmentMyAccountBinding.class);
                startActivity(intent);
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