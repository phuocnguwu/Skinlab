package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.adapters.ProductAdapter;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityAboutskinResultBinding;

import java.util.ArrayList;

public class Aboutskin_result extends AppCompatActivity {

    ActivityAboutskinResultBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;
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
        binding = ActivityAboutskinResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadDb();
        addEvents();
    }

    private void loadDb() {
        db = SQLiteDatabase.openDatabase(this.getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
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