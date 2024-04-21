package com.example.skinlab;

import static com.example.skinlab.Products.db;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.adapters.FeedbackAdapter;
import com.example.adapters.ProductDetailsAdapter;
import com.example.models.Feedback;
import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityProductDetailsBinding;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityProductDetailsDialogAddtocartBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Product_Details extends AppCompatActivity {
    ActivityProductDetailsBinding binding;
    ProductDetailsAdapter adapter;
    FeedbackAdapter feedbackAdapter;
    ArrayList<Product> products;

    List<Feedback> feedbacks;
    Product selectedProduct;

    public static final String DB_NAME = "Skinlab.db";
    public static final String TABLE_NAME_FEEDBACK = "feedback";
    public static final String COLUMN_FEEDBACK_ID = "feedback_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_PRODUCT_ID = "pd_id";
    public static final String COLUMN_DATE_CREATED = "date_created";
    public static final String COLUMN_FEEDBACK_RATINGS = "feedback_ratings";
    public static final String COLUMN_FEEDBACK_TITLE = "feedback_title";
    public static final String COLUMN_FEEDBACK_CONTENT = "feedback_content";
    public static final String TABLE_NAME_USER = "user";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_AVATAR = "user_avatar";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        initFeedback();
        addEvents();
        loadData();
        loadFeedback();


    }

    private void initFeedback() {

//        feedbacks = new ArrayList<>();
//        feedbacks.add(new Feedback("feedback01", R.drawable.account, "Emily", "07/04/2024", 5, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
//        feedbacks.add(new Feedback("feedback01",R.drawable.account, "John", "07/04/2024", 4, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
//        feedbacks.add(new Feedback("feedback01",R.drawable.account, "Belle", "07/04/2024", 5, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));

//        Log.d("Product_Details", "Feedbacks size: " + feedbacks.size());
    }

    private void loadFeedback() {
        feedbacks = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        // Kiểm tra selectedProduct trước khi truy vấn dữ liệu
        if (selectedProduct == null) {
            Log.e("Product_Details", "selectedProduct is null");
            return;
        }
        Cursor cursor = db.rawQuery("SELECT f.*, u." + COLUMN_USER_AVATAR + ", u." + COLUMN_USER_NAME +
                " FROM " + TABLE_NAME_FEEDBACK + " AS f" +
                " JOIN " + TABLE_NAME_USER + " AS u ON f." + COLUMN_USER_ID + " = u." + COLUMN_USER_ID +
                " WHERE f." + COLUMN_PRODUCT_ID + " = ?", new String[]{selectedProduct.getPd_id()});

        // Truy vấn các đánh giá từ cơ sở dữ liệu dựa trên ID sản phẩm

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int columnIndexfeedbackId = cursor.getColumnIndex(COLUMN_FEEDBACK_ID);
                int columnIndexuserId = cursor.getColumnIndex(COLUMN_USER_ID);
                int columnIndexdateCreated = cursor.getColumnIndex(COLUMN_DATE_CREATED);
                int columnIndexratings = cursor.getColumnIndex(COLUMN_FEEDBACK_RATINGS);
                int columnIndextitle = cursor.getColumnIndex(COLUMN_FEEDBACK_TITLE);
                int columnIndexcontent = cursor.getColumnIndex(COLUMN_FEEDBACK_CONTENT);
                int columnIndexuserThumbUrl = cursor.getColumnIndex(COLUMN_USER_AVATAR);
                int columnIndexuserName = cursor.getColumnIndex(COLUMN_USER_NAME);

                if (columnIndexfeedbackId != -1 && columnIndexuserId != -1 && columnIndexdateCreated != -1 &&
                        columnIndexratings != -1 && columnIndextitle != -1 && columnIndexcontent != -1 &&
                        columnIndexuserThumbUrl != -1 && columnIndexuserName != -1) {

                    String feedbackId = cursor.getString(columnIndexfeedbackId);
                    String userId = cursor.getString(columnIndexuserId);
                    String dateCreated = cursor.getString(columnIndexdateCreated);
                    int ratings = cursor.getInt(columnIndexratings);
                    String title = cursor.getString(columnIndextitle);
                    String content = cursor.getString(columnIndexcontent);
                    String userThumbUrl = cursor.getString(columnIndexuserThumbUrl);
                    String userName = cursor.getString(columnIndexuserName);

                    // Tạo đối tượng Feedback
                    Feedback feedback = new Feedback(feedbackId, userThumbUrl, userName, dateCreated, ratings, title, content);
                    feedbacks.add(feedback);
                }
            } while (cursor.moveToNext());
            cursor.close();

            // Khởi tạo và gán FeedbackAdapter cho ListView
            FeedbackAdapter feedbackAdapter = new FeedbackAdapter(Product_Details.this, R.layout.list_item_feedback, feedbacks);
            binding.lvFeedback.setAdapter(feedbackAdapter);
        } else {
            Log.e("Product_Details", "Cursor is null or empty");
            // Đóng con trỏ nếu không có dữ liệu
            if (cursor != null) {
                cursor.close();
            }
        }
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
                Intent intent = new Intent(Product_Details.this, Donhang_dathang.class);
                startActivity(intent);
            }
        });
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityProductDetailsDialogAddtocartBinding DialogAddtocartBinding = ActivityProductDetailsDialogAddtocartBinding.inflate(LayoutInflater.from(Product_Details.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Product_Details.this)
                        .setView(DialogAddtocartBinding.getRoot())
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
        });

        binding.txtProductFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product_Details.this, Product_Details_Feedback.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedProduct")) {

            if (intent != null && intent.hasExtra("selectedProduct")) {
                selectedProduct = (Product) intent.getSerializableExtra("selectedProduct");

                if (selectedProduct != null) {
                    Picasso.get().load(selectedProduct.getPd_photo()).into(binding.imvProduct);
                    binding.txtProductName.setText(selectedProduct.getPd_name());
                    binding.txtProductBrand.setText(selectedProduct.getPd_brand());
                    binding.txtProductPrice.setText(String.valueOf(selectedProduct.getPd_price() + " đ"));
                    binding.txtProductPrice2.setText(String.valueOf(selectedProduct.getPd_price2() + " đ"));
                    binding.txtProductDes.setText(selectedProduct.getPd_des());

                }else{
                    Log.d("Product_Details", "selectedProduct is null");
                }
            }
        }

    }
}
