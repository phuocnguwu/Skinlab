package com.example.skinlab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.adapters.GioHangAdapter;
import com.example.models.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Giohang_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Giohang_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lvGiohang;
    private ArrayList<Product> gioHangItemList;
    private GioHangAdapter gioHangAdapter;


    public Giohang_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Giohang_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Giohang_Fragment newInstance(String param1, String param2) {
        Giohang_Fragment fragment = new Giohang_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_giohang_, container, false);

        // Khởi tạo danh sách giỏ hàng
        gioHangItemList = new ArrayList<>();
        // Thêm các item vào danh sách giỏ hàng (giả sử bạn có danh sách các sản phẩm)
        gioHangItemList.add(new Product("https://i.pinimg.com/736x/bf/69/e1/bf69e1c1f03979c3f712a5c0a86d4da8.jpg",null, "Nước tẩy trang", 189000,0,null,null,null,null));

        // Ánh xạ ListView
        lvGiohang = view.findViewById(R.id.lvGiohang);

        // Khởi tạo và thiết lập adapter
        gioHangAdapter = new GioHangAdapter(getContext(), R.layout.giohang_item, gioHangItemList);
        lvGiohang.setAdapter(gioHangAdapter);

        return view;
    }
}