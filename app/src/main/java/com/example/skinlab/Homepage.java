package com.example.skinlab;


import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adapters.ProductAdapter;
import com.example.adapters.SliderRecyclerAdapter;
import com.example.models.Product;
import com.example.models.Slider;
import com.example.skinlab.databinding.ActivityMainContaintFragmentBinding;
import com.example.skinlab.databinding.FragmentHomepageBinding;
import com.example.skinlab.databinding.FragmentMyAccountBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homepage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homepage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Homepage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Homepage.
     */
    // TODO: Rename and change types and number of parameters
    public static Homepage newInstance(String param1, String param2) {
        Homepage fragment = new Homepage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentHomepageBinding binding;
    ProductAdapter adapter;

    SliderRecyclerAdapter adapter2;

    ArrayList<Product> products;

    ArrayList<Slider> sliders;

    public static final String DB_NAME = "Skinlab.db";
    public static final String DB_FOLDER = "databases";


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
    public static final String COLUMN_PD_RATING = "rating";
    private int currentPosition = 0;
    private final int DELAY_MS = 2000;
    private Handler sliderHandler;
    private Runnable sliderRunnable;


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
        binding = FragmentHomepageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        copyDb();
        loadDb();
        addEvents();
        loadSlider();
    }


    private void copyDb() {
        File dbFile = requireContext().getDatabasePath(DB_NAME);
        if(!dbFile.exists()){
            if(copyDbFromAssets())
                Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(requireContext(), "Fail!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean copyDbFromAssets() {
        String dbPath = requireContext().getApplicationInfo().dataDir + "/" + DB_FOLDER + "/" + DB_NAME;
        //data/data/packageName/databases/product_db.db
        try {
            InputStream inputStream = requireContext().getAssets().open(DB_NAME);
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024]; int length;
            while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length);
            }
            outputStream.flush(); outputStream.close(); inputStream.close();
            Log.d("CopyDB", "Database copied successfully!");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("CopyDB", "Error copying database: " + e.getMessage());
            return false;
        }
    }

    private void loadDb() {
        db = SQLiteDatabase.openDatabase(requireContext().getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
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
                int columnIndexRating = cursor.getColumnIndex(COLUMN_PD_RATING);

                if (columnIndexId != -1 && columnIndexName != -1 && columnIndexPrice != -1 &&
                        columnIndexPrice2 != -1 && columnIndexBrand != -1 && columnIndexCate != -1 &&
                        columnIndexDes != -1 && columnIndexPhoto != -1 && columnIndexSkintype != -1 && columnIndexRating != -1) {

                    String pdId = cursor.getString(columnIndexId);
                    String pdName = cursor.getString(columnIndexName);
                    int pdPrice = cursor.getInt(columnIndexPrice);
                    int pdPrice2 = cursor.getInt(columnIndexPrice2);
                    String pdBrand = cursor.getString(columnIndexBrand);
                    String pdCate = cursor.getString(columnIndexCate);
                    String pdDes = cursor.getString(columnIndexDes);
                    String pdPhoto = cursor.getString(columnIndexPhoto);
                    String pdSkintype = cursor.getString(columnIndexSkintype);
                    int pdRating = cursor.getInt(columnIndexRating);
//
//                    // Tạo đối tượng Product từ dữ liệu truy vấn
                    Product product = new Product(pdPhoto, pdId, pdName, pdPrice, pdPrice2, pdBrand, pdCate, pdDes, pdSkintype, pdRating);
                    products.add(product);
                }
            }
            while (cursor.moveToNext()) ;
            cursor.close();
        }

        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3);
        binding.rcvProduct.setLayoutManager(layoutManager);

        ArrayList<Product> limitedProducts = new ArrayList<>(products.subList(0, Math.min(products.size(), 9)));
        adapter = new ProductAdapter(requireActivity(), limitedProducts);
        binding.rcvProduct.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(requireActivity(), Product_Details.class);
                intent.putExtra("selectedProduct", product);
                startActivity(intent);
            }
        });
    }

    private void loadSlider() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rcvSlider.setLayoutManager(layoutManager);
        binding.rcvSlider.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), layoutManager.getOrientation());
        binding.rcvSlider.addItemDecoration(dividerItemDecoration);
        sliders = new ArrayList<>();
        sliders.add(new Slider(R.drawable.slider1));
        sliders.add(new Slider(R.drawable.slider2));
        adapter2 = new SliderRecyclerAdapter(requireContext(), sliders);
        binding.rcvSlider.setAdapter(adapter2);
        // Tự động chuyển ảnh
        sliderHandler = new Handler();
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == sliders.size()) {
                    currentPosition = 0;
                    // Đưa slider về vị trí đầu tiên
                    binding.rcvSlider.scrollToPosition(currentPosition);
                } else {
                    binding.rcvSlider.smoothScrollToPosition(currentPosition++);
                }
                sliderHandler.postDelayed(this, DELAY_MS);
            }
        };
        sliderHandler.postDelayed(sliderRunnable, DELAY_MS);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (sliderHandler != null && sliderRunnable != null) {
            sliderHandler.removeCallbacks(sliderRunnable);
        }
    }

    private void addEvents() {

        // Category
        binding.layoutSuaruamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gửi Intent tới Products với category tương ứng
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("category", "Sữa rửa mặt");
                startActivity(intent);
            }
        });

        binding.layoutNuocduong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("category", "Nước dưỡng");
                startActivity(intent);
            }
        });

        // Tương tự cho các category khác
        binding.layoutKemduong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("category", "Kem dưỡng");
                startActivity(intent);
            }
        });

        binding.layoutTinhchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("category", "Tinh chất");
                startActivity(intent);
            }
        });

        binding.layoutMatna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("category", "Mặt nạ");
                startActivity(intent);
            }
        });

        binding.layoutInnisfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("brand", "INNISFREE");
                startActivity(intent);
            }
        });
        binding.layoutKlairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("brand", "Klairs");
                startActivity(intent);
            }
        });
        binding.layoutAHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("brand", "AHC");
                startActivity(intent);
            }
        });
        binding.layoutLaneige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("brand", "Laneige");
                startActivity(intent);
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = binding.edtSearch.getText().toString().trim();

                // Kiểm tra xem keyword có rỗng không
                if (!keyword.isEmpty()) {
                    // Tạo Intent để chuyển sang trang Product
                    Intent intent = new Intent(requireActivity(), Products.class);
                    // Đính kèm từ khoá tìm kiếm vào Intent
                    intent.putExtra("searchKeyword", keyword);
                    // Chuyển sang trang Product
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo cho người dùng nhập từ khóa tìm kiếm
                    Toast.makeText(requireContext(), "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.imvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một đối tượng FragmentTransaction
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                // Thêm Giohang_Fragment vào FragmentManager và thay thế fragment hiện tại
                transaction.replace(R.id.containerLayout, new Giohang_Fragment());

                // Thực hiện giao dịch
                transaction.commit();
            }
        });

        binding.tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                startActivity(intent);
            }
        });
        binding.txtProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Products.class);
                intent.putExtra("showAllProducts", true);
                intent.putExtra("searchKeyword", "");
                intent.putExtra("category", "Sữa rửa mặt");
                intent.putExtra("brand", "INNISFREE");
                startActivity(intent);
            }
        });
        binding.btnLichhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Aboutskin_lichhen.class);
                startActivity(intent);
            }
        });

    }

}