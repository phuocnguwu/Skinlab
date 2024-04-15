package com.example.skinlab;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.skinlab.databinding.ActivityMyaccountBinding;

public class Myaccount extends AppCompatActivity {

    ActivityMyaccountBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    boolean OpenCam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyaccountBinding.inflate(getLayoutInflater());
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
    }

    private void addEvents() {
        binding.moronghoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount.this, Myaccount_Profile.class);
                startActivity(intent);
            }
        });
        binding.morongdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount.this, Donhang_dathang.class);
                startActivity(intent);

            }
        });
        binding.morongdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myaccount.this, Myaccount_Diachi.class);
                startActivity(intent);

            }
        });
        binding.morongcaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.morongdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btneditavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();

            }

            private void showBottomSheet() {
                Dialog dialog = new Dialog(Myaccount.this);
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
    }
}