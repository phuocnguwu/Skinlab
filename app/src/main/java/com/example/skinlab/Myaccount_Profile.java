package com.example.skinlab;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityMyaccountProfileBinding;
import com.example.skinlab.databinding.FragmentMyAccountBinding;
import com.squareup.picasso.Picasso;

public class Myaccount_Profile extends AppCompatActivity {
    ActivityMyaccountProfileBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    boolean OpenCam;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        if (OpenCam) {
                            Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                            binding.imvavatar.setImageBitmap(photo);
                        } else {
                            Uri selectedPhotoUri = result.getData().getData();
                            binding.imvavatar.setImageURI(selectedPhotoUri);
                        }

                    }

                }
        );
        databaseHelper = new DatabaseHelper(this);

        String loggedInPhone = getLoggedInPhone();
        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
            // Lấy thông tin của người dùng đăng nhập từ cơ sở dữ liệu
            String avatarUrl = getUserAvatarUrl(loggedInPhone);
            String userName = getUserName(loggedInPhone);
            String userEmail = getUserEmail(loggedInPhone);
            String userPhone = getUserPhone(loggedInPhone);
            String userDOB = getUserDOB(loggedInPhone);
            String userGender = getUserGender(loggedInPhone);

            // Hiển thị thông tin của người dùng đăng nhập
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                Picasso.get().load(avatarUrl).into(binding.imvavatar);
            }
            binding.txtHotenprofile.setText(userName);
            binding.edtHoten.setText(userName);
            binding.edtEmail.setText(userEmail);
            binding.edtsdt.setText(userPhone);
            binding.edtGioitinh.setText(userGender);
            binding.edtngaysinh.setText(userDOB);



        }
    }

    private String getUserGender(String loggedInPhone) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String userGender = "";

        Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_GENDER},
                DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userGenderColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_GENDER);
            if (userGenderColumnIndex != -1) {
                userGender = cursor.getString(userGenderColumnIndex);
            } else {
                Log.e("Error", "Column 'user_phone' does not exist in the result set");
            }
            cursor.close();
        } else {
            Log.e("Error", "No data found in the result set");
        }
        return userGender;
    }

    private String getUserDOB(String loggedInPhone) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String userDOB = "";

        Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_DOB},
                DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userPhoneColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_DOB);
            if (userPhoneColumnIndex != -1) {
                userDOB = cursor.getString(userPhoneColumnIndex);
            } else {
                Log.e("Error", "Column 'user_dob' does not exist in the result set");
            }
            cursor.close();
        } else {
            Log.e("Error", "No data found in the result set");
        }
        return userDOB;
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
    private String getUserPhone(String loggedInPhone) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String userPhone = "";

        Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_PHONE},
                DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userPhoneColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PHONE);
            if (userPhoneColumnIndex != -1) {
                userPhone = cursor.getString(userPhoneColumnIndex);
            } else {
                Log.e("Error", "Column 'user_phone' does not exist in the result set");
            }
            cursor.close();
        } else {
            Log.e("Error", "No data found in the result set");
        }
        return userPhone;
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
    private String getUserEmail(String loggedInPhone) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String userEmail = "";

        Cursor cursor = db.query(DatabaseHelper.USER, new String[]{DatabaseHelper.COLUMN_USER_EMAIL},
                DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{loggedInPhone}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userEmailColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL);
            if (userEmailColumnIndex != -1) {
                userEmail = cursor.getString(userEmailColumnIndex);
            } else {
                Log.e("Error", "Column 'user_name' does not exist in the result set");
            }
            cursor.close();
        } else {
            Log.e("Error", "No data found in the result set");
        }
        return userEmail;
    }

    private String getLoggedInPhone() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInPhone", "");
    }

    private void addEvents() {
        binding.btneditavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();

            }

            private void showBottomSheet() {
                Dialog dialog = new Dialog(Myaccount_Profile.this);
                dialog.setContentView(R.layout.bottomsheet_dialog);

                LinearLayout bsCam = dialog.findViewById(R.id.bsCam);
                bsCam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenCam = true;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        activityResultLauncher.launch(intent);
                        dialog.dismiss();
                    }
                });

                LinearLayout bsGal = dialog.findViewById(R.id.bsGallery);
                bsGal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activityResultLauncher.launch(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }

        });
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        binding.btnsavepf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newhoten = binding.txtHoten.getText().toString();
                String newsdt = binding.txtsdt.getText().toString();
                String newemail = binding.txtEmail.getText().toString();
                binding.txtHoten.setText(newhoten);
                binding.txtsdt.setText(newsdt);
                binding.txtEmail.setText(newemail);
                showAlerDialog();
            }
            private void showAlerDialog() {
                ActivityDialogSaveBinding dialogsaveBinding = ActivityDialogSaveBinding.inflate(LayoutInflater.from(Myaccount_Profile.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(Myaccount_Profile.this)
                        .setView(dialogsaveBinding.getRoot())
                        .setCancelable(true);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(200, 200);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 1000);
            }
        });
    }
}