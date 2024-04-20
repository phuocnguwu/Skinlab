package com.example.skinlab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;

public class ForumDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "Skinlab.db";
    public static final int DB_VERSION = 1;

    public static final String FORUM = "forum";
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






    public ForumDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FORUM);
        onCreate(db);
    }

    //THUC HIEN SELECT
    public Cursor queryData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }



    private byte[] convertPhoto(Context context, int resourcedId) {
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(resourcedId);
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }


}
