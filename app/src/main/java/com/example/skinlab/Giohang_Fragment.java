package com.example.skinlab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.adapters.GioHangAdapter;
import com.example.models.Product;
import com.example.skinlab.databinding.FragmentGiohangBinding;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Giohang_Fragment extends Fragment {
    private ListView lvGiohang;
    private GioHangAdapter gioHangAdapter;
    private ArrayList<Product> gioHangItemList = new ArrayList<>();
    FragmentGiohangBinding binding;
    Product selectedProduct;

    public Giohang_Fragment() {}

//    public static Giohang_Fragment newInstance(Bundle bundle) {
//        Giohang_Fragment giohangFragment = new Giohang_Fragment();
//        if (bundle != null) {
//            giohangFragment.setArguments(bundle);
//        }
//        return giohangFragment;
//    }

//    private ArrayList<Product> gioHangItemList;

//    public void addToCart(Product product, Context context) {
//        if (lvGiohang != null) {
//            if (gioHangAdapter == null) {
//                gioHangItemList.add(product);
//                gioHangAdapter = new GioHangAdapter(context, R.layout.giohang_item, gioHangItemList);
//                lvGiohang.setAdapter(gioHangAdapter);
//            } else {
//                gioHangItemList.add(product);
//                gioHangAdapter.notifyDataSetChanged();
//            }
//        } else {
//            // Log hoặc xử lý trường hợp lvGiohang không được tìm thấy
//            Log.e("Giohang_Fragment", "ListView lvGiohang is null");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGiohangBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        lvGiohang = view.findViewById(R.id.lvGiohang);
        gioHangAdapter = new GioHangAdapter(getContext(), R.layout.giohang_item, gioHangItemList);
        lvGiohang.setAdapter(gioHangAdapter);
        loadCartFromSharedPreferences(); // Load cart data from SharedPreferences
        return view;
    }

    // Method to load cart data from SharedPreferences
    private void loadCartFromSharedPreferences() {
        Log.d("Giohang_Fragment", "chạy cái hàm mới gofi");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        Set<String> productSet = sharedPreferences.getStringSet("cart_items", new HashSet<String>());
        for (String productString : productSet) {
            // Parse the product string to Product object and add to gioHangItemList
            Product product = parseProductFromString(productString);
            if (product != null) {
                Log.d("Giohang_Fragment", "product: " + product);
                gioHangItemList.add(product);
                gioHangAdapter.notifyDataSetChanged();
            } else {
                Log.d("Giohang_Fragment", "product = null");
            }
        }
//        gioHangAdapter = new GioHangAdapter(getActivity(), R.layout.giohang_item, gioHangItemList);
//        lvGiohang.setAdapter(gioHangAdapter);

    }

    // Method to parse product string from SharedPreferences to Product object
    // Method to parse product string from SharedPreferences to Product object
    private Product parseProductFromString(String productString) {
        if (productString == null || productString.isEmpty()) {
            return null;
        }

        Log.d("Giohang_Fragment", "Parsing product string: " + productString); // Log dữ liệu đầu vào

        // Phân tách chuỗi thành các phần tử bằng dấu phẩy (,)
        String[] parts = productString.split(",");

        // Kiểm tra xem có đủ thông tin để tạo đối tượng Product không
        if (parts.length < 8) {
            Log.e("Giohang_Fragment", "Invalid product string: " + productString); // Log nếu dữ liệu không hợp lệ
            return null;
        }

        // Lấy thông tin từ các phần tử của mảng
        String pd_id = parts[0];
        String pd_name = parts[1];
        int pd_price = Integer.parseInt(parts[2]);
        int pd_price2 = Integer.parseInt(parts[3]);
        String pd_cate = parts[4];
        String pd_brand = parts[5];
        String pd_photo = parts[6];
        String pd_des = parts[7];

        // Tạo đối tượng Product từ thông tin đã lấy được
        Product product;
        product = new Product(pd_id, pd_name, pd_price, pd_price2, pd_cate, pd_brand, pd_photo, pd_des);
        Log.d("product test", "product: " + product);

        return product;
    }


}
