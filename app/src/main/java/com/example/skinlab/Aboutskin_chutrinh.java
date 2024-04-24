package com.example.skinlab;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.adapters.ProductAdapter;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityAboutskinChutrinhBinding;
import com.example.skinlab.databinding.ActivityDialogYeucauDangnhapBinding;
import com.example.skinlab.databinding.ActivityForumDialogSendBinding;

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

        addEvents();


    }

    @Override
    public void onResume() {
        updateDb();
        loadDb();
        super.onResume();
    }
    private void loadDb() {
        db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        products = new ArrayList<>();

        boolean loggedIn = Boolean.parseBoolean(isLoggedIn());
        String loggedInPhone = getLoggedInPhone();
        String userSkinType = dbHelper.getUserSkinType(loggedInPhone);
        Log.d("ProductCheck",  "Skintype: " + userSkinType);

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
//
//                  / Kiểm tra tình trạng da của người dùng có nằm trong pdSkintype của sản phẩm không
                    if (pdSkintype != null && userSkinType != null && pdSkintype.contains(userSkinType) && loggedIn) {
                        // Tạo đối tượng Product từ dữ liệu truy vấn
                        Product product = new Product(pdPhoto, pdId, pdName, pdPrice, pdPrice2, pdBrand, pdCate, pdDes, pdSkintype);
                        products.add(product);

                        // Log để kiểm tra
                        Log.d("ProductCheck", "Added product: " + " | Skintype: " + pdSkintype);
                    }
                }
            }
            while (cursor.moveToNext()) ;
            cursor.close();
        }

        GridLayoutManager layoutManagerSuaruamat = new GridLayoutManager(this, 3);
        GridLayoutManager layoutManagerToner = new GridLayoutManager(this, 3);
        GridLayoutManager layoutManagerSerum = new GridLayoutManager(this, 3);
        GridLayoutManager layoutManagerKem = new GridLayoutManager(this, 3);

        binding.rcvSuaruamat.setLayoutManager(layoutManagerSuaruamat);
        binding.rcvToner.setLayoutManager(layoutManagerToner);
        binding.rcvSerum.setLayoutManager(layoutManagerSerum);
        binding.rcvKem.setLayoutManager(layoutManagerKem);

        ArrayList<Product> suaruamat = filterProductsByCategory(products, "Sữa rửa mặt");
        ProductAdapter suaruamatAdapter = new ProductAdapter(this, suaruamat);
        binding.rcvSuaruamat.setAdapter(suaruamatAdapter);

        ArrayList<Product> toner = filterProductsByCategory(products, "Toner");
        ProductAdapter tonerAdapter = new ProductAdapter(this, toner);
        binding.rcvToner.setAdapter(tonerAdapter);

        ArrayList<Product> serum = filterProductsByCategory(products, "Tinh chất");
        ProductAdapter serumAdapter = new ProductAdapter(this, serum);
        binding.rcvSerum.setAdapter(serumAdapter);

        ArrayList<Product> kem = filterProductsByCategory(products, "Kem dưỡng");
        ProductAdapter kemAdapter = new ProductAdapter(this, kem);
        binding.rcvKem.setAdapter(kemAdapter);

        // Set item click listener for each adapter
        suaruamatAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                openProductDetails(product);
            }
        });

        tonerAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                openProductDetails(product);
            }
        });

        serumAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                openProductDetails(product);
            }
        });

        kemAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                openProductDetails(product);
            }
        });
    }

    private void openProductDetails(Product product) {
        Intent intent = new Intent(Aboutskin_chutrinh.this, Product_Details.class);
        intent.putExtra("selectedProduct", product);
        startActivity(intent);
    }

    private ArrayList<Product> filterProductsByCategory(ArrayList<Product> products, String category) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPd_cate().equalsIgnoreCase(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private void updateDb(){
        String loggedInPhone = getLoggedInPhone(); // Lấy user_phone từ SharedPreferences
        String userSkinType = dbHelper.getUserSkinType(loggedInPhone);

// Kiểm tra xem có đăng nhập hay không
        boolean loggedIn = Boolean.parseBoolean(isLoggedIn());
        if (loggedIn) {
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
            showAlerDialog();

        }

    }

    private String isLoggedIn() {
        // Sử dụng requireContext() để lấy Context của Fragment
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        return String.valueOf(loggedIn);
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

    private void showAlerDialog() {
        ActivityDialogYeucauDangnhapBinding yeucauDangnhapBinding = ActivityDialogYeucauDangnhapBinding.inflate(LayoutInflater.from(Aboutskin_chutrinh.this));
        AlertDialog.Builder builder = new AlertDialog.Builder(Aboutskin_chutrinh.this)
                .setView(yeucauDangnhapBinding.getRoot())
                .setCancelable(true);

        // Create dialog
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(200, 200);
        dialog.show();
    }

}