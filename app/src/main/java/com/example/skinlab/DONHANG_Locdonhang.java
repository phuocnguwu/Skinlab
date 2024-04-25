package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adapters.CategoryAdapter;
import com.example.models.Category;
import com.example.skinlab.databinding.ActivityAboutskinIntroTestBinding;
import com.example.skinlab.databinding.ActivityMainContaintFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class DONHANG_Locdonhang extends AppCompatActivity {

    ActivityMainContaintFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainContaintFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}