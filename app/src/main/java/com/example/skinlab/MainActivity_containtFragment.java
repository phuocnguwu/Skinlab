
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

import com.example.models.Product;
import com.example.skinlab.databinding.ActivityMainContaintFragmentBinding;

public class MainActivity_containtFragment extends AppCompatActivity {
    ActivityMainContaintFragmentBinding binding;
    private boolean isLoggedIn = false;

    Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainContaintFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        isLoggedIn = readLoginStatus();
        replaceFragment(new Homepage());
    }

//    protected void onStart() {
//        super.onStart();
//        replaceFragment(new Homepage());
//        return;
//    }

    protected void onResumeFragments() {
        super.onResumeFragments();
//        // Kiểm tra xem có dữ liệu trong Bundle không
//        Bundle bundle = getArguments();
//        if (bundle != null && bundle.containsKey("selectedProduct")) {
//            selectedProduct = (Product) bundle.getSerializable("selectedProduct");
//            Log.d("Product_Details", "selectedProduct: Price1 = " + selectedProduct);
//        } else {
//            Log.d("Product_Details", "vãi lồn");
//            replaceFragment(new Giohang_Fragment());
//        }
        Product selectedProduct = (Product) getIntent().getSerializableExtra("selectedProduct");

        Giohang_Fragment giohangFragment = new Giohang_Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedProduct", selectedProduct);
        giohangFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, giohangFragment)
                .commit();
//        replaceFragment(new Homepage());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        checkLoginAndReplaceFragment();

        // Kiểm tra fragment có phải là Giohang_Fragment không
//        if (fragment instanceof Giohang_Fragment) {
//            Giohang_Fragment giohangFragment = (Giohang_Fragment) fragment;
//            if (selectedProduct != null) {
//                // Tạo bundle và truyền selectedProduct vào Giohang_Fragment
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("selectedProduct", selectedProduct);
//                giohangFragment.setArguments(bundle);
//                Log.d("Product_Details", "selectedProduct: Price2 = " + bundle);
//            }
//        }
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
