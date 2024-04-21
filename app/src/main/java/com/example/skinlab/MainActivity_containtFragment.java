
package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.skinlab.databinding.ActivityMainContaintFragmentBinding;

public class MainActivity_containtFragment extends AppCompatActivity {
    ActivityMainContaintFragmentBinding binding;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainContaintFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        isLoggedIn = readLoginStatus();
        replaceFragment(new Homepage());

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        checkLoginAndReplaceFragment();

    }

    private void checkLoginAndReplaceFragment() {
        isLoggedIn = readLoginStatus();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transction = manager.beginTransaction();
            Fragment fragment = null;

            if (v.equals(binding.btnTaikhoan)) {
                if (isLoggedIn) {
                    fragment = new MyAccountFragment();
                } else {
                    fragment = new LoginFragment();
                } }
            else if (v.equals(binding.btnTrangchu))
                fragment = new Homepage();
            else if (v.equals(binding.btnDiendan))
                fragment = new ForumFragment();
            else if (v.equals(binding.btnTuvan))
                fragment = new AboutskinFragment();
            else if (v.equals(binding.btnDathang))
                fragment = new Giohang_Fragment();
            assert fragment != null;
            transction.replace(R.id.containerLayout, fragment);
            transction.addToBackStack(null);
            transction.commit();
            checkLoginAndReplaceFragment();
        }
    };
    private void addEvents() {
        binding.btnTaikhoan.setOnClickListener(clickListener);
        binding.btnTrangchu.setOnClickListener(clickListener);
        binding.btnDiendan.setOnClickListener(clickListener);
        binding.btnTuvan.setOnClickListener(clickListener);
        binding.btnDathang.setOnClickListener(clickListener);
    }
    private boolean readLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}
