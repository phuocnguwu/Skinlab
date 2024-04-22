
package com.example.skinlab;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.interfaces.OnUserInfoUpdateListener;
import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityMyaccountProfileBinding;
import com.example.skinlab.databinding.FragmentMyAccountBinding;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class Myaccount_Profile extends AppCompatActivity implements OnUserInfoUpdateListener {
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
        // Khởi tạo onBackPressedDispatcher
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Gửi một sự kiện hoặc dấu hiệu cho Fragment rằng nó nên được hiển thị lại
                Intent intent = new Intent();
                intent.putExtra("refreshFragment", true);
                setResult(RESULT_OK, intent);
                finish(); // Hoặc thực hiện các hoạt động khác khi cần
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

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

    public String getUserName(String loggedInPhone) {
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
                MyAccountFragment fragment = (MyAccountFragment) getSupportFragmentManager().findFragmentById(R.id.containerLayout);
                if (fragment != null) {
                    fragment.refreshUserProfile();
                }
                onBackPressed();
            }
        });
        binding.btnsavepf.setOnClickListener(v -> saveUserProfile());

    }

    private void saveUserProfile() {
        String newHoTen = binding.edtHoten.getText().toString();
        String newSdt = binding.edtsdt.getText().toString();
        String newEmail = binding.edtEmail.getText().toString();
        String newGioiTinh = binding.edtGioitinh.getText().toString();
        String newNgaySinh = binding.edtngaysinh.getText().toString();
        Uri newAvatarUri = getImageUriFromImageView(binding.imvavatar);
        // Kiểm tra các điều kiện
        if (!isValidPhoneNumber(newSdt)) {
            // Số điện thoại không hợp lệ
            showToast("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
            return;
        }

        if (!isValidEmail(newEmail)) {
            // Email không hợp lệ
            showToast("Email không hợp lệ. Vui lòng nhập lại.");
            return;
        }

        updateUserInDatabase(newHoTen, newSdt, newEmail, newGioiTinh, newNgaySinh, newAvatarUri);
        showToast("Thông tin đã được cập nhật");
        // Gọi phương thức onUpdateUserInfo để thông báo rằng thông tin đã được cập nhật
        onUpdateUserInfo(newHoTen, newAvatarUri);

    }

    // Phương thức kiểm tra số điện thoại hợp lệ
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("0") && phoneNumber.length() == 10;
    }

    // Phương thức kiểm tra email hợp lệ
    private boolean isValidEmail(String email) {
        // Đây là một kiểm tra đơn giản, bạn có thể thêm các điều kiện kiểm tra khác nếu cần
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private Uri getImageUriFromImageView(ImageView imageView) {
        if (binding.imvavatar.getDrawable() instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.imvavatar.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            return getImageUri(bitmap);
        } else {
            return null;
        }
    }
    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);

        if (path != null) {
            return Uri.parse(path);
        } else {
            // Xử lý trường hợp không thể chèn hình ảnh vào MediaStore, có thể trả về Uri mặc định hoặc null.
            return null; // hoặc trả về Uri mặc định hoặc thực hiện hành động khác tùy thuộc vào logic của ứng dụng.
        }
    }
//    private Uri getImageUri(Bitmap bitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
//        return Uri.parse(path);
//    }

    private void updateUserInDatabase(String hoTen, String sdt, String email, String gioiTinh, String ngaySinh, Uri avatarUri) {
        // Get the writable database
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Create a ContentValues object to store the new values
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_NAME, hoTen);
        values.put(DatabaseHelper.COLUMN_USER_PHONE, sdt);
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_USER_GENDER, gioiTinh);
        values.put(DatabaseHelper.COLUMN_USER_DOB, ngaySinh);

        // Check if an avatar URI is provided
        if (avatarUri != null) {
            // If avatar URI is provided, convert it to string and store it in ContentValues
            String avatarUrl = avatarUri.toString();
            values.put(DatabaseHelper.COLUMN_USER_AVA, avatarUrl);
        }

        // Define the selection clause to update the correct user
        String selection = DatabaseHelper.COLUMN_USER_PHONE + " = ?";
        String[] selectionArgs = {getLoggedInPhone()};

        // Update the user information in the database
        int rowsUpdated = db.update(DatabaseHelper.USER, values, selection, selectionArgs);

        // Close the database
        db.close();
        // Check if the update was successful
        if (rowsUpdated > 0) {
            // Update successful, show a message to the user if needed
            binding.txtHotenprofile.setText(hoTen);
        } else {
            // Update failed, show an error message to the user if needed
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserInfo(String hoTen, Uri avatarUri) {
        // Gọi phương thức updateUserInfo của MyAccountFragment
        MyAccountFragment fragment = (MyAccountFragment) getSupportFragmentManager().findFragmentById(R.id.containerLayout);
        if (fragment != null) {
            fragment.updateUserInfo(hoTen, avatarUri);
        }
    }
}