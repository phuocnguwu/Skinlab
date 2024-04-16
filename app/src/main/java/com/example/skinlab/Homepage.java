package com.example.skinlab;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapters.ProductAdapter;
import com.example.adapters.SliderRecyclerAdapter;
import com.example.models.Product;
import com.example.models.Slider;
import com.example.skinlab.databinding.ActivityMainContaintFragmentBinding;
import com.example.skinlab.databinding.FragmentHomepageBinding;
import com.example.skinlab.databinding.FragmentMyAccountBinding;

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
        initData();
        loadData();
        addEvents();
        loadSlider();
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
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3);
        binding.rcvProduct.setLayoutManager(layoutManager);

        adapter = new ProductAdapter(requireActivity(), products);
        binding.rcvProduct.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Xử lý khi người dùng nhấn vào một item trong RecyclerView
                Intent intent = new Intent(requireActivity(), Product_Details.class);
                intent.putExtra("selectedProduct", product);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        products = new ArrayList<>();
        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product1, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product2, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000,50000,  "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
        products.add(new Product(R.drawable.product3, "product01", "Kem dưỡng ẩm trà xanh Innisfree Green Tea Seed", 468000, 50000, "Innisfree", "Kem dưỡng", "Kem dưỡng ẩm trà xanh innisfree Green Tea Seed Cream, giải pháp cấp ẩm và làm dịu cho da bổ sung lớp màng dưỡng ẩm để bảo bệ da khỏi những tác hại bởi việc mất nước gây ra."));
    }

}