package com.example.skinlab;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

    public static final String USER = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_PHONE = "user_phone";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_GENDER = "user_gender";
    public static final String COLUMN_USER_DOB = "user_DOB";
    public static final String COLUMN_USER_ADDRESS = "user_address";
    public static final String COLUMN_USER_ADDRESS2 = "user_address2";
    public static final String COLUMN_USER_SKINTYPE = "user_skin";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_AVA = "user_avatar";


    public static final String COLUMN_USER_PASSWORD = "user_password";


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

        binding.btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        binding.btnSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) binding.imvContentImg.getDrawable();
                if (drawable != null) {
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] forumImage = stream.toByteArray();

                    String forumTitle = binding.edtAddTitle.getText().toString();
                    String forumContent = binding.edtAddContent.getText().toString();
                    int forumRating = Integer.parseInt(binding.edtRating.getText().toString());

                    String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                    // Insert user details into the forum
                    Cursor userCursor = db.rawQuery("SELECT " + COLUMN_USER_NAME + ", " + COLUMN_USER_AVA + " FROM " + USER, null);
                    String userName = "";
                    byte[] userAvatar = new byte[0];
                    int userNameIndex = userCursor.getColumnIndex(COLUMN_USER_NAME);
                    int userAvatarIndex = userCursor.getColumnIndex(COLUMN_USER_AVA);
                    if (userCursor.moveToFirst()) {
                        userName = userCursor.getString(userNameIndex);
                        userAvatar = userCursor.getBlob(userAvatarIndex);
                    }
                    userCursor.close();

                    ContentValues values = new ContentValues();
                    values.put(COLUMN_FORUM_AVATAR, userAvatar);
                    values.put(COLUMN_FORUM_NAME, userName);
                    values.put(COLUMN_FORUM_TITLE, forumTitle);
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
                        Toast.makeText(Forum_AddReview_Main.this, "Review added successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Forum_AddReview_Main.this, "Failed to add review!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    // Handle the case when no image is selected
                    Toast.makeText(Forum_AddReview_Main.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private void showAlerDialog() {
        if (!isFinishing()) { // Kiểm tra xem Activity có đang hoạt động hay không
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

}