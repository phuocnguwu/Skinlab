package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.skinlab.adapters.ProductAdapter;
import com.example.skinlab.databinding.ActivityProductsBinding;
import com.example.skinlab.models.Product;

import java.util.ArrayList;

public class Products extends AppCompatActivity {
    ActivityProductsBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        loadData();
        addEvents();

    }

    private void addEvents() {
    }

    private void loadData() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rcvProduct.setLayoutManager(layoutManager);

        adapter = new ProductAdapter(Products.this, products);
        binding.rcvProduct.setAdapter(adapter);
    }

    private void initData() {
        products = new ArrayList<>();
        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));

    }

}