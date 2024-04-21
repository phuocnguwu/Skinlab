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
import java.util.HashSet;
import java.util.Set;

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

        // Khởi tạo đối tượng Set để lưu trạng thái các thương hiệu được chọn
//        Set<String> selectedBrands = new HashSet<>();

        // Gọi hàm saveCheckBoxState() cho từng CheckBox
//        saveCheckBoxState("Innisfree", binding.checkboxBrandInnisfree, selectedBrands);
//        saveCheckBoxState("Laneige", binding.checkboxBrandLaneige, selectedBrands);
//        saveCheckBoxState("DHC", binding.checkboxBrandDHC, selectedBrands);
//        saveCheckBoxState("Klairs", binding.checkboxBrandKlairs, selectedBrands);


        Intent intent = getIntent();
        if (intent !=null) {
            showAllProducts = intent.getBooleanExtra("showAllProducts", false);
            searchKeyword = intent.getStringExtra("searchKeyword");
        }

        loadDb();
        addEvents();


    }

    private void applyFilters() {
        // Lọc theo danh mục
        StringBuilder categorySelection = new StringBuilder();

        if (!selectedCategories.isEmpty()) {
            categorySelection.append(COLUMN_PD_CATE).append(" IN (");
            for (int i = 0; i < selectedCategories.size(); i++) {
                categorySelection.append("'").append(selectedCategories.get(i)).append("'");
                if (i < selectedCategories.size() - 1) {
                    categorySelection.append(", ");
                }
            }
            categorySelection.append(")");
        }

        // Lọc theo thương hiệu
        StringBuilder brandSelection = new StringBuilder();
        if (!selectedBrands.isEmpty()) {
            brandSelection.append(COLUMN_PD_BRAND).append(" IN (");
            for (int i = 0; i < selectedBrands.size(); i++) {
                brandSelection.append("'").append(selectedBrands.get(i)).append("'");
                if (i < selectedBrands.size() - 1) {
                    brandSelection.append(", ");
                }
            }
            brandSelection.append(")");
        }

        // Lọc theo khoảng giá
        StringBuilder priceSelection = new StringBuilder();
        if (!selectedPriceRanges.isEmpty()) {
            priceSelection.append("(");
            for (int i = 0; i < selectedPriceRanges.size(); i++) {
                int priceRange = selectedPriceRanges.get(i);
                switch (priceRange) {
                    case 1:
                        priceSelection.append(COLUMN_PD_PRICE).append(" < 200000");
                        break;
                    case 2:
                        priceSelection.append(COLUMN_PD_PRICE).append(" BETWEEN 200000 AND 500000");
                        break;
                    case 3:
                        priceSelection.append(COLUMN_PD_PRICE).append(" BETWEEN 500000 AND 750000");
                        break;
                    case 4:
                        priceSelection.append(COLUMN_PD_PRICE).append(" > 750000");
                        break;
                }
                if (i < selectedPriceRanges.size() - 1) {
                    priceSelection.append(" OR ");
                }
            }
            priceSelection.append(")");
        }
        String pselection = priceSelection.toString();

// Kiểm tra xem selection có được xây dựng đúng không bằng cách in ra
        Log.d("Price Selection", pselection);

        // Xây dựng câu truy vấn
        StringBuilder selection = new StringBuilder();
        boolean isFirst = true;
        if (categorySelection.length() > 0) {
            selection.append(categorySelection);
            isFirst = false;
        }
        if (brandSelection.length() > 0) {
            if (!isFirst) {
                selection.append(" AND ");
            }
            selection.append(brandSelection);
            isFirst = false;
        }
        if (priceSelection.length() > 0) {
            if (!isFirst) {
                selection.append(" AND ");
            }
            selection.append(priceSelection);
        }

        // Thực hiện truy vấn
        Cursor cursor = null;
        if (selection.length() > 0) {
            cursor = db.query(TBL_NAME, null, selection.toString(), null, null, null, null);
        } else {
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

            // Cập nhật RecyclerView
            adapter.notifyDataSetChanged();
        }
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
                // Xử lý khi người dùng nhấn vào một item trong RecyclerView
                // Khởi tạo Intent để chuyển sang màn hình chi tiết sản phẩm
                Intent intent = new Intent(Products.this, Product_Details.class);
                // Đính kèm thông tin sản phẩm được chọn vào Intent
                intent.putExtra("selectedProduct", product);
                // Chuyển sang màn hình chi tiết sản phẩm
                startActivity(intent);
            }
        });
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

                Dialog dialog = new Dialog(Products.this);
                dialog.setContentView(R.layout.filter_dialog);

                // Xử lý các lựa chọn
                // Cate
                CheckBox checkboxCate1 = dialog.findViewById(R.id.checkboxCate_SRM);
                checkboxCate1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedCategories.add("Sữa rửa mặt");
                    } else {
                        selectedCategories.remove("Sữa rửa mặt");
                    }
                });
                CheckBox checkboxCate2 = dialog.findViewById(R.id.checkboxCate_ND);
                checkboxCate2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedCategories.add("Nước dưỡng");
                    } else {
                        selectedCategories.remove("Nước dưỡng");
                    }
                });
                CheckBox checkboxCate3 = dialog.findViewById(R.id.checkboxCate_KD);
                checkboxCate3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedCategories.add("Kem dưỡng");
                    } else {
                        selectedCategories.remove("Kem dưỡng");
                    }
                });
                CheckBox checkboxCate4 = dialog.findViewById(R.id.checkboxCate_MN);
                checkboxCate4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedCategories.add("Mặt nạ");
                    } else {
                        selectedCategories.remove("Mặt nạ");
                    }
                });
                CheckBox checkboxCate5 = dialog.findViewById(R.id.checkboxCate_TC);
                checkboxCate5.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedCategories.add("Tinh chất");
                    } else {
                        selectedCategories.remove("Tinh chất");
                    }
                });

                //Brands
                CheckBox checkboxBrand1 = dialog.findViewById(R.id.checkboxBrand_Innisfree);
                checkboxBrand1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                            selectedBrands.add("INNISFREE");
                    } else {
                        selectedBrands.remove("INNISFREE");
                    }
                });
                CheckBox checkboxBrand2 = dialog.findViewById(R.id.checkboxBrand_Laneige);
                checkboxBrand2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedBrands.add("Laneige");
                    } else {
                        selectedBrands.remove("Laneige");
                    }
                });
                CheckBox checkboxBrand3 = dialog.findViewById(R.id.checkboxBrand_AHC);
                checkboxBrand3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedBrands.add("AHC");
                    } else {
                        selectedBrands.remove("AHC");
                    }
                });
                CheckBox checkboxBrand4 = dialog.findViewById(R.id.checkboxBrand_Klairs);
                checkboxBrand4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedBrands.add("Klairs");
                    } else {
                        selectedBrands.remove("Klairs");
                    }
                });

                //Price
                CheckBox checkboxPrice250 = dialog.findViewById(R.id.checkboxPrice_250);
                checkboxPrice250.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedPriceRanges.add(1); // Giá dưới 200,000 đ
                    } else {
                        selectedPriceRanges.remove(Integer.valueOf(1));
                    }
                });

                CheckBox checkboxPrice500 = dialog.findViewById(R.id.checkboxPrice_500);
                checkboxPrice500.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedPriceRanges.add(2); // Giá từ 200,000 đ đến 500,000 đ
                    } else {
                        selectedPriceRanges.remove(Integer.valueOf(2));
                    }
                });

                CheckBox checkboxPrice750 = dialog.findViewById(R.id.checkboxPrice_750);
                checkboxPrice750.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedPriceRanges.add(3); // Giá từ 500,000 đ đến 750,000 đ
                    } else {
                        selectedPriceRanges.remove(Integer.valueOf(3));
                    }
                });

                CheckBox checkboxPrice1000 = dialog.findViewById(R.id.checkboxPrice_1000);
                checkboxPrice1000.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedPriceRanges.add(4); // Giá trên 750,000 đ
                    } else {
                        selectedPriceRanges.remove(Integer.valueOf(4));
                    }
                });




                Button btnDefault = dialog.findViewById(R.id.btnFilter_Default);
                btnDefault.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xóa tất cả các bộ lọc được chọn
                        selectedCategories.clear();
                        selectedBrands.clear();
                        selectedPriceRanges.clear();
//                        loadDb();
                        dialog.dismiss();
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
    private void saveCheckBoxState(String key, CheckBox checkBox, Set<String> selectedSet) {
        SharedPreferences sharedPreferences = getSharedPreferences("FilterPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedSet.add(key);
            } else {
                selectedSet.remove(key);
            }
            editor.putStringSet("selectedBrands", new HashSet<>(selectedSet));
            editor.apply();
        });

        // Khôi phục trạng thái đã lưu từ SharedPreferences
        Set<String> savedSelectedSet = sharedPreferences.getStringSet("selectedBrands", new HashSet<>());
        checkBox.setChecked(savedSelectedSet.contains(key));
    }

}