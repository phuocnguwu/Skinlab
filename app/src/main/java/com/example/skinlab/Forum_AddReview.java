package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.skinlab.databinding.ActivityForumAddReviewBinding;

public class Forum_AddReview extends AppCompatActivity {
    ActivityForumAddReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum_AddReview.this, Forum_AddReview_Main.class);
                startActivity(intent);

            }
        });
        binding.lvReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Forum_AddReview.this, Forum_Detailed.class);
            }
        });
    }
}