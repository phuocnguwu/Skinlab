package com.example.skinlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.adapters.FeedbackAdapter;
import com.example.adapters.ProductDetailsAdapter;
import com.example.models.Feedback;
import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityProductDetailsBinding;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityProductDetailsDialogAddtocartBinding;

import java.util.ArrayList;
import java.util.List;

public class Product_Details extends AppCompatActivity {
    ActivityProductDetailsBinding binding;
    ProductDetailsAdapter adapter;

    FeedbackAdapter feedbackAdapter;
    ArrayList<Product> products;

    List<Feedback> feedbacks;
    Product selectedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initFeedback();
        loadFeedback();

        loadData();
        addEvents();
    }

    private void initFeedback() {

        feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback(R.drawable.account, "Emily", "07/04/2024", 5, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
        feedbacks.add(new Feedback(R.drawable.account, "John", "07/04/2024", 4, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
        feedbacks.add(new Feedback(R.drawable.account, "Belle", "07/04/2024", 5, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));

        Log.d("Product_Details", "Feedbacks size: " + feedbacks.size());
    }

    private void loadFeedback() {
            feedbackAdapter = new FeedbackAdapter(Product_Details.this, R.layout.list_item_feedback, feedbacks);
            binding.lvFeedback.setAdapter(feedbackAdapter);

    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.imvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product_Details.this, Donhang_dathang.class);
                startActivity(intent);
            }
        });
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityProductDetailsDialogAddtocartBinding DialogAddtocartBinding = ActivityProductDetailsDialogAddtocartBinding.inflate(LayoutInflater.from(Product_Details.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Product_Details.this)
                        .setView(DialogAddtocartBinding.getRoot())
                        .setCancelable(true);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(200, 200);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 1000);
            }
        });

        binding.txtProductFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product_Details.this, Product_Details_Feedback.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedProduct")) {

            if (intent != null && intent.hasExtra("selectedProduct")) {
                selectedProduct = (Product) intent.getSerializableExtra("selectedProduct");

                if (selectedProduct != null) {
//                    binding.imvProduct.setImageResource(selectedProduct.getPd_photo());
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
