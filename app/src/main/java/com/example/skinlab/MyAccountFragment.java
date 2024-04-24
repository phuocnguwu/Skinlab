package com.example.skinlab;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.skinlab.databinding.FragmentMyAccountBinding;
import com.squareup.picasso.Picasso;

public class MyAccountFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentMyAccountBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    boolean OpenCam;
    DatabaseHelper databaseHelper;


    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private String mParam1;

    private String mParam2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        databaseHelper = new DatabaseHelper(requireContext());




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvents();
// Đăng ký sự kiện trở lại khi Fragment được hiển thị
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Thực hiện các hoạt động cần thiết khi nhấn nút back trong Fragment
                // Ví dụ: hiển thị hộp thoại xác nhận, hoặc đóng Fragment
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        String loggedInPhone = getLoggedInPhone();
        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
            // Lấy thông tin của người dùng đăng nhập từ cơ sở dữ liệu
            String avatarUrl = getUserAvatarUrl(loggedInPhone);
            String userName = getUserName(loggedInPhone);
            // Hiển thị thông tin của người dùng đăng nhập
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                Picasso.get().load(avatarUrl).into(binding.imvavatar);
            }
            binding.txtHotenprofile.setText(userName);
        }
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                if (OpenCam) {
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    binding.imvavatar.setImageBitmap(photo);
                } else {
                    Uri selectedPhotoUri = result.getData().getData();
                    binding.imvavatar.setImageURI(selectedPhotoUri);
                }
            }
        });
    }

    private String getLoggedInPhone() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }
    private String getUserAvatarUrl(String loggedInPhone) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String avatarUrl = "";

        Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_AVA},
                DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userAvatarColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_AVA);
            if (userAvatarColumnIndex != -1) {
                avatarUrl = cursor.getString(userAvatarColumnIndex);
            } else {
                Log.e("Error", "Column 'user_avatar' does not exist in the result set");
            }
            cursor.close();
        } else {
            Log.e("Error", "No data found in the result set");
        }
        return avatarUrl;
    }

    private String getUserName(String loggedInPhone) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String userName = "";

        Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_NAME},
                DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userNameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NAME);
            if (userNameColumnIndex != -1) {
                userName = cursor.getString(userNameColumnIndex);
            } else {
                Log.e("Error", "Column 'user_name' does not exist in the result set");
            }
            cursor.close();
        } else {
            Log.e("Error", "No data found in the result set");
        }
        return userName;
    }
    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void addEvents() {
        binding.moronghoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Myaccount_Profile.class);
                startActivity(intent);
            }
        });

        binding.morongdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Donhang_Fragment.class);
                startActivity(intent);

            }
        });
        binding.morongdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Myaccount_Diachi.class);
                startActivity(intent);

            }
        });



        binding.morongdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn đăng xuất không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", false);
                        editor.apply();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.containerLayout, new LoginFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

 

    public void updateUserInfo(String hoTen, Uri avatarUri) {
        binding.txtHotenprofile.setText(hoTen);
        if (avatarUri != null) {
            Picasso.get().load(avatarUri).into(binding.imvavatar);
        }
    }
    public void updateUserProfile(String hoTen, Uri avatarUri) {
        updateUserInfo(hoTen, avatarUri);
    }
    public void refreshUserProfile() {
        // Thực hiện tải lại thông tin người dùng từ cơ sở dữ liệu hoặc từ bất kỳ nguồn dữ liệu nào khác
        String loggedInPhone = getLoggedInPhone();
        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
            // Lấy thông tin của người dùng đăng nhập từ cơ sở dữ liệu hoặc bất kỳ nguồn dữ liệu nào khác
            String avatarUrl = getUserAvatarUrl(loggedInPhone);
            String userName = getUserName(loggedInPhone);
            // Cập nhật thông tin người dùng trên giao diện
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                Picasso.get().load(avatarUrl).into(binding.imvavatar);
            }
            // Cập nhật các trường thông tin khác của người dùng trên giao diện
            binding.txtHotenprofile.setText(userName);
            // Cập nhật các trường thông tin khác của người dùng trên giao diện
        }
    }



}
