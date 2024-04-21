package com.example.skinlab;

import static android.app.PendingIntent.getActivity;
import static com.example.skinlab.Products.db;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.adapters.FeedbackAdapter;
import com.example.adapters.ProductDetailsAdapter;
import com.example.interfaces.ProductInterface;
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

    public static final String TABLE_NAME_FEEDBACK = "feedback";
    public static final String COLUMN_FEEDBACK_ID = "feedback_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_DATE_CREATED = "date_created";
    public static final String COLUMN_FEEDBACK_RATINGS = "feedback_ratings";
    public static final String COLUMN_FEEDBACK_TITLE = "feedback_title";
    public static final String COLUMN_FEEDBACK_CONTENT = "feedback_content";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        initFeedback();
//        loadFeedback();

//        loadFeedbacksByProductId();

        loadData();
        addEvents();
    }

//    public List<Feedback> getFeedbacksByProductId(String productId) {
//        List<Feedback> feedbackList = new ArrayList<>();
//        Cursor cursor = db.rawQuery(query, new String[]{productId});
//        if (cursor != null && cursor.moveToFirst()) {
//            List<Feedback> feedbacks = new ArrayList<>();
//
//            do {
//                String feedbackId = cursor.getString(cursor.getColumnIndex("feedback_id"));
//                byte[] userThumb = cursor.getBlob(cursor.getColumnIndex("user_thumb"));
//                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
//                String dateCreated = cursor.getString(cursor.getColumnIndex("date_created"));
//                int ratings = cursor.getInt(cursor.getColumnIndex("feedback_ratings"));
//                String title = cursor.getString(cursor.getColumnIndex("feedback_title"));
//                String content = cursor.getString(cursor.getColumnIndex("feedback_content"));
//
//                // Tạo đối tượng Feedback từ dữ liệu trong Cursor
//                Feedback feedback = new Feedback(feedbackId, userThumb, userName, dateCreated, ratings, title, content);
//
//                // Thêm đối tượng Feedback vào danh sách
//                feedbacks.add(feedback);
//            } while (cursor.moveToNext());
//
//            // Đóng Cursor sau khi sử dụng xong
//            cursor.close();
//
//            // Trả về danh sách các đối tượng Feedback
//            return feedbacks;
//        } else {
//            // Nếu Cursor rỗng, trả về danh sách trống
//            return feedbacks;
//        }
//    }

    private void initFeedback() {

//        feedbacks = new ArrayList<>();
//        feedbacks.add(new Feedback("feedback01", R.drawable.account, "Emily", "07/04/2024", 5, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
//        feedbacks.add(new Feedback("feedback01",R.drawable.account, "John", "07/04/2024", 4, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));
//        feedbacks.add(new Feedback("feedback01",R.drawable.account, "Belle", "07/04/2024", 5, "Giao nhanh, hàng chính hãng", "Tôi đã mua lần thứ 3. Check mã vạch chính hãng nên yên tâm sử dụng."));

//        Log.d("Product_Details", "Feedbacks size: " + feedbacks.size());
    }

    private void loadFeedback() {
            feedbackAdapter = new FeedbackAdapter(Product_Details.this, R.layout.list_item_feedback, feedbacks);
            binding.lvFeedback.setAdapter(feedbackAdapter);

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
                    binding.txtId.setText(selectedProduct.getPd_id());
                    binding.txtProductPrice.setText(String.valueOf(selectedProduct.getPd_price() + " đ"));
                    binding.txtProductPrice2.setText(String.valueOf(selectedProduct.getPd_price2() + " đ"));
                    binding.txtProductDes.setText(selectedProduct.getPd_des());

                }else{
                    Log.d("Product_Details", "selectedProduct is null");
                }
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
                // Tạo Intent để chuyển dữ liệu sang Giohang_Fragment
//              Giohang_Fragment giohangFragment = Giohang_Fragment.newInstance(new Bundle());
//              giohangFragment.addToCart(selectedProduct, Product_Details.this); // Truyền context từ Activity hiện tại
//              getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, giohangFragment).commit();
//
//              giohangFragment = (Giohang_Fragment) getSupportFragmentManager().findFragmentById(R.id.containerLayout);

//                Giohang_Fragment giohangFragment = new Giohang_Fragment();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("selectedProduct", selectedProduct);
//                Log.d("Product_Details", "selectedProduct: " + selectedProduct);
//                Log.d("Product_Details", "selectedProduct: ID = " + selectedProduct.getPd_id());
//                Log.d("Product_Details", "selectedProduct: Name = " + selectedProduct.getPd_name());
//                Log.d("Product_Details", "selectedProduct: Price = " + selectedProduct.getPd_price());
//                giohangFragment.setArguments(bundle);
//                Log.d("Product_Details", "selectedProduct: Price = " + bundle);

                Intent intent = new Intent(Product_Details.this, MainActivity_containtFragment.class);
                intent.putExtra("selectedProduct", selectedProduct);
                startActivity(intent);

                // Tạo Intent để chuyển dữ liệu sang MainActivity_containtFragment
//                Intent intent = new Intent(Product_Details.this, MainActivity_containtFragment.class);
//                // Truyền selectedProduct qua Intent
//                intent.putExtra("selectedProduct", selectedProduct);
//                Log.d("Product_Details", "selectedProduct: " + selectedProduct);
//                Log.d("Product_Details", "selectedProduct: ID = " + selectedProduct.getPd_id());
//                Log.d("Product_Details", "selectedProduct: Name = " + selectedProduct.getPd_name());
//                Log.d("Product_Details", "selectedProduct: Price = " + selectedProduct.getPd_price());
//                // Chuyển sang MainActivity_containtFragment
//                startActivity(intent);

                // Kiểm tra nếu giohangFragment không null và selectedProduct đã được khởi tạo
//              if (giohangFragment != null && selectedProduct != null) {
//              // Gọi phương thức addToCart() của Giohang_Fragment và truyền vào selectedProduct
//                 giohangFragment.addToCart(selectedProduct);
//              } else {
//              // Xử lý trường hợp Giohang_Fragment không được tìm thấy hoặc selectedProduct không tồn tại
//                 Log.e("Product_Details", "Giohang_Fragment is null or selectedProduct is null");
//              }



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



//            public void onClick(AdapterView<?> parent, View view, int position, long id) {
//                // Truy cập phần tử từ Adapter, không phải từ AdapterView
//                Product product;
//                product = products.get(position);
//
//                // Kiểm tra xem Fragment có thực hiện ProductInterface không
//                if (view.getContext() instanceof ProductInterface) {
//                    ProductInterface productInterface = (ProductInterface) view.getContext();
//                    productInterface.onClickItem(product);
//                }
//            }
        });

        binding.txtProductFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product_Details.this, Product_Details_Feedback.class);
                startActivity(intent);
            }
        });
    }


}
