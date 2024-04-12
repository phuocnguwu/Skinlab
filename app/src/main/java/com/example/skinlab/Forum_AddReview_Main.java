package com.example.skinlab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityForumAddReviewMainBinding;
import com.example.skinlab.databinding.ActivityForumDialogSendBinding;

public class Forum_AddReview_Main extends AppCompatActivity {
    ActivityForumAddReviewMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumAddReviewMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.btnSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlerDialog();
                Intent intent = new Intent(Forum_AddReview_Main.this, Forum_AddReview.class);
                startActivity(intent);
            }

        });
    }

    private void showAlerDialog() {
        ActivityForumDialogSendBinding forumdialogsendBinding = ActivityForumDialogSendBinding.inflate(LayoutInflater.from(Forum_AddReview_Main.this));
        AlertDialog.Builder builder = new AlertDialog.Builder(Forum_AddReview_Main.this)
                .setView(forumdialogsendBinding.getRoot())
                .setCancelable(true);

        // Create dialog
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
}