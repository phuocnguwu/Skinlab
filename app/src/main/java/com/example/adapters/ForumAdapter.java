package com.example.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.models.Feedback;
import com.example.models.Forum;

import java.util.List;

public class ForumAdapter extends BaseAdapter {
    Activity context;

    int list_item_forum;
    List<Forum> forums;

    //Constructor


    public ForumAdapter(Activity context, int list_item_forum, List<Forum> forums) {
        this.context = context;
        this.list_item_forum = list_item_forum;
        this.forums = forums;
    }



    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
