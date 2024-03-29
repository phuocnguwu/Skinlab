package com.example.skinlab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skinlab.adapters.ProductAdapter;
import com.example.skinlab.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> products;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_products, container, false);

        recyclerView = view.findViewById(R.id.rcvProduct);
        products = new ArrayList<>();
        adapter = new ProductAdapter(getActivity(), products);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Load dữ liệu sản phẩm
        loadData();

        return view;
    }

    private void loadData() {
        // Tải dữ liệu sản phẩm vào productList
        // Ví dụ:
        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        // ...
        adapter.notifyDataSetChanged(); // Cập nhật giao diện sau khi thêm dữ liệu
    }
}
