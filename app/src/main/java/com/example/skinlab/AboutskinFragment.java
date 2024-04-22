package com.example.skinlab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapters.ProductAdapter;
import com.example.models.Product;
import com.example.skinlab.databinding.FragmentAboutskinBinding;
import com.example.skinlab.databinding.FragmentForumBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutskinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutskinFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutskinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutskinFragment.
     */
    // TODO: Rename and change types and number of parameters

    FragmentAboutskinBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;
    DatabaseHelper dbHelper;

    String userSkinType;

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



    public static AboutskinFragment newInstance(String param1, String param2) {
        AboutskinFragment fragment = new AboutskinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAboutskinBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(requireContext());

        addEvents();
        loadDb();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDb();
    }


    private void loadDb() {
        db = SQLiteDatabase.openDatabase(requireContext().getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        products = new ArrayList<>();

        String loggedInPhone = getLoggedInPhone();
        userSkinType = dbHelper.getUserSkinType(loggedInPhone);
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
                    if (pdSkintype != null && userSkinType != null && pdSkintype.contains(userSkinType)) {
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
        // Tạo layout manager cho RecyclerView (ở đây là GridLayoutManager)
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3);
        binding.rcvProduct.setLayoutManager(layoutManager); // Đặt layout manager cho RecyclerView

        ArrayList<Product> limitedProducts = new ArrayList<>(products.subList(0, Math.min(products.size(), 6)));
        // Khởi tạo adapter và gán danh sách sản phẩm vào adapter
        adapter = new ProductAdapter(requireActivity(), limitedProducts);

        binding.rcvProduct.setAdapter(adapter); // Đặt adapter cho RecyclerView
        adapter.notifyDataSetChanged();


        // Định nghĩa sự kiện click trên mỗi sản phẩm trong RecyclerView
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Xử lý khi người dùng nhấn vào một item trong RecyclerView
                // Khởi tạo Intent để chuyển sang màn hình chi tiết sản phẩm
                Intent intent = new Intent(requireActivity(), Product_Details.class);
                // Đính kèm thông tin sản phẩm được chọn vào Intent
                intent.putExtra("selectedProduct", product);
                // Chuyển sang màn hình chi tiết sản phẩm
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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }



    private void addEvents() {
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(requireActivity(), Aboutskin_intro_test.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });

        binding.ibtnChutrinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(requireActivity(), Aboutskin_chutrinh.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });

        binding.ibtnLichhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent để chuyển từ Main_Aboutskin đến Aboutskin_intro_test
                Intent intent = new Intent(requireActivity(), Aboutskin_lichhen.class);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });

    }
}