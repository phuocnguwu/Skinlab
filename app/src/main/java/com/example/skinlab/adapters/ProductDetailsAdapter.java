package com.example.skinlab.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skinlab.Product_Details;
import com.example.skinlab.R;
import com.example.skinlab.models.Product;

import java.util.List;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder> {
    Context context;
    List<Product> products;

    public ProductDetailsAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_product_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imvProduct.setImageResource(products.get(position).getPd_photo());
        holder.txtProductBrand.setText(products.get(position).getPd_brand());
        holder.txtProductName.setText(products.get(position).getPd_name());
        holder.txtProductPrice.setText(products.get(position).getPd_price() + " đ");
        holder.txtProductPrice2.setText(products.get(position).getPd_price2() + " đ");
        holder.txtProductDes.setText(products.get(position).getPd_des() + " đ");

        // Khi click 1 item
        final int itemPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product selectedProduct = products.get(itemPosition);

                Intent intent = new Intent(context, Product_Details.class);
                intent.putExtra("selectedProduct", selectedProduct);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imvProduct;
        TextView txtProductName, txtProductPrice, txtProductBrand, txtProductPrice2, txtProductDes, txtProductFeedback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
        }
    }



}
