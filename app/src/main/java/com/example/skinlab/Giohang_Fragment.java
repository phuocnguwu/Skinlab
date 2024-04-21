package com.example.skinlab;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.adapters.GioHangAdapter;
import com.example.models.Product;
import com.example.skinlab.databinding.FragmentGiohangBinding;
import java.util.ArrayList;

public class Giohang_Fragment extends Fragment {
    private ListView lvGiohang;
    private GioHangAdapter gioHangAdapter;
    private ArrayList<Product> gioHangItemList = new ArrayList<>();
    FragmentGiohangBinding binding;
    Product selectedProduct;

    public Giohang_Fragment() {}

//    public static Giohang_Fragment newInstance(Bundle bundle) {
//        Giohang_Fragment giohangFragment = new Giohang_Fragment();
//        if (bundle != null) {
//            giohangFragment.setArguments(bundle);
//        }
//        return giohangFragment;
//    }

//    private ArrayList<Product> gioHangItemList;

//    public void addToCart(Product product, Context context) {
//        if (lvGiohang != null) {
//            if (gioHangAdapter == null) {
//                gioHangItemList.add(product);
//                gioHangAdapter = new GioHangAdapter(context, R.layout.giohang_item, gioHangItemList);
//                lvGiohang.setAdapter(gioHangAdapter);
//            } else {
//                gioHangItemList.add(product);
//                gioHangAdapter.notifyDataSetChanged();
//            }
//        } else {
//            // Log hoặc xử lý trường hợp lvGiohang không được tìm thấy
//            Log.e("Giohang_Fragment", "ListView lvGiohang is null");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGiohangBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        gioHangItemList = new ArrayList<>();
//        gioHangItemList.add(new Product("https://i.pinimg.com/736x/bf/69/e1/bf69e1c1f03979c3f712a5c0a86d4da8.jpg", null, "Nước tẩy trang", 189000, 0, null, null, null, null));
        lvGiohang = view.findViewById(R.id.lvGiohang);
//        if (getArguments() != null) {
//            getData();
//        }
        gioHangAdapter = new GioHangAdapter(getContext(), R.layout.giohang_item, gioHangItemList);
        lvGiohang.setAdapter(gioHangAdapter);
        return view;
    }

    public void onStart() {
//        gioHangItemList.add(new Product("https://i.pinimg.com/736x/bf/69/e1/bf69e1c1f03979c3f712a5c0a86d4da8.jpg", null, "Nước tẩy trang", 189000, 0, null, null, null, null));
        super.onStart();
    }

    public void onResume() {
        Log.d("Resume", "có chạy nè");
        if (getArguments() != null) {
            getData();
        }
//        Bundle bundle = getArguments();
//        Log.d("Product_Details", "selectedProduct: Price = " + bundle);
//        if (bundle != null && bundle.containsKey("selectedProduct")) {
//            // Nhận selectedProduct từ bundle
//            selectedProduct = (Product) bundle.getSerializable("selectedProduct");
//            // Thêm selectedProduct vào danh sách và cập nhật adapter
//            gioHangItemList.add(selectedProduct);
//            gioHangAdapter = new GioHangAdapter(getContext(), R.layout.giohang_item, gioHangItemList);
//            lvGiohang.setAdapter(gioHangAdapter);
//        } else {
//            Log.d("Giohang_Fragment", "selectedProduct not found in arguments");
//        }
        super.onResume();
    }

    private void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.d("bundle", "huhu" + bundle);
            Product p = (Product) bundle.getSerializable("selectedProduct");
//            gioHangItemList.add(new Product("https://i.pinimg.com/736x/bf/69/e1/bf69e1c1f03979c3f712a5c0a86d4da8.jpg", null, "Nước tẩy trang", 189000, 0, null, null, null, null));
//            lvGiohang = view.findViewById(R.id.lvGiohang);
            if (p != null) {
            Log.d("Product_Details", "selectedProduct: " + p);
            Log.d("Product_Details", "selectedProduct: ID = " + p.getPd_id());
            Log.d("Product_Details", "selectedProduct: Name = " + p.getPd_name());
            Log.d("Product_Details", "selectedProduct: Price = " + p.getPd_price());

                gioHangItemList.add(p);
//                gioHangItemList.notifyDataSetChanged();
            } else {
                Log.d("product", "đ có product đâu");

            }
        } else {
            Log.d("Bundle", "Đ có Bundle");
        }
    }
}
