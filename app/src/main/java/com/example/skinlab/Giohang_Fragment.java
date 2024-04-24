package com.example.skinlab;


import static java.lang.Integer.parseInt;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.adapters.AddressRecyclerAdapter;
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
    int totalPrice;
    private AddressRecyclerAdapter addressRecyclerAdapter;

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
        gioHangAdapter = new GioHangAdapter(getContext(), R.layout.giohang_item, gioHangItemList);
        gioHangAdapter.setTotalPriceListener(new GioHangAdapter.TotalPriceListener() {
            @Override
            public void onTotalPriceChanged(int totalPrice) {
                // Cập nhật giá trị tổng giá vào txtTotalPrice ở đây
                binding.txtTotalPrice.setText(String.valueOf(totalPrice));
            }
        });
        lvGiohang.setAdapter(gioHangAdapter);
        loadCartFromSharedPreferences(); // Load cart data from SharedPreferences
        addEvents();
        return view;


    }



    private void addEvents() {
        binding.btnDathangGiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalPrice();
                Intent intent;
                intent = new Intent(getActivity(), Donhang_dathang.class);
                intent.putExtra("totalPrice", totalPrice);
                Log.d("test intent giohang", "Giá trị đơn: " + totalPrice);
                startActivity(intent);
            }

        });
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
        int pd_price = parseInt(parts[2]);
        int pd_price2 = parseInt(parts[3]);
        String pd_cate = parts[4];
        String pd_brand = parts[5];
        String pd_photo = parts[6];
        String pd_des = parts[7];


        // Tạo đối tượng Product từ thông tin đã lấy được
        Product product;
        product = new Product(pd_id, pd_name, pd_price, pd_price2, pd_cate, pd_brand, pd_photo, pd_des);
        Log.d("product test", "product: " + product);


        binding.txtTotalPrice.setText(String.valueOf(calculateTotalPrice()));


        return product;
    }






    private String convertProductToString(Product product) {
        StringBuilder stringBuilder = new StringBuilder();


        // Thêm các trường thông tin của sản phẩm vào chuỗi
        stringBuilder.append(product.getPd_id()).append(","); // pd_id
        stringBuilder.append(product.getPd_name()).append(","); // pd_name
        stringBuilder.append(product.getPd_price()).append(","); // pd_price
        stringBuilder.append(product.getPd_price2()).append(","); // pd_price2
        stringBuilder.append(product.getPd_cate()).append(","); // pd_cate
        stringBuilder.append(product.getPd_brand()).append(","); // pd_brand
        stringBuilder.append(product.getPd_photo()).append(","); // pd_photo
        stringBuilder.append(product.getPd_des()).append(","); // pd_des


        // Trả về chuỗi kết quả
        return stringBuilder.toString();
    }


    private int calculateTotalPrice() {
        totalPrice = 0;
        for (Product product : gioHangItemList) {
            totalPrice += product.getPd_price() * product.getQuantity();
        }
        binding.txtTotalPrice.setText(String.valueOf(totalPrice));
        return totalPrice;
    }


    private void moveToDonHang() {
        // Tạo một Intent
        Intent intent = new Intent(getActivity(), Donhang_dathang.class);
        // Đính kèm dữ liệu sản phẩm vào Intent
        intent.putExtra("gioHangItemList", gioHangItemList);
        // Đính kèm dữ liệu totalPrice vào Intent
        intent.putExtra("totalPrice", totalPrice);

        // Chuyển sang Activity Donhang_dathang
        startActivity(intent);
    }


}
