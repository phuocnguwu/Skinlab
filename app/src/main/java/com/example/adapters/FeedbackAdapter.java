package com.example.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Feedback;
import com.example.models.Product;
import com.example.skinlab.R;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends BaseAdapter {
    Activity context;

    int list_item_feedback;
    List<Feedback> feedbacks;

    public FeedbackAdapter(Activity context, int list_item_feedback, List<Feedback> feedbacks) {
        this.context = context;
        this.list_item_feedback = list_item_feedback;
        this.feedbacks = feedbacks;
    }

    @Override
    public int getCount() {
        return feedbacks.size();
    }

    @Override
    public Object getItem(int position) {
        return feedbacks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(list_item_feedback, null);


            holder.imvUser = view.findViewById(R.id.imvUser);
            holder.txtUserName = view.findViewById(R.id.txtUserName);
            holder.txtDate = view.findViewById(R.id.txtDate);
            holder.txtRatings = view.findViewById(R.id.txtRatings);
            holder.txtFeedbackTitle = view.findViewById(R.id.txtFeedbackTitle);
            holder.txtFeedbackContent = view.findViewById(R.id.txtFeedbackContent);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //Lien ket du lieu

        Feedback f = feedbacks.get(i);
//        holder.imvUser.setImageResource(f.getUserThumb());
        holder.txtUserName.setText(f.getUserName());
        holder.txtDate.setText(f.getDate());
        holder.txtRatings.setText(String.valueOf( Math.round(f.getRatings())));
        holder.txtFeedbackTitle.setText(f.getFeedbackTitle());
        holder.txtFeedbackContent.setText(f.getFeedbackContent());

        return view;
    }

    public static class ViewHolder {
        ImageView imvUser;
        TextView txtUserName, txtDate, txtRatings,txtFeedbackTitle, txtFeedbackContent;
    }
}