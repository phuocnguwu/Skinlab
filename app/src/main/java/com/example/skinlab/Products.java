package com.example.skinlab;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.adapters.ProductAdapter;
import com.example.skinlab.databinding.ActivityProductsBinding;
import com.example.models.Product;

import java.util.ArrayList;

public class Products extends AppCompatActivity {
    ActivityProductsBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;

    public static final String DB_NAME = "Skinlab.db";
    public static final String DB_FOLDER = "databases";

    Context context;


    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "product";
    public static final String COLUMN_PD_ID = "pd_id";
    public static final String COLUMN_PD_NAME = "pd_name";
    public static final String COLUMN_PD_PRICE = "pd_price";
    public static final String COLUMN_PD_PRICE2 = "pd_price2";
    public static final String COLUMN_PD_BRAND = "pd_brand";
    public static final String COLUMN_PD_CATE = "pd_cate";
    public static final String COLUMN_PD_PHOTO = "pd_photo";
    public static final String COLUMN_PD_DES = "pd_des";
    public static final String COLUMN_PD_SKINTYPE = "pd_skintype";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadDb();

//        initData();
//        loadData();
        addEvents();

    }

    private void loadDb() {
        db = SQLiteDatabase.openDatabase(getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        products = new ArrayList<>();

        Cursor cursor = db.query(TBL_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int columnIndexId = cursor.getColumnIndex(COLUMN_PD_ID);
                int columnIndexName = cursor.getColumnIndex(COLUMN_PD_NAME);
                int columnIndexPrice = cursor.getColumnIndex(COLUMN_PD_PRICE);
                int columnIndexPrice2 = cursor.getColumnIndex(COLUMN_PD_PRICE2);
                int columnIndexBrand = cursor.getColumnIndex(COLUMN_PD_BRAND);
                int columnIndexCate = cursor.getColumnIndex(COLUMN_PD_CATE);
                int columnIndexDes = cursor.getColumnIndex(COLUMN_PD_DES);
                int columnIndexPhoto = cursor.getColumnIndex(COLUMN_PD_PHOTO);
                int columnIndexSkiptype = cursor.getColumnIndex(COLUMN_PD_SKINTYPE);

                if (columnIndexId != -1 && columnIndexName != -1 && columnIndexPrice != -1 &&
                        columnIndexPrice2 != -1 && columnIndexBrand != -1 && columnIndexCate != -1 &&
                        columnIndexDes != -1 && columnIndexPhoto != -1 && columnIndexSkiptype != -1) {

                    String pdId = cursor.getString(columnIndexId);
                    String pdName = cursor.getString(columnIndexName);
                    int pdPrice = cursor.getInt(columnIndexPrice);
                    int pdPrice2 = cursor.getInt(columnIndexPrice2);
                    String pdBrand = cursor.getString(columnIndexBrand);
                    String pdCate = cursor.getString(columnIndexCate);
                    String pdDes = cursor.getString(columnIndexDes);
                    String pdPhoto = cursor.getString(columnIndexPhoto);
                    String pdSkintype = cursor.getString(columnIndexPhoto);
//
//                    // Tạo đối tượng Product từ dữ liệu truy vấn
                    Product product = new Product(pdPhoto, pdId, pdName, pdPrice, pdPrice2, pdBrand, pdCate, pdDes, pdSkintype);
                    products.add(product);
                }
            }
            while (cursor.moveToNext()) ;
            cursor.close();
        }
        // Tạo layout manager cho RecyclerView (ở đây là GridLayoutManager)
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rcvProduct.setLayoutManager(layoutManager); // Đặt layout manager cho RecyclerView

        // Khởi tạo adapter và gán danh sách sản phẩm vào adapter
        adapter = new ProductAdapter(this, products);
        binding.rcvProduct.setAdapter(adapter); // Đặt adapter cho RecyclerView

        // Định nghĩa sự kiện click trên mỗi sản phẩm trong RecyclerView
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Xử lý khi người dùng nhấn vào một item trong RecyclerView
                // Khởi tạo Intent để chuyển sang màn hình chi tiết sản phẩm
                Intent intent = new Intent(context, Product_Details.class);
                // Đính kèm thông tin sản phẩm được chọn vào Intent
                intent.putExtra("selectedProduct", product);
                // Chuyển sang màn hình chi tiết sản phẩm
                startActivity(intent);
            }
        });
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.imvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Products.this, Donhang_dathang.class);
                startActivity(intent);
            }
        });
        binding.btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showFilterSheet();}

            private void showFilterSheet() {

                Dialog dialog = new Dialog(Products.this);
                dialog.setContentView(R.layout.filter_dialog);


                LinearLayout btnDefault = dialog.findViewById(R.id.btnFilter_Default);
                btnDefault.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                LinearLayout btnApply = dialog.findViewById(R.id.btnFilter_Apply);
                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
                layoutParams.width = width;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(layoutParams);

                dialog.getWindow().setWindowAnimations(R.style.FilterAnimation);
                dialog.getWindow().setGravity(Gravity.RIGHT);
                dialog.show();

    }

        });

    }

    private void loadData() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rcvProduct.setLayoutManager(layoutManager);

        adapter = new ProductAdapter(Products.this, products);
        binding.rcvProduct.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Xử lý khi người dùng nhấn vào một item trong RecyclerView
                Intent intent = new Intent(Products.this, Product_Details.class);
                intent.putExtra("selectedProduct", product);
                startActivity(intent);
            }
        });
    }

    private void initData() {
//        products = new ArrayList<>();
//        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,500000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,500000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,500000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,500000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,500000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,500000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
    }

}