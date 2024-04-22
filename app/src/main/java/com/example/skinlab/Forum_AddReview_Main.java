package com.example.skinlab;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.models.Forum;
import com.example.skinlab.databinding.ActivityDialogSaveBinding;
import com.example.skinlab.databinding.ActivityForumAddReviewMainBinding;
import com.example.skinlab.databinding.ActivityForumDialogSendBinding;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Forum_AddReview_Main extends AppCompatActivity {
    ActivityForumAddReviewMainBinding binding;
    ArrayList<Forum> forums;
    ActivityResultLauncher<Intent> launcher;
    ActivityResultLauncher<Intent> launcher1;
    boolean openCam;

    public static final String DB_NAME = "Skinlab.db";
    public static final String DB_FOLDER = "databases";


    public static SQLiteDatabase db = null;

    public static final String TBL_NAME = "forum";
    public static final String COLUMN_FORUM_ID = "forum_id";
    public static final String COLUMN_FORUM_NAME = "forum_name";
    public static final String COLUMN_FORUM_AVATAR = "forum_avatar";
    public static final String COLUMN_FORUM_DATE = "forum_date";
    public static final String COLUMN_FORUM_RATING = "forum_rating";
    public static final String COLUMN_FORUM_TITLE = "forum_title";
    public static final String COLUMN_FORUM_SHORTCONTENT = "forum_shortcontent";
    public static final String COLUMN_FORUM_CONTENT = "forum_content";
    public static final String COLUMN_FORUM_CONTENT_IMG = "forum_content_img";
    public static final String COLUMN_FORUM_COMMENT_NAME1 = "forum_comment_name1";
    public static final String COLUMN_FORUM_COMMENT_CONTENT1 = "forum_comment_content1";
    public static final String COLUMN_FORUM_COMMENT_NAME2 = "forum_comment_name2";
    public static final String COLUMN_FORUM_COMMENT_CONTENT2 = "forum_comment_content2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumAddReviewMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();

        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (openCam) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        binding.imvAvatar.setImageBitmap(photo);
                    } else {
                        Uri selectedPhotoUri = result.getData().getData();
                        binding.imvAvatar.setImageURI(selectedPhotoUri);
                    }

                }
            });

        launcher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        if (openCam) {
                            Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                            binding.imvContentImg.setImageBitmap(photo);
                        } else {
                            Uri selectedPhotoUri = result.getData().getData();
                            binding.imvContentImg.setImageURI(selectedPhotoUri);
                        }

                    }
                });

    }

    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum_AddReview_Main.this, ForumFragment.class);
                startActivity(intent);
            }
        });
        binding.imvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });

        binding.btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet1();
            }
        });
        binding.btnSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable forumContentImg = (BitmapDrawable) binding.imvContentImg.getDrawable();
                BitmapDrawable forumAvatarImg = (BitmapDrawable) binding.imvAvatar.getDrawable();
                Bitmap bitmap_cimg = forumContentImg.getBitmap();
                Bitmap bitmap_avatar = forumAvatarImg.getBitmap();
                ByteArrayOutputStream stream_cimg = new ByteArrayOutputStream();
                ByteArrayOutputStream stream_avatar = new ByteArrayOutputStream();
                bitmap_cimg.compress(Bitmap.CompressFormat.PNG, 100, stream_cimg);
                bitmap_avatar.compress(Bitmap.CompressFormat.PNG, 100, stream_avatar);
                byte[] forumImage = stream_cimg.toByteArray();
                byte[] forumAvatar = stream_avatar.toByteArray();
                String forumName = binding.txtName.getText().toString();
                String forumTitle = binding.edtAddTitle.getText().toString();
                String forumShortContent = binding.edtShortContent.getText().toString();
                String forumContent = binding.edtAddContent.getText().toString();
                int forumRating = Integer.parseInt(binding.edtRating.getText().toString());

                // Lấy thời gian hiện tại
                String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                ContentValues values = new ContentValues();
                values.put(COLUMN_FORUM_NAME, forumName);
                values.put(COLUMN_FORUM_AVATAR, forumAvatar);
                values.put(COLUMN_FORUM_TITLE, forumTitle);
                values.put(COLUMN_FORUM_SHORTCONTENT, forumShortContent);
                values.put(COLUMN_FORUM_CONTENT, forumContent);
                values.put(COLUMN_FORUM_CONTENT_IMG, forumImage);
                values.put(COLUMN_FORUM_RATING, forumRating);
                values.put(COLUMN_FORUM_DATE, currentDateAndTime);
                values.put(COLUMN_FORUM_COMMENT_NAME1, "");
                values.put(COLUMN_FORUM_COMMENT_CONTENT1, "");
                values.put(COLUMN_FORUM_COMMENT_NAME2, "");
                values.put(COLUMN_FORUM_COMMENT_CONTENT2, "");

                long numbOfRows = db.insert(TBL_NAME, null, values);
                if (numbOfRows > 0) {
                    showAlerDialog();
                }
                finish();
            }

        });
    }
    private void showAlerDialog() {
        if (!isFinishing()) {
            ActivityForumDialogSendBinding forumdialogsendBinding = ActivityForumDialogSendBinding.inflate(LayoutInflater.from(Forum_AddReview_Main.this));
            AlertDialog.Builder builder = new AlertDialog.Builder(Forum_AddReview_Main.this)
                    .setView(forumdialogsendBinding.getRoot())
                    .setCancelable(true);

            // Create dialog
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
    }

    private void showBottomSheet() {
        Dialog dialog = new Dialog(Forum_AddReview_Main.this);
        dialog.setContentView(R.layout.bottomsheet_dialog);

        LinearLayout bsCam = dialog.findViewById(R.id.bsCam);
        bsCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCam = true;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                launcher.launch(intent);
                dialog.dismiss();
            }
        });

        LinearLayout bsGal = dialog.findViewById(R.id.bsGallery);
        bsGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCam = false;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void showBottomSheet1() {
        Dialog dialog = new Dialog(Forum_AddReview_Main.this);
        dialog.setContentView(R.layout.bottomsheet_dialog);

        LinearLayout bsCam = dialog.findViewById(R.id.bsCam);
        bsCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCam = true;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher1.launch(intent);
                dialog.dismiss();
            }
        });

        LinearLayout bsGal = dialog.findViewById(R.id.bsGallery);
        bsGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCam = false;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher1.launch(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}