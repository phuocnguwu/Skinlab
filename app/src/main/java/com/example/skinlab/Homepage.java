package com.example.skinlab;


import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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
//        initData();
        copyDb();
        loadDb();
//        loadData();
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
//            File f = new File(requireContext().getApplicationInfo().dataDir + DB_FOLDER);
//            if(!f.exists()){
//                f.mkdir();
//            }
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
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3);
        binding.rcvProduct.setLayoutManager(layoutManager); // Đặt layout manager cho RecyclerView

        ArrayList<Product> limitedProducts = new ArrayList<>(products.subList(0, Math.min(products.size(), 6)));
        // Khởi tạo adapter và gán danh sách sản phẩm vào adapter
        adapter = new ProductAdapter(requireActivity(), limitedProducts);

        binding.rcvProduct.setAdapter(adapter); // Đặt adapter cho RecyclerView

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
    }

    private void addEvents() {

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String keyword = binding.edtSearch.getText().toString().trim();
//
//                // Kiểm tra xem keyword có rỗng không
//                if (!keyword.isEmpty()) {
//                    // Thực hiện truy vấn dữ liệu từ cơ sở dữ liệu
//                    ArrayList<Product> searchResults = searchProductsByName(keyword);
//
//                    // Hiển thị kết quả tìm kiếm trên RecyclerView
//                    if (searchResults != null && !searchResults.isEmpty()) {
//                        // Cập nhật dữ liệu cho Adapter của RecyclerView
//                        adapter.setData(searchResults);
//                        // Cập nhật giao diện RecyclerView
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        // Hiển thị thông báo cho người dùng không tìm thấy kết quả
//                        Toast.makeText(requireContext(), "Không tìm thấy sản phẩm phù hợp", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // Hiển thị thông báo cho người dùng nhập từ khóa tìm kiếm
//                    Toast.makeText(requireContext(), "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
//                }
//            }

//            private ArrayList<Product> searchProductsByName(String keyword) {
//                ArrayList<Product> searchResults = new ArrayList<>();
//
//                // Thực hiện truy vấn sản phẩm theo tên từ cơ sở dữ liệu
//                // Ví dụ: sử dụng SQLite Database để thực hiện truy vấn
//                // Thay đổi query này tùy theo cơ sở dữ liệu của bạn
//                SQLiteDatabase db = dbHelper.getReadableDatabase();
//                String query = "SELECT * FROM Products WHERE productName LIKE '%" + keyword + "%'";
//                Cursor cursor = db.rawQuery(query, null);
//
//                // Duyệt qua các dòng kết quả và thêm vào danh sách searchResults
//                if (cursor != null) {
//                    while (cursor.moveToNext()) {
//                        int id = cursor.getInt(cursor.getColumnIndex("id"));
//                        String name = cursor.getString(cursor.getColumnIndex("productName"));
//                        // Các thông tin sản phẩm khác tương ứng
//
//                        // Tạo đối tượng Product từ dữ liệu truy vấn
//                        Product product = new Product(id, name, ...);
//                        searchResults.add(product);
//                    }
//                    cursor.close();
//                }
//                db.close();
//
//                return searchResults;
            }
        });

        binding.imvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Donhang_dathang.class);
                startActivity(intent);
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

    private void loadData() {

        // Load dữ liệu bằng cách initData
//        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3);
//        binding.rcvProduct.setLayoutManager(layoutManager);
//
//        adapter = new ProductAdapter(requireActivity(), products);
//        binding.rcvProduct.setAdapter(adapter);
//
//        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Product product) {
//                // Xử lý khi người dùng nhấn vào một item trong RecyclerView
//                Intent intent = new Intent(requireActivity(), Product_Details.class);
//                intent.putExtra("selectedProduct", product);
//                startActivity(intent);
//            }
//        });
    }

//    private void initData() {
//        products = new ArrayList<>();
//        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, 50000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
//    }

}