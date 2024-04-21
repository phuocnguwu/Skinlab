package com.example.skinlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.adapters.ForumAdapter;
import com.example.models.Forum;
import com.example.models.Product;
import com.example.skinlab.databinding.ActivityForumDetailedBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class Forum_Detailed extends AppCompatActivity {
    ActivityForumDetailedBinding binding;
    ArrayList<Forum> forums;
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
    public static final String COLUMN_FORUM_IMG = "forum_img";
    public static final String COLUMN_FORUM_COMMENT_NAME1 = "forum_comment_name1";
    public static final String COLUMN_FORUM_COMMENT_CONTENT1 = "forum_comment_content1";
    public static final String COLUMN_FORUM_COMMENT_NAME2 = "forum_comment_name2";
    public static final String COLUMN_FORUM_COMMENT_CONTENT2 = "forum_comment_content2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = openOrCreateDatabase("Skinlab.db", MODE_PRIVATE, null);

        int selectedForumId = getIntent().getIntExtra("forum_id", -1);
        Log.d("Forum_Detailed", "Selected Forum ID: " + selectedForumId);

        if (selectedForumId != -1) {
            loadDataDetails(selectedForumId);
        }
//        loadDb();

        addEvents();
    }

    private void loadDataDetails (int forumId){
        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + COLUMN_FORUM_ID + " = " + forumId, null);
        int forumNameIndex = cursor.getColumnIndex(COLUMN_FORUM_NAME);
        int forumAvatarIndex = cursor.getColumnIndex(COLUMN_FORUM_AVATAR);
        int forumRatingIndex = cursor.getColumnIndex(COLUMN_FORUM_RATING);
        int forumDateIndex = cursor.getColumnIndex(COLUMN_FORUM_DATE);
        int forumTitleIndex = cursor.getColumnIndex(COLUMN_FORUM_TITLE);
        int forumContentIndex = cursor.getColumnIndex(COLUMN_FORUM_CONTENT);
        int forumImageIndex = cursor.getColumnIndex(COLUMN_FORUM_IMG);
        int forumCommentName1Index = cursor.getColumnIndex(COLUMN_FORUM_COMMENT_NAME1);
        int forumCommentContent1Index = cursor.getColumnIndex(COLUMN_FORUM_COMMENT_CONTENT1);
        int forumCommentName2Index = cursor.getColumnIndex(COLUMN_FORUM_COMMENT_NAME2);
        int forumCommentContent2Index = cursor.getColumnIndex(COLUMN_FORUM_COMMENT_CONTENT2);

        if (cursor.moveToFirst()) {
            String forumName = cursor.getString(forumNameIndex);
            byte[] forumAvatar = cursor.getBlob(forumAvatarIndex);
            int forumRating = cursor.getInt(forumRatingIndex);
            String forumDate = cursor.getString(forumDateIndex);
            String forumTitle = cursor.getString(forumTitleIndex);
            String forumContent = cursor.getString(forumContentIndex);
            byte[] forumImage = cursor.getBlob(forumImageIndex);
            String forumCommentName1 = cursor.getString(forumCommentName1Index);
            String forumCommentContent1 = cursor.getString(forumCommentContent1Index);
            String forumCommentName2 = cursor.getString(forumCommentName2Index);
            String forumCommentContent2 = cursor.getString(forumCommentContent2Index);

            binding.txtUserName.setText(forumName);
            Bitmap bitmapAvatar = BitmapFactory.decodeByteArray(forumAvatar, 0, forumAvatar.length);
            binding.imvAvatar.setImageBitmap(bitmapAvatar);
            binding.txtDate.setText(forumDate);
            binding.txtRating.setText(String.valueOf(forumRating));
            binding.txtReviewTitle.setText(forumTitle);
            binding.txtContent.setText(forumContent);
            Bitmap bitmapContentImg = BitmapFactory.decodeByteArray(forumImage, 0, forumImage.length);
            binding.imvContentImg.setImageBitmap(bitmapContentImg);
            binding.txtCommentName1.setText(forumCommentName1);
            binding.txtCommentContent1.setText(forumCommentContent1);
            binding.txtCommentName2.setText(forumCommentName2);
            binding.txtCommentContent2.setText(forumCommentContent2);
        } while (cursor.moveToNext());
        cursor.close();
    }

    private void addEvents() {
        binding.btnclickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum_Detailed.this, ForumFragment.class);
                startActivity(intent);
            }
        });
    }

}