
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

import java.util.HashSet;
import java.util.Set;

public class MainActivity_containtFragment extends AppCompatActivity {
    ActivityMainContaintFragmentBinding binding;
    private boolean isLoggedIn = false;

    Product selectedProduct;

    //Giohang_Fragment giohangFragment;
    Giohang_Fragment giohangFragment = new Giohang_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainContaintFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isLoggedIn = readLoginStatus();
        replaceFragment(new Homepage());

        // Kiểm tra xem có dữ liệu sản phẩm trong SharedPreferences không
        SharedPreferences sharedPreferences = getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        Set<String> productSet = sharedPreferences.getStringSet("cart_items", new HashSet<String>());
        Log.d("Main", "Set String: " + productSet);


        addEvents();
    }

//    protected void onStart() {
//        super.onStart();
//        replaceFragment(new Homepage());
//        return;
//    }

//    protected void onResume() {
//        super.onResume();
//        Product selectedProduct = (Product) getIntent().getSerializableExtra("selectedProduct");
//
//        //Giohang_Fragment giohangFragment = new Giohang_Fragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("selectedProduct", selectedProduct);
//        giohangFragment.setArguments(bundle);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.containerLayout, giohangFragment)
//                .commit();
////        replaceFragment(new Homepage());
//    }

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

//    public void updateCartInSharedPreferences(Product product) {
//        Log.d("Main","chạy đc cái update r");
//        SharedPreferences sharedPreferences = getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Set<String> productSet = sharedPreferences.getStringSet("cart_items", new HashSet<String>());
//        // Convert product to string and add to productSet
//        productSet.add(convertProductToString(product));
//        editor.putStringSet("cart_items", productSet);
//        Log.d("sp main", "cíu" + productSet);
//        editor.apply();
//    }

//    private String convertProductToString(Product product) {
//        StringBuilder stringBuilder = new StringBuilder();
//
//        // Thêm các trường thông tin của sản phẩm vào chuỗi
//        stringBuilder.append(product.getPd_id()).append(","); // pd_id
//        stringBuilder.append(product.getPd_name()).append(","); // pd_name
//        stringBuilder.append(product.getPd_price()).append(","); // pd_price
//        stringBuilder.append(product.getPd_price2()).append(","); // pd_price2
//        stringBuilder.append(product.getPd_cate()).append(","); // pd_cate
//        stringBuilder.append(product.getPd_brand()).append(","); // pd_brand
//        stringBuilder.append(product.getPd_photo()).append(","); // pd_photo
//        stringBuilder.append(product.getPd_des()).append(","); // pd_des
//
//        // Trả về chuỗi kết quả
//        return stringBuilder.toString();
//    }

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
