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
import com.example.models.Order;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityDialogDathangBinding;
import com.example.skinlab.databinding.ActivityDonhangDathangBinding;
import com.example.skinlab.databinding.ActivityForumDialogSendBinding;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Donhang_dathang extends AppCompatActivity {


    ActivityDonhangDathangBinding binding;
    GioHangAdapter gioHangAdapter;
    private ArrayList<Product> gioHangItemList = new ArrayList<>();
    int receivedTotalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonhangDathangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receivedTotalPrice = extras.getInt("totalPrice");
        }
        gioHangAdapter = new GioHangAdapter(Donhang_dathang.this, R.layout.dathang_item_list, gioHangItemList);
        binding.lvOrderlist.setAdapter(gioHangAdapter);
        loadCartFromSharedPreferences();
        addEvents();
    }


    private void loadCartFromSharedPreferences() {
        SharedPreferences sharedPreferences = Donhang_dathang.this.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        Set<String> productSet = sharedPreferences.getStringSet("cart_items", new HashSet<String>());
        for (String productString : productSet) {
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
        Log.d("Donhang_dathang", "Parsing product string: " + productString);
        String[] parts = productString.split(",");
        if (parts.length < 8) {
            Log.e("Giohang_Fragment", "Invalid product string: " + productString);
            return null;
        }
        String pd_id = parts[0];
        String pd_name = parts[1];
        int pd_price = parseInt(parts[2]);
        int pd_price2 = parseInt(parts[3]);
        String pd_cate = parts[4];
        String pd_brand = parts[5];
        String pd_photo = parts[6];
        String pd_des = parts[7];
        int quantity;
        try {
            quantity = Integer.parseInt(parts[8]);
        } catch (NumberFormatException e) {
            Log.w("Donhang_dathang", "Lỗi format số lượng: " + parts[8]);
            quantity = 0;}
        Product product = new Product(pd_id, pd_name, pd_price, pd_price2, pd_cate, pd_brand, pd_photo, pd_des);
        product.setQuantity(quantity);
        binding.textView37.setText(String.valueOf(receivedTotalPrice));
        binding.textView34.setText(String.valueOf(receivedTotalPrice));

        return product;
    }

    private void addEvents() {
        binding.btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một đối tượng Order từ dữ liệu đã có
                //Order order = new Order("user_001", "product_order1_here", 1, "product_order2_here", 2, "product_order3_here", 3, "Đang xử lý");
                Order order = createOrderFromCart();

                // Chèn đối tượng Order vào cơ sở dữ liệu
                DatabaseHelper dbHelper = new DatabaseHelper(Donhang_dathang.this);
                if (dbHelper.insertOrder(order)) {
                    // Nếu chèn thành công, hiển thị thông báo hoặc thực hiện hành động tiếp theo ở đây
                    Log.d("Database", "Insert successful");
                } else {
                    // Nếu chèn không thành công, hiển thị thông báo hoặc xử lý lỗi ở đây
                    Log.d("Database", "Insert failed");
                }

                // Hiển thị dialog hoặc chuyển sang activity khác ở đây
                showAlerDialog();
                Intent intent = new Intent(Donhang_dathang.this, Donhang_Chitietdonhang.class);
                startActivity(intent);
            }
        });
    }

    private Order createOrderFromCart() {
        List<String> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        for (Product product : gioHangItemList) {
            productIds.add(product.getPd_id());
            quantities.add(product.getQuantity());
        }

        // Chuyển list thành mảng để truyền vào constructor của Order
        String[] productIdArray = productIds.toArray(new String[0]);
        Integer[] quantityArray = quantities.toArray(new Integer[0]);

        // Tạo một đối tượng Order từ dữ liệu đã có
        Order order = new Order("user_001", productIdArray[0], quantityArray[0], productIdArray[1], quantityArray[1], productIdArray[2], quantityArray[2], "Đang xử lý");
        return order;
    }


    private void showAlerDialog() {
        ActivityDialogDathangBinding dialogDathangBinding = ActivityDialogDathangBinding.inflate(LayoutInflater.from(Donhang_dathang.this));
        AlertDialog.Builder builder = new AlertDialog.Builder(Donhang_dathang.this)
                .setView(dialogDathangBinding.getRoot())
                .setCancelable(true);

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
