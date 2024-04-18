package com.example.skinlab;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skinlab.databinding.FragmentAboutskinBinding;
import com.example.skinlab.databinding.FragmentForumBinding;

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
        addEvents();
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