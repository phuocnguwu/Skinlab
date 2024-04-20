package com.example.skinlab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.skinlab.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    FragmentLoginBinding binding;
    private boolean isLoggedIn;
    private DatabaseHelper databaseHelper;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        databaseHelper = new DatabaseHelper(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvents();
    }

    private void addEvents() {
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPhone = binding.editTextEmailPhone.getText().toString().trim();
                String password = binding.editTextPassword.getText().toString().trim();
                if (emailPhone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm log để kiểm tra dữ liệu đầu vào
                    Log.d("LoginFragment", "Số điện thoại: " + emailPhone + ", Mật khẩu: " + password);

                    // Kiểm tra đăng nhập
                    if (databaseHelper.checkLogin(emailPhone, password)) {
                        isLoggedIn = true;
                        saveLoginStatus(isLoggedIn, emailPhone); // Lưu sđt của người dùng đăng nhập
                        Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        // In ra để kiểm tra số điện thoại mới đã được cập nhật chưa
                        Log.d("LoginFragment", "Số điện thoại của người dùng mới: " + emailPhone);
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.containerLayout, new MyAccountFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else {
                        Toast.makeText(getContext(), "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private void saveLoginStatus(boolean isLoggedIn, String loggedInPhone) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", isLoggedIn);
                editor.putString("loggedInPhone", loggedInPhone); // Lưu sđt của người dùng đăng nhập
                editor.apply();
            }
        });
        binding.textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Signup.class);
                startActivity(intent);
            }
        });
    }
}


