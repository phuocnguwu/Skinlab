package com.example.skinlab;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.adapters.ProductAdapter;
import com.example.skinlab.databinding.ActivityProductsBinding;
import com.example.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity {
    ActivityProductsBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;

    public static final String DB_NAME = "Skinlab.db";

    // Khai báo các biến để lưu trạng thái của các bộ lọc
    private ArrayList<String> selectedCategories = new ArrayList<>();
    private ArrayList<String> selectedBrands = new ArrayList<>();
    private ArrayList<Integer> selectedPriceRanges = new ArrayList<>();
    boolean showAllProducts = true;
    String searchKeyword;

    public static SQLiteDatabase db;

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

        Intent intent = getIntent();
        if (intent !=null) {
            showAllProducts = intent.getBooleanExtra("showAllProducts", false);
            searchKeyword = intent.getStringExtra("searchKeyword");
        }

        loadDb();
        addEvents();
    }

    private void loadDb() {
        db = SQLiteDatabase.openDatabase(getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        products = new ArrayList<>();

        Intent intent = getIntent();
        boolean showAllProducts = intent.getBooleanExtra("showAllProducts", false);
        String searchKeyword = intent.getStringExtra("searchKeyword");
        if (searchKeyword == null) {
            searchKeyword = "";
        }
        String brand = intent.getStringExtra("brand");
        String category = intent.getStringExtra("category");


        Cursor cursor;

        if (showAllProducts) {
            // Hiển thị tất cả sản phẩm
            cursor = db.query(TBL_NAME, null, null, null, null, null, null);
        } else if (searchKeyword != null && !searchKeyword.isEmpty()) {
            // Tìm kiếm sản phẩm theo từ khoá
            String selection = COLUMN_PD_NAME + " LIKE ?";
            String[] selectionArgs = { "%" + searchKeyword + "%" };
            cursor = db.query(TBL_NAME, null, selection, selectionArgs, null, null, null);
        } else if (category != null && !category.isEmpty()) {
            // Lọc sản phẩm theo category
            String selection = COLUMN_PD_CATE + " = ?";
            String[] selectionArgs = { category };
            cursor = db.query(TBL_NAME, null, selection, selectionArgs, null, null, null);
        } else if (brand != null && !brand.isEmpty()) {
            // Lọc sản phẩm theo brand
            String selection = COLUMN_PD_BRAND + " = ?";
            String[] selectionArgs = { brand };
            cursor = db.query(TBL_NAME, null, selection, selectionArgs, null, null, null);
        } else {
            // Mặc định không hiển thị sản phẩm nào
            cursor = null;
        }

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

                    // Tạo đối tượng Product từ dữ liệu truy vấn
                    Product product = new Product(pdPhoto, pdId, pdName, pdPrice, pdPrice2, pdBrand, pdCate, pdDes, pdSkintype);
                    products.add(product);
                }
            } while (cursor.moveToNext());

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
                Intent intent = new Intent(Products.this, Product_Details.class);
                intent.putExtra("selectedProduct", product);
                startActivity(intent);
            }
        });
    }

    private void applyFilters() {

        List<String> conditions = new ArrayList<>();

        // Lọc theo danh mục
        if (!selectedCategories.isEmpty()) {
            conditions.add(COLUMN_PD_CATE + " IN (" + buildInCondition(selectedCategories) + ")");
        }

        // Lọc theo thương hiệu
        if (!selectedBrands.isEmpty()) {
            conditions.add(COLUMN_PD_BRAND + " IN (" + buildInCondition(selectedBrands) + ")");
        }

        // Lọc theo khoảng giá
        if (!selectedPriceRanges.isEmpty()) {
            List<String> priceConditions = new ArrayList<>();
            for (Integer priceRange : selectedPriceRanges) {
                switch (priceRange) {
                    case 1:
                        priceConditions.add(COLUMN_PD_PRICE + " < 200000");
                        break;
                    case 2:
                        priceConditions.add(COLUMN_PD_PRICE + " BETWEEN 200000 AND 500000");
                        break;
                    case 3:
                        priceConditions.add(COLUMN_PD_PRICE + " BETWEEN 500000 AND 750000");
                        break;
                    case 4:
                        priceConditions.add(COLUMN_PD_PRICE + " > 750000");
                        break;
                }
            }
            if (!priceConditions.isEmpty()) {
                conditions.add("(" + String.join(" OR ", priceConditions) + ")");
            }
        }

        // Xây dựng câu truy vấn
        String selection = "";
        if (!conditions.isEmpty()) {
            selection = String.join(" AND ", conditions);
        }

        // Thực hiện truy vấn
        Cursor cursor = null;
        if (selection.length() > 0) {
            cursor = db.query(TBL_NAME, null, selection.toString(), null, null, null, null);
        } else {
//            cursor = null;
            cursor = db.query(TBL_NAME, null, null, null, null, null, null);
        }

        // Xử lý kết quả truy vấn và cập nhật danh sách sản phẩm
        if (cursor != null && cursor.moveToFirst()) {
            products.clear();
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

                    // Tạo đối tượng Product từ dữ liệu truy vấn
                    Product product = new Product(pdPhoto, pdId, pdName, pdPrice, pdPrice2, pdBrand, pdCate, pdDes, pdSkintype);
                    products.add(product);
                }
            } while (cursor.moveToNext());
            cursor.close();
            adapter.notifyDataSetChanged();
        }else{
            products.clear();
            adapter.notifyDataSetChanged();
        }

    }
    private String buildInCondition(List<String> values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            builder.append("'").append(values.get(i)).append("'");
            if (i < values.size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }



    private void addEvents() {

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = binding.edtSearch.getText().toString().trim();

                // Kiểm tra xem keyword có rỗng không
                if (!keyword.isEmpty()) {
                    // Tạo Intent để chuyển sang trang Product
                    Intent intent = new Intent(Products.this, Products.class);
                    // Đính kèm từ khoá tìm kiếm vào Intent
                    intent.putExtra("searchKeyword", keyword);
                    // Chuyển sang trang Product
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo cho người dùng nhập từ khóa tìm kiếm
                    Toast.makeText(Products.this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.imvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Products.this, Giohang_Fragment.class);
                startActivity(intent);
            }
        });
        binding.btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showFilterSheet();}

            private void showFilterSheet() {
                SharedPreferences sharedPreferences = getSharedPreferences("checkbox_states", Context.MODE_PRIVATE);

                selectedCategories.clear();
                selectedBrands.clear();
                selectedPriceRanges.clear();

                Dialog dialog = new Dialog(Products.this);
                dialog.setContentView(R.layout.filter_dialog);


                // Xử lý các lựa chọn
                // Cate
                CheckBox checkboxCate1 = dialog.findViewById(R.id.checkboxCate_SRM);
                checkboxCate1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxCate_SRM",checkboxCate1, sharedPreferences );
                    if (isChecked) {
                        selectedCategories.add("Sữa rửa mặt");
                    } else {
                        selectedCategories.remove("Sữa rửa mặt");
                    }
                });
                CheckBox checkboxCate2 = dialog.findViewById(R.id.checkboxCate_ND);
                checkboxCate2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxCate_ND",checkboxCate2, sharedPreferences );
                    if (isChecked) {
                        selectedCategories.add("Nước dưỡng");
                    } else {
                        selectedCategories.remove("Nước dưỡng");
                    }
                });
                CheckBox checkboxCate3 = dialog.findViewById(R.id.checkboxCate_KD);
                checkboxCate3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxCate_KD",checkboxCate3, sharedPreferences );
                    if (isChecked) {
                        selectedCategories.add("Kem dưỡng");
                    } else {
                        selectedCategories.remove("Kem dưỡng");
                    }
                });
                CheckBox checkboxCate4 = dialog.findViewById(R.id.checkboxCate_MN);
                checkboxCate4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxCate_MN",checkboxCate4, sharedPreferences );
                    if (isChecked) {
                        selectedCategories.add("Mặt nạ");
                    } else {
                        selectedCategories.remove("Mặt nạ");
                    }
                });
                CheckBox checkboxCate5 = dialog.findViewById(R.id.checkboxCate_TC);
                checkboxCate5.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxCate_TC",checkboxCate5, sharedPreferences );
                    if (isChecked) {
                        selectedCategories.add("Tinh chất");
                    } else {
                        selectedCategories.remove("Tinh chất");
                    }
                });

                //Brands
                CheckBox checkboxBrand1 = dialog.findViewById(R.id.checkboxBrand_Innisfree);
                checkboxBrand1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxBrand_Innisfree",checkboxBrand1, sharedPreferences );
                    if (isChecked) {
                            selectedBrands.add("INNISFREE");
                    } else {
                        selectedBrands.remove("INNISFREE");
                    }
                });
                CheckBox checkboxBrand2 = dialog.findViewById(R.id.checkboxBrand_Laneige);
                checkboxBrand2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxBrand_Laneige",checkboxBrand2, sharedPreferences );
                    if (isChecked) {
                        selectedBrands.add("Laneige");
                    } else {
                        selectedBrands.remove("Laneige");
                    }
                });
                CheckBox checkboxBrand3 = dialog.findViewById(R.id.checkboxBrand_AHC);
                checkboxBrand3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxBrand_AHC",checkboxBrand3, sharedPreferences );
                    if (isChecked) {
                        selectedBrands.add("AHC");
                    } else {
                        selectedBrands.remove("AHC");
                    }
                });
                CheckBox checkboxBrand4 = dialog.findViewById(R.id.checkboxBrand_Klairs);
                checkboxBrand4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxBrand_Klairs",checkboxBrand4, sharedPreferences );
                    if (isChecked) {
                        selectedBrands.add("Klairs");
                    } else {
                        selectedBrands.remove("Klairs");
                    }
                });

                //Price
                CheckBox checkboxPrice250 = dialog.findViewById(R.id.checkboxPrice_250);
                checkboxPrice250.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxPrice_250",checkboxPrice250, sharedPreferences );
                    if (isChecked) {
                        selectedPriceRanges.add(1); // Giá dưới 200,000 đ
                    } else {
                        selectedPriceRanges.remove(Integer.valueOf(1));
                    }
                });

                CheckBox checkboxPrice500 = dialog.findViewById(R.id.checkboxPrice_500);
                checkboxPrice500.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxPrice_500",checkboxPrice500, sharedPreferences );
                    if (isChecked) {
                        selectedPriceRanges.add(2); // Giá từ 200,000 đ đến 500,000 đ
                    } else {
                        selectedPriceRanges.remove(Integer.valueOf(2));
                    }
                });

                CheckBox checkboxPrice750 = dialog.findViewById(R.id.checkboxPrice_750);
                checkboxPrice750.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxPrice_750",checkboxPrice750, sharedPreferences );
                    if (isChecked) {
                        selectedPriceRanges.add(3); // Giá từ 500,000 đ đến 750,000 đ
                    } else {
                        selectedPriceRanges.remove(Integer.valueOf(3));
                    }
                });

                CheckBox checkboxPrice1000 = dialog.findViewById(R.id.checkboxPrice_1000);
                checkboxPrice1000.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    saveCheckBoxState("checkboxPrice_1000",checkboxPrice1000, sharedPreferences );
                    if (isChecked) {
                        selectedPriceRanges.add(4); // Giá trên 750,000 đ
                    } else {
                        selectedPriceRanges.remove(Integer.valueOf(4));
                    }
                });

                // Khôi phục trạng thái của các checkbox trước khi hiển thị dialog
                restoreCheckBoxState("checkboxCate_SRM", checkboxCate1, sharedPreferences);
                restoreCheckBoxState("checkboxCate_ND", checkboxCate2, sharedPreferences);
                restoreCheckBoxState("checkboxCate_KD", checkboxCate3, sharedPreferences);
                restoreCheckBoxState("checkboxCate_MN", checkboxCate4, sharedPreferences);
                restoreCheckBoxState("checkboxCate_TC", checkboxCate5, sharedPreferences);
                restoreCheckBoxState("checkboxBrand_Innisfree", checkboxBrand1, sharedPreferences);
                restoreCheckBoxState("checkboxBrand_Laneige", checkboxBrand2, sharedPreferences);
                restoreCheckBoxState("checkboxBrand_AHC", checkboxBrand3, sharedPreferences);
                restoreCheckBoxState("checkboxBrand_Klairs", checkboxBrand4, sharedPreferences);
                restoreCheckBoxState("checkboxPrice_250", checkboxPrice250, sharedPreferences);
                restoreCheckBoxState("checkboxPrice_500", checkboxPrice500, sharedPreferences);
                restoreCheckBoxState("checkboxPrice_750", checkboxPrice750, sharedPreferences);
                restoreCheckBoxState("checkboxPrice_1000", checkboxPrice1000, sharedPreferences);


                Button btnDefault = dialog.findViewById(R.id.btnFilter_Default);
                btnDefault.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xóa tất cả các bộ lọc được chọn
                        selectedCategories.clear();
                        selectedBrands.clear();
                        selectedPriceRanges.clear();

                        // Đặt lại trạng thái của các checkbox trong dialog
                        CheckBox checkboxCate_SRM = dialog.findViewById(R.id.checkboxCate_SRM);
                        CheckBox checkboxCate_ND = dialog.findViewById(R.id.checkboxCate_ND);
                        CheckBox checkboxCate_KD = dialog.findViewById(R.id.checkboxCate_KD);
                        CheckBox checkboxCate_MN = dialog.findViewById(R.id.checkboxCate_MN);
                        CheckBox checkboxCate_TC = dialog.findViewById(R.id.checkboxCate_TC);
                        CheckBox checkboxBrand_Innisfree = dialog.findViewById(R.id.checkboxBrand_Innisfree);
                        CheckBox checkboxBrand_Laneige = dialog.findViewById(R.id.checkboxBrand_Laneige);
                        CheckBox checkboxBrand_AHC = dialog.findViewById(R.id.checkboxBrand_AHC);
                        CheckBox checkboxBrand_Klairs = dialog.findViewById(R.id.checkboxBrand_Klairs);
                        CheckBox checkboxPrice_250 = dialog.findViewById(R.id.checkboxPrice_250);
                        CheckBox checkboxPrice_500 = dialog.findViewById(R.id.checkboxPrice_500);
                        CheckBox checkboxPrice_750 = dialog.findViewById(R.id.checkboxPrice_750);
                        CheckBox checkboxPrice_1000 = dialog.findViewById(R.id.checkboxPrice_1000);

                        checkboxCate_SRM.setChecked(false);
                        checkboxCate_ND.setChecked(false);
                        checkboxCate_KD.setChecked(false);
                        checkboxCate_MN.setChecked(false);
                        checkboxCate_TC.setChecked(false);
                        checkboxBrand_Innisfree.setChecked(false);
                        checkboxBrand_Laneige.setChecked(false);
                        checkboxBrand_AHC.setChecked(false);
                        checkboxBrand_Klairs.setChecked(false);
                        checkboxPrice_250.setChecked(false);
                        checkboxPrice_500.setChecked(false);
                        checkboxPrice_750.setChecked(false);
                        checkboxPrice_1000.setChecked(false);

//                        loadDb();
//                        dialog.dismiss();
                    }
                });

                Button btnApply = dialog.findViewById(R.id.btnFilter_Apply);
                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        applyFilters();
                        dialog.dismiss();
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
    private void saveCheckBoxState(String key, CheckBox checkBox, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, checkBox.isChecked());
        editor.apply();
    }

    private void restoreCheckBoxState(String key, CheckBox checkBox, SharedPreferences sharedPreferences) {
        boolean isChecked = sharedPreferences.getBoolean(key, false); // Giá trị mặc định là false nếu chưa lưu trạng thái
        checkBox.setChecked(isChecked);

        if (!isChecked) {
            selectedCategories.remove(checkBox.getText().toString()); // Xóa khỏi danh sách nếu không được chọn
            selectedBrands.remove(checkBox.getText().toString()); // Xóa khỏi danh sách nếu không được chọn
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Xóa tất cả các bộ lọc đã chọn
        selectedCategories.clear();
        selectedBrands.clear();
        selectedPriceRanges.clear();

        // Đặt trạng thái của các checkbox về mặc định
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox_states", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Xóa tất cả trạng thái checkbox đã lưu
        editor.apply();
    }

    private void clearCheckBoxStates() {
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox_states", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}