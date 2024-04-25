package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

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
import com.example.skinlab.databinding.ActivityAboutskinResultBinding;

import java.util.ArrayList;

public class Aboutskin_result extends AppCompatActivity {

    ActivityAboutskinResultBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;
    String userSkinType;
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
    public static final String TBL_SKIN = "ABOUTSKIN_DACDIEM";
    public static final String COLUMN_DACDIEM1 = "skin_dacdiem1";
    public static final String COLUMN_DACDIEM2 = "skin_dacdiem2";
    public static final String COLUMN_DACDIEM3 = "skin_dacdiem3";
    public static final String COLUMN_MOTA1 = "skin_mota";
    public static final String COLUMN_MOTA2 = "skin_mota2";
    public static final String COLUMN_USERSKIN = "user_skin";
    public static final String COLUMN_PD_RATING = "rating";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutskinResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userSkinType = getIntent().getStringExtra("userSkintype");
        Log.d("skintype", "Hi" + userSkinType);

        updateDb();
        loadDb();
        addEvents();

    }
    @Override
    public void onResume() {
        super.onResume();
        updateDb();
    }

    private void loadDb() {
        db = SQLiteDatabase.openDatabase(this.getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        products = new ArrayList<>();

        Log.d("skintype", "Hi" + userSkinType);

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
                int columnIndexRating = cursor.getColumnIndex(COLUMN_PD_RATING);

                if (columnIndexId != -1 && columnIndexName != -1 && columnIndexPrice != -1 &&
                        columnIndexPrice2 != -1 && columnIndexBrand != -1 && columnIndexCate != -1 &&
                        columnIndexDes != -1 && columnIndexPhoto != -1 && columnIndexSkiptype != -1 && columnIndexRating != -1) {

                    String pdId = cursor.getString(columnIndexId);
                    String pdName = cursor.getString(columnIndexName);
                    int pdPrice = cursor.getInt(columnIndexPrice);
                    int pdPrice2 = cursor.getInt(columnIndexPrice2);
                    String pdBrand = cursor.getString(columnIndexBrand);
                    String pdCate = cursor.getString(columnIndexCate);
                    String pdDes = cursor.getString(columnIndexDes);
                    String pdPhoto = cursor.getString(columnIndexPhoto);
                    String pdSkintype = cursor.getString(columnIndexSkiptype);
                    int pdRating = cursor.getInt(columnIndexRating);

                    // Tạo đối tượng Product từ dữ liệu truy vấn
                    if (pdSkintype != null && userSkinType != null && pdSkintype.contains(userSkinType)) {
                        // Tạo đối tượng Product từ dữ liệu truy vấn
                        Product product = new Product(pdPhoto, pdId, pdName, pdPrice, pdPrice2, pdBrand, pdCate, pdDes, pdSkintype, pdRating);
                        products.add(product);
                    }
                }
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        // Tạo layout manager cho RecyclerView (ở đây là GridLayoutManager)
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rcvProduct.setLayoutManager(layoutManager); // Đặt layout manager cho RecyclerView

        ArrayList<Product> limitedProducts = new ArrayList<>(products.subList(0, Math.min(products.size(), 6)));
        // Khởi tạo adapter và gán danh sách sản phẩm vào adapter
        adapter = new ProductAdapter(this, limitedProducts);

        binding.rcvProduct.setAdapter(adapter); // Đặt adapter cho RecyclerView

        // Định nghĩa sự kiện click trên mỗi sản phẩm trong RecyclerView
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Xử lý khi người dùng nhấn vào một item trong RecyclerView
                // Khởi tạo Intent để chuyển sang màn hình chi tiết sản phẩm
                Intent intent = new Intent(Aboutskin_result.this, Product_Details.class);
                // Đính kèm thông tin sản phẩm được chọn vào Intent
                intent.putExtra("selectedProduct", product);
                // Chuyển sang màn hình chi tiết sản phẩm
                startActivity(intent);
            }
        });
    }

    private void updateDb() {
        dbHelper = new DatabaseHelper(this);

        // Kiểm tra xem người dùng có đăng nhập không
        String loggedInPhone = getLoggedInPhone();

        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
            // Nếu có đăng nhập, kiểm tra totalScore và cập nhật user_skin tương ứng
            int totalScore = getIntent().getIntExtra("TOTAL_SCORE", 0);
            if (totalScore > 20 && totalScore <= 40) {
                // Update user_skin thành "Da dầu"
                dbHelper.updateUserSkin(loggedInPhone, "Da dầu");
                userSkinType = "Da dầu";
                binding.txtDa.setText("Da dầu");
                // Truy vấn dữ liệu từ bảng ABOUTSKIN_DACDIEM cho "Da dầu"
                Cursor cursor = dbHelper.getSkinDataByType("Da dầu");
                if (cursor != null && cursor.moveToFirst()) {
                    // Hiển thị dữ liệu lên các TextView tương ứng
                    binding.txtDacdiem1.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM1)));
                    binding.txtDacdiem2.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM2)));
                    binding.txtDacdiem3.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM3)));
                    binding.txtMota1.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTA1)));
                    binding.txtMota2.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTA2)));
                    cursor.close();
                }
            } else if (totalScore >= 10 && totalScore <= 20) {
                // Update user_skin thành "Da khô"
                dbHelper.updateUserSkin(loggedInPhone, "Da khô");
                binding.txtDa.setText("Da khô");
                userSkinType = "Da khô";
                // Truy vấn dữ liệu từ bảng ABOUTSKIN_DACDIEM cho "Da khô"
                Cursor cursor = dbHelper.getSkinDataByType("Da khô");
                if (cursor != null && cursor.moveToFirst()) {
                    // Hiển thị dữ liệu lên các TextView tương ứng
                    binding.txtDacdiem1.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM1)));
                    binding.txtDacdiem2.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM2)));
                    binding.txtDacdiem3.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM3)));
                    binding.txtMota1.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTA1)));
                    binding.txtMota2.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTA2)));
                    cursor.close();
                }
            }
        } else {
            // Nếu không đăng nhập, chỉ setText cho txtDa dựa trên totalScore
            int totalScore = getIntent().getIntExtra("TOTAL_SCORE", 0);
            if (totalScore > 20 && totalScore <= 40) {
                binding.txtDa.setText("Da dầu");
                userSkinType = "Da dầu";
                Cursor cursor = dbHelper.getSkinDataByType("Da dầu");
                if (cursor != null && cursor.moveToFirst()) {
                    // Hiển thị dữ liệu lên các TextView tương ứng
                    binding.txtDacdiem1.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM1)));
                    binding.txtDacdiem2.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM2)));
                    binding.txtDacdiem3.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM3)));
                    binding.txtMota1.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTA1)));
                    binding.txtMota2.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTA2)));
                    cursor.close();
                }
            } else if (totalScore >= 10 && totalScore <= 20) {
                binding.txtDa.setText("Da khô");
                userSkinType = "Da khô";
                Cursor cursor = dbHelper.getSkinDataByType("Da khô");
                if (cursor != null && cursor.moveToFirst()) {
                    // Hiển thị dữ liệu lên các TextView tương ứng
                    binding.txtDacdiem1.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM1)));
                    binding.txtDacdiem2.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM2)));
                    binding.txtDacdiem3.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DACDIEM3)));
                    binding.txtMota1.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTA1)));
                    binding.txtMota2.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTA2)));
                    cursor.close();
                }
            }
//            loadDb();
        }
    }

    private String getLoggedInPhone() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }

    private void addEvents() {

        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(Aboutskin_result.this, Aboutskin_test.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });
    }
}