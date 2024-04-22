package com.example.skinlab;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.adapters.ProductAdapter;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityAboutskinChutrinhBinding;

import java.util.ArrayList;
import java.util.List;

public class Aboutskin_chutrinh extends AppCompatActivity {

    ActivityAboutskinChutrinhBinding binding;
    ProductAdapter adapter;

    ArrayList<Product> products;

    DatabaseHelper dbHelper;

    public static final String DB_NAME = "Skinlab.db";
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
        binding = ActivityAboutskinChutrinhBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);

        loadDb();
        addEvents();


    }

    @Override
    public void onResume() {
        updateDb();
        super.onResume();
    }
    private void loadDb() {
        db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
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
                int columnIndexSkintype = cursor.getColumnIndex(COLUMN_PD_SKINTYPE);

                if (columnIndexId != -1 && columnIndexName != -1 && columnIndexPrice != -1 &&
                        columnIndexPrice2 != -1 && columnIndexBrand != -1 && columnIndexCate != -1 &&
                        columnIndexDes != -1 && columnIndexPhoto != -1 && columnIndexSkintype != -1) {

                    String pdId = cursor.getString(columnIndexId);
                    String pdName = cursor.getString(columnIndexName);
                    int pdPrice = cursor.getInt(columnIndexPrice);
                    int pdPrice2 = cursor.getInt(columnIndexPrice2);
                    String pdBrand = cursor.getString(columnIndexBrand);
                    String pdCate = cursor.getString(columnIndexCate);
                    String pdDes = cursor.getString(columnIndexDes);
                    String pdPhoto = cursor.getString(columnIndexPhoto);
                    String pdSkintype = cursor.getString(columnIndexSkintype);

                    Log.d("Database Values", "pdId: " + pdId + ", pdName: " + pdName + ", pdPrice: " + pdPrice + ", pdPrice2: " + pdPrice2 +
                            ", pdBrand: " + pdBrand + ", pdCate: " + pdCate + ", pdDes: " + pdDes + ", pdPhoto: " + pdPhoto +
                            ", pdSkintype: " + pdSkintype);
//
//                    // Tạo đối tượng Product từ dữ liệu truy vấn
                    Product product = new Product(pdPhoto, pdId, pdName, pdPrice, pdPrice2, pdBrand, pdCate, pdDes, pdSkintype);
                    products.add(product);
                }
            }
            while (cursor.moveToNext()) ;
            cursor.close();
        }

        String userSkinType = dbHelper.getUserSkinType(getLoggedInPhone());
        ArrayList<Product> suaruamat = filterProductsBySkinType(products, "Sữa rửa mặt", userSkinType);
        binding.rcvSuaruamat.setAdapter(new ProductAdapter(this, suaruamat));

        ArrayList<Product> toner = filterProductsBySkinType(products, "Nước dưỡng", userSkinType);
        binding.rcvToner.setAdapter(new ProductAdapter(this, toner));

        ArrayList<Product> serum = filterProductsBySkinType(products, "Tinh chất", userSkinType);
        binding.rcvSerum.setAdapter(new ProductAdapter(this, serum));

        ArrayList<Product> kem = filterProductsBySkinType(products, "Kem dưỡng", userSkinType);
        binding.rcvKem.setAdapter(new ProductAdapter(this, kem));

        Log.d("loadDb", "Sữa rửa mặt size: " + suaruamat.size());
        Log.d("loadDb", "Toner size: " + toner.size());
        Log.d("loadDb", "Serum size: " + serum.size());
        Log.d("loadDb", "Kem dưỡng size: " + kem.size());


        GridLayoutManager layoutManagerSuaruamat = new GridLayoutManager(this, 3);
        GridLayoutManager layoutManagerToner = new GridLayoutManager(this, 3);
        GridLayoutManager layoutManagerSerum = new GridLayoutManager(this, 3);
        GridLayoutManager layoutManagerKem = new GridLayoutManager(this, 3);

        binding.rcvSuaruamat.setLayoutManager(layoutManagerSuaruamat);
        binding.rcvToner.setLayoutManager(layoutManagerToner);
        binding.rcvSerum.setLayoutManager(layoutManagerSerum);
        binding.rcvKem.setLayoutManager(layoutManagerKem);

//        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Product product) {
//                Intent intent = new Intent(Aboutskin_chutrinh.this, Product_Details.class);
//                intent.putExtra("selectedProduct", product);
//                startActivity(intent);
//            }
//        });
    }

    private ArrayList<Product> filterProductsBySkinType(ArrayList<Product> products, String category, String userSkinType) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            // Lọc sản phẩm theo category và userSkinType
            if (product.getPd_cate().equalsIgnoreCase(category) && product.getPd_skintype().equalsIgnoreCase(userSkinType)) {
                filteredProducts.add(product);
            }
        }
        for (Product product : filteredProducts) {
            Log.d("Filtered Product", "Product Name: " + product.getPd_name() + ", Category: " + product.getPd_cate() + ", Skin Type: " + product.getPd_skintype());
        }

        Log.d("filterProductsBySkinType", "category: " + category + ", userSkinType: " + userSkinType + ", products size: " + products.size());
        return filteredProducts;
    }


    private void updateDb(){
        String loggedInPhone = getLoggedInPhone(); // Lấy user_phone từ SharedPreferences
        String userSkinType = dbHelper.getUserSkinType(loggedInPhone);

// Kiểm tra xem có đăng nhập hay không
        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
            if (userSkinType != null && !userSkinType.isEmpty()) {
                // Hiển thị dữ liệu từ cột user_skin nếu có
                binding.txtTinhtrangda.setText(userSkinType);
            } else {
                // Hiển thị "Không có" nếu cột user_skin trống hoặc dữ liệu là null
                binding.txtTinhtrangda.setText("Không có");
            }
        } else {
            // Hiển thị "Không có" nếu không có đăng nhập
            binding.txtTinhtrangda.setText("Không có");
        }

    }

    private String getLoggedInPhone() {
        // Sử dụng requireContext() để lấy Context của Fragment
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }

    private void addEvents() {
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(Aboutskin_chutrinh.this, Aboutskin_intro_test.class);

                // Khởi chạy Intent
                startActivity(intent);

            }
        });
    }

}