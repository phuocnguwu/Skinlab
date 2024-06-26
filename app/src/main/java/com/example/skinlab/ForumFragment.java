package com.example.skinlab;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.adapters.ForumAdapter;
import com.example.adapters.ProductAdapter;
import com.example.adapters.SliderRecyclerAdapter;
import com.example.models.Forum;
import com.example.models.Product;
import com.example.skinlab.databinding.FragmentForumBinding;
import com.example.skinlab.databinding.FragmentMyAccountBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForumFragment newInstance(String param1, String param2) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentForumBinding binding;
    ForumAdapter adapter;
    ArrayList<Forum> forums;
    public static final String DB_NAME = "Skinlab.db";
    public static final String DB_FOLDER = "databases";

    Context context;

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
        binding = FragmentForumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvents();
//        loadDb();
    }

    @Override
    public void onResume() {
        loadDb();
        super.onResume();
    }

    private void loadDb() {
        File dbFile = requireContext().getDatabasePath(DB_NAME);
        db = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
        forums = new ArrayList<>();
        String[] projection = {
                COLUMN_FORUM_ID,
                COLUMN_FORUM_NAME,
                COLUMN_FORUM_AVATAR,
                COLUMN_FORUM_DATE,
                COLUMN_FORUM_RATING,
                COLUMN_FORUM_TITLE,
                COLUMN_FORUM_SHORTCONTENT
        };
        Cursor cursor = db.query(TBL_NAME, projection, null, null, null, null, null);
        while (cursor.moveToNext()){
            byte[] imageData = cursor.getBlob(2);
            forums.add(new Forum(cursor.getInt(0),
                    imageData,
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6)));
        }
        cursor.close();

        adapter = new ForumAdapter(requireActivity(), R.layout.forum_item_layout, forums);
        binding.lvReview.setAdapter(adapter);
    }

    private void deleteReviewFromDatabase(Forum selectedForum) {
        SQLiteDatabase db = requireContext().openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        String selection = COLUMN_FORUM_ID + " = ?";
        String[] selectionArgs = { String.valueOf(selectedForum.getFr_id()) };
        int rowsDeleted = db.delete(TBL_NAME, selection, selectionArgs);
        db.close();

        if (rowsDeleted > 0) {
            Log.d("DELETE", "Review deleted successfully");
        } else {
            Log.d("DELETE", "Failed to delete review");
        }
    }


    private void addEvents() {
        binding.btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Forum_AddReview_Main.class);
                startActivity(intent);
            }
        });
        binding.lvReview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Forum selectedForum = forums.get(position);

                deleteReviewFromDatabase(selectedForum);

                forums.remove(position);
                adapter.notifyDataSetChanged();

                Toast.makeText(getContext(), "Review deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        binding.lvReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Forum selectedForum = forums.get(position);
                Intent intent = new Intent(requireActivity(), Forum_Detailed.class);
                intent.putExtra("forum_id", selectedForum.getFr_id());
                startActivity(intent);
            }
        });
    }
}