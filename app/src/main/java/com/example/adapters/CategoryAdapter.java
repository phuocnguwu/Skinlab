package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.models.Category;
import com.example.skinlab.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donhang_category_selecteditem, parent, false);
        TextView txtSelecteditem = convertView.findViewById(R.id.txtcategory_selecteditem);

        Category category = this.getItem(position);
        if (category != null){
            txtSelecteditem.setText(category.getSelectedTag());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donhang_category_item, parent, false);
        TextView txtCategory = convertView.findViewById(R.id.txtcategory_item);

        Category category = this.getItem(position);
        if (category != null){
            txtCategory.setText(category.getSelectedTag());
        }
        return convertView;
    }
}
