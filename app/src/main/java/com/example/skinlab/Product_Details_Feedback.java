package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.adapters.FeedbackAdapter;
import com.example.adapters.ProductDetailsAdapter;
import com.example.models.Feedback;
import com.example.skinlab.databinding.ActivityProductDetailsBinding;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityProductDetailsFeedbackBinding;

import java.util.ArrayList;
import java.util.List;

public class Product_Details_Feedback extends AppCompatActivity {
    ActivityProductDetailsFeedbackBinding binding;
    ProductDetailsAdapter adapter;

    FeedbackAdapter feedbackAdapter;
    ArrayList<Product> products;

    List<Feedback> feedbacks;
    Product selectedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProductDetailsFeedbackBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        initFeedback();
        loadFeedback();
        addEvents();
    }

    private void initFeedback() {

//        feedbacks = new ArrayList<>();
//        feedbacks.add(new Feedback(R.drawable.account, "Emily", "07/04/2024", 5, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
//        feedbacks.add(new Feedback(R.drawable.account, "John", "07/04/2024", 4, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
//        feedbacks.add(new Feedback(R.drawable.account, "Belle", "07/04/2024", 5, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
//
//        Log.d("Product_Details", "Feedbacks size: " + feedbacks.size());
    }

    private void loadFeedback() {
        feedbackAdapter = new FeedbackAdapter(Product_Details_Feedback.this, R.layout.list_item_feedback, feedbacks);
        binding.lvFeedback.setAdapter(feedbackAdapter);
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    }
