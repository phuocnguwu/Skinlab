package com.example.skinlab;


import static android.app.PendingIntent.getActivity;


import static java.lang.Integer.parseInt;
import static java.security.AccessController.getContext;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import com.example.adapters.GioHangAdapter;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityDialogDathangBinding;
import com.example.skinlab.databinding.ActivityDonhangDathangBinding;
import com.example.skinlab.databinding.ActivityForumDialogSendBinding;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Donhang_dathang extends AppCompatActivity {


    ActivityDonhangDathangBinding binding;
    GioHangAdapter gioHangAdapter;
    private ArrayList<Product> gioHangItemList = new ArrayList<>();
    int totalPrice = 0;
    //int newQuantity;

    int receivedTotalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonhangDathangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receivedTotalPrice = extras.getInt("totalPrice");
            // Sử dụng receivedTotalPrice để hiển thị tổng giá tiền
        }
        Log.d("test intent donhang", "Giá trị đơn: " + receivedTotalPrice);
        gioHangAdapter = new GioHangAdapter(Donhang_dathang.this, R.layout.dathang_item_list, gioHangItemList);
        //int newQuantity = gioHangAdapter.getNewQuantity();
        //Log.d("quantity don hang", "quantity new: " + newQuantity);
        binding.lvOrderlist.setAdapter(gioHangAdapter);
        loadCartFromSharedPreferences();




        // Hiển thị dữ liệu sản phẩm lên ListView
        // Tạo adapter và thiết lập cho ListView
//        GioHangAdapter adapter = new GioHangAdapter(this, R.layout.dathang_item_list, gioHangItemList);
//        binding.lvOrderlist.setAdapter(adapter);


        addEvents();
    }


    private void loadCartFromSharedPreferences() {
        Log.d("Donhang", "chạy cái hàm mới gofi");
        SharedPreferences sharedPreferences = Donhang_dathang.this.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        Set<String> productSet = sharedPreferences.getStringSet("cart_items", new HashSet<String>());
        for (String productString : productSet) {
            // Parse the product string to Product object and add to gioHangItemList
            Product product = parseProductFromString(productString);
            if (product != null) {
                Log.d("Load cart", "product: " + product);
                gioHangItemList.add(product);
                gioHangAdapter.notifyDataSetChanged();
            } else {
                Log.d("Load cart", "product = null");
            }
        }
    }


    private Product parseProductFromString(String productString) {
        if (productString == null || productString.isEmpty()) {
            return null;
        }


        Log.d("Donhang_dathang", "Parsing product string: " + productString); // Log dữ liệu đầu vào


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


        // Kiểm tra chuỗi số lượng
        int quantity;
        try {
            quantity = Integer.parseInt(parts[8]);
        } catch (NumberFormatException e) {
            Log.w("Donhang_dathang", "Lỗi format số lượng: " + parts[8]);
            // Xử lý lỗi: bỏ qua quantity hoặc lưu trữ toàn bộ chuỗi mô tả
            quantity = 0;}


        Product product = new Product(pd_id, pd_name, pd_price, pd_price2, pd_cate, pd_brand, pd_photo, pd_des);
        product.setQuantity(quantity);


        // Tạo đối tượng Product từ thông tin đã lấy được
//        Product product;
//        product = new Product(pd_id, pd_name, pd_price, pd_price2, pd_cate, pd_brand, pd_photo, pd_des);
//        Log.d("donhang test", "product: " + product);


            binding.textView37.setText(String.valueOf(receivedTotalPrice));
            binding.textView34.setText(String.valueOf(receivedTotalPrice));



        //Log.d("donhangtest", "giá trị đơn: " + (String.valueOf(calculateTotalPrice())));
        //Log.d("hàm mới gemini", "quantity: " + product.getQuantity());
        return product;
    }


//    private int calculateTotalPrice() {
//        for (Product product : gioHangItemList) {
//            totalPrice += product.getPd_price() * product.getQuantity();
//            Log.d("calculatedonhang", "đụ má khổ ghê " + product.getPd_price());
//            Log.d("calculatedonhang", "đụ má khổ ghê " + product.getQuantity());
//            Log.d("calculatedonhang", "đụ má khổ ghê1 " + totalPrice);
//        }
//        Log.d("calculatedonhang", "đụ má khổ ghê2 " + totalPrice);
////        binding.textView37.setText(String.valueOf(totalPrice));
//        return totalPrice;
//    }


    private void addEvents() {
        binding.btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlerDialog();
                Intent intent = new Intent(Donhang_dathang.this, Donhang_Chitietdonhang.class);
                startActivity(intent);
            }
        });
    }


    private void showAlerDialog() {
        ActivityDialogDathangBinding dialogDathangBinding = ActivityDialogDathangBinding.inflate(LayoutInflater.from(Donhang_dathang.this));
        AlertDialog.Builder builder = new AlertDialog.Builder(Donhang_dathang.this)
                .setView(dialogDathangBinding.getRoot())
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
