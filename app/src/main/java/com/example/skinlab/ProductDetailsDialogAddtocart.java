package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.skinlab.databinding.ActivityProductDetailsDialogAddtocartBinding;

public class ProductDetailsDialogAddtocart extends AppCompatActivity {

    ActivityProductDetailsDialogAddtocartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProductDetailsDialogAddtocartBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}