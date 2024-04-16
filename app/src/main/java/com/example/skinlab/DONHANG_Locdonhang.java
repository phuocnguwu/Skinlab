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
    Spinner spncategory;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainContaintFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spncategory = findViewById(R.id.spn_category);
        adapter = new CategoryAdapter(this, R.layout.donhang_category_selecteditem, getListCategory());
        spncategory.setAdapter(adapter);
        spncategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DONHANG_Locdonhang.this, "Vui lòng chọn bộ lọc!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Category> getListCategory() {
        List<Category> list = new ArrayList<>();
        list.add(new Category("Đang xử lý"));
        list.add(new Category("Đang vận chuyển"));
        list.add(new Category("Đang giao hàng"));
        list.add(new Category("Giao hàng thành công"));
        list.add(new Category("Đã hủy"));
        return list;
    }
}