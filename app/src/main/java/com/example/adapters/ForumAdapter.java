package com.example.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Forum;
import com.example.skinlab.ForumFragment;
import com.example.skinlab.R;

import java.util.List;

public class ForumAdapter extends BaseAdapter {
    Context context;

    int list_item_forum;
    List<Forum> forums;

    //Constructor
    public ForumAdapter(Context context, int list_item_forum, List<Forum> forums) {
        this.context = context;
        this.list_item_forum = list_item_forum;
        this.forums = forums;
    }



    @Override
    public int getCount() {
        return forums.size();
    }

    @Override
    public Object getItem(int position) {
        return forums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(list_item_forum, null);
            holder.imvAvatar = view.findViewById(R.id.imvAvatar);
            holder.txtUserName = view.findViewById(R.id.txtUserName);
            holder.txtDate = view.findViewById(R.id.txtDate);
            holder.txtRating = view.findViewById(R.id.txtRating);
            holder.txtReviewTitle = view.findViewById(R.id.txtReviewTitle);
            holder.txtReviewContent = view.findViewById(R.id.txtReviewContent);
//            holder.imvContentImg = view.findViewById(R.id.imvContentImg);
//            holder.txtReviewContent = view.findViewById(R.id.txtReviewContent);
//            holder.txtCommentName1 = view.findViewById(R.id.txtCommentName1);
//            holder.txtCommentContent1 = view.findViewById(R.id.txtCommentContent1);
//            holder.txtCommentName2 = view.findViewById(R.id.txtCommentName2);
//            holder.txtCommentContent2 = view.findViewById(R.id.txtCommentContent2);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }


        //Binding data
//        Forum f = forums.get(position);
//
//        holder.txtUserName.setText(f.getFr_username());
//        holder.txtDate.setText(f.getFr_date());
//        holder.txtRating.setText(String.valueOf(f.getFr_rating()));
//        holder.txtReviewTitle.setText(f.getFr_reviewtitle());
//        holder.txtReviewContent.setText(f.getFr_reviewcontent());
//        byte[] data_avatar = f.getFr_avatar();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(data_avatar, 0, data_avatar.length);
//        holder.imvAvatar.setImageBitmap(bitmap);
//
//        byte[] data_contentimg = f.getFr_contentimg();
//        Bitmap data_contentimg = BitmapFactory.decodeByteArray(data_contentimg, 0, data_contentimg.length);
//        holder.imvAvatar.setImageBitmap(bitmap);

        return view;
    }


    public static class ViewHolder{
        ImageView imvAvatar, imvContentImg;
        TextView txtUserName, txtDate, txtRating, txtReviewTitle, txtReviewContent, txtContent, txtCommentName1, txtCommentContent1, txtCommentName2, txtCommentContent2;
    }
}
