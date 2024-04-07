package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.adapters.ProductDetailsAdapter;
import com.example.skinlab.databinding.ActivityProductDetailsBinding;
import com.example.models.Product;

import java.util.ArrayList;

public class Product_Details extends AppCompatActivity {
    ActivityProductDetailsBinding binding;
    ProductDetailsAdapter adapter;
    ArrayList<Product> products;
    Product selectedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        loadData();
        addEvents();
    }

    private void addEvents() {
    }

    private void loadData() {

//        products = new ArrayList<>();
//        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        Product product = products.get(0);
//        binding.imvProduct.setImageResource(product.getPd_photo());
//        binding.txtProductName.setText(product.getPd_name());
//        binding.txtProductBrand.setText(product.getPd_brand());
//        binding.txtProductPrice.setText(String.valueOf(product.getPd_price() + " đ"));
//        binding.txtProductPrice2.setText(String.valueOf(product.getPd_price2() + " đ"));
//        binding.txtProductDes.setText(product.getPd_des());


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedProduct")) {

            if (intent != null && intent.hasExtra("selectedProduct")) {
                selectedProduct = (Product) intent.getSerializableExtra("selectedProduct");

                if (selectedProduct != null) {
                    binding.imvProduct.setImageResource(selectedProduct.getPd_photo());
                    binding.txtProductName.setText(selectedProduct.getPd_name());
                    binding.txtProductBrand.setText(selectedProduct.getPd_brand());
                    binding.txtProductPrice.setText(String.valueOf(selectedProduct.getPd_price() + " đ"));
                    binding.txtProductPrice2.setText(String.valueOf(selectedProduct.getPd_price2() + " đ"));
                    binding.txtProductDes.setText(selectedProduct.getPd_des());

                }else{
                    Log.d("Product_Details", "selectedProduct is null");
                }
            }

    }

    }}
