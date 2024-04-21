package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skinlab.databinding.ActivityQuenmatkhauMatkhaumoiBinding;

public class Quenmatkhau_Matkhaumoi extends AppCompatActivity {
    ActivityQuenmatkhauMatkhaumoiBinding binding;
    EditText txtMatkhaumoi, txtNhaplaimatkhau;
    Button btnluumatkhaumoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuenmatkhauMatkhaumoiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txtMatkhaumoi = findViewById(R.id.txtMatkhaumoi);
        txtNhaplaimatkhau = findViewById(R.id.txtNhaplaimatkhau);
        btnluumatkhaumoi = findViewById(R.id.btnluumatkhaumoi);

        btnluumatkhaumoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


}
