package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.skinlab.databinding.ActivityForumDialogSendBinding;

public class Forum_Dialog_Send extends AppCompatActivity {

    ActivityForumDialogSendBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumDialogSendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}