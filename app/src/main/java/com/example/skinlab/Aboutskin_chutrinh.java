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
    public static final String USER = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_SKINTYPE = "user_skin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinChutrinhBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
        int userId = getUserId(); // Lấy user_id của người dùng
        String userSkinType = getUserSkinType(userId); // Lấy loại da của người dùng
        if (userSkinType != null) {
            // Mở kết nối database ở đây
            db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
            loadDb(userSkinType, "Sữa rửa mặt", binding.rcvSuaruamat);
            loadDb(userSkinType, "Nước dưỡng", binding.rcvToner);
            loadDb(userSkinType, "Tinh chất", binding.rcvSerum);
            loadDb(userSkinType, "Kem dưỡng", binding.rcvKem);
        }

        dbHelper = new DatabaseHelper(this);


    }

    @Override
    public void onResume() {
        super.onResume();
        updateDb();
    }

    // Phương thức để lấy loại da từ database của người dùng
    private int getUserId() {
        Log.d("getUserId", "Start getting user ID");
        int userId = -1; // Giả sử user_id không tồn tại hoặc có lỗi

        // Mở kết nối đến cơ sở dữ liệu
        db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);

        // Truy vấn cơ sở dữ liệu để lấy user_id
        Cursor cursor = db.query(USER, new String[]{COLUMN_USER_ID}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userIdIndex = cursor.getColumnIndex(COLUMN_USER_ID);
            userId = cursor.getInt(userIdIndex);
            cursor.close();
        }
        Log.d("getUserId", "Finish getting user ID: " + userId);
        return userId;
    }
    private String getUserSkinType(int userId) {
        Log.d("getUserSkinType", "Start getting user skin type for userId: " + userId);
        String skinType = null;
        db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        String[] columns = {COLUMN_USER_SKINTYPE};
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)}; // Chuyển đổi user_id thành chuỗi

        Cursor cursor = db.query(USER, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userSkinTypeIndex = cursor.getColumnIndex(COLUMN_USER_SKINTYPE);
            skinType = cursor.getString(userSkinTypeIndex);
            cursor.close();
        }
        Log.d("getUserSkinType", "Finish getting user skin type: " + skinType);

        return skinType;
    }

    private void loadDb(String skinType, String category, RecyclerView recyclerView) {

        SQLiteDatabase db = SQLiteDatabase.openDatabase(getApplicationContext().getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        products = new ArrayList<>();

        String selection = COLUMN_PD_SKINTYPE + " = ? AND " + COLUMN_PD_CATE + " = ?";
        String[] selectionArgs = {skinType, category};

        Cursor cursor = db.query(TBL_NAME, null, selection, selectionArgs, null, null, null);
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

                    Product product = new Product(pdPhoto, pdId, pdName, pdPrice, pdPrice2, pdBrand, pdCate, pdDes, pdSkintype);
                    products.add(product);

                }
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        Log.d("LoadDb", "Finish loading database: " + products.size());

        // Khởi tạo adapter và gán danh sách sản phẩm vào adapter
        adapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(adapter); // Đặt adapter cho RecyclerView

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager); // Đặt layout manager cho RecyclerView



        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(Aboutskin_chutrinh.this, Product_Details.class);
                intent.putExtra("selectedProduct", product);
                startActivity(intent);
            }
        });
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