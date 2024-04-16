package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.skinlab.databinding.ActivityMainContaintFragmentBinding;

public class MainActivity_containtFragment extends AppCompatActivity {
    ActivityMainContaintFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainContaintFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();

    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transction = manager.beginTransaction();
            Fragment fragment = null;
            if (v.equals(binding.btnTaikhoan))
                fragment = new MyAccountFragment();
            else if (v.equals(binding.btnTrangchu))
                fragment = new Homepage();
            else if (v.equals(binding.btnDiendan))
                fragment = new ForumFragment();

//            else if (v.equals(binding.btnfrag2))
//                fragment = new Fragment2();

            assert fragment != null;
            transction.replace(R.id.containerLayout, fragment);
            transction.addToBackStack(null);
            transction.commit();
        }
    };

    private void addEvents() {
        binding.btnTaikhoan.setOnClickListener(clickListener);
        binding.btnTrangchu.setOnClickListener(clickListener);

    }
}