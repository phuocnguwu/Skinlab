package com.example.skinlab.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skinlab.models.Product;
import com.example.skinlab.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>  {
    Context context;
    List<Product> products;


    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imvProduct.setImageResource(products.get(position).getPd_photo());
        holder.txtProductName.setText(products.get(position).getPd_name());
        holder.txtProductPrice.setText(products.get(position).getPd_price() + "đ");

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imvProduct;
        TextView txtProductName,txtProductPrice ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
        }
    }

}