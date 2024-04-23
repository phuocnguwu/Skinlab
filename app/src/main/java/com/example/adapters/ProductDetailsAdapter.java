package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skinlab.Product_Details;
import com.example.skinlab.R;
import com.example.models.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

//                holder.imvProduct.setImageResource(products.get(position).getPd_photo());
        Picasso.get().load(products.get(position).getPd_photo()).into(holder.imvProduct);
        holder.txtProductBrand.setText(products.get(position).getPd_brand());
        holder.txtProductName.setText(products.get(position).getPd_name());
        holder.txtProductDes.setText(products.get(position).getPd_des());
        holder.txtId.setText(products.get(position).getPd_id());

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.'); // Dấu chấm làm dấu phân tách hàng nghìn

        DecimalFormat decimalFormat = new DecimalFormat("#,##0", symbols);
        decimalFormat.setGroupingSize(3); // Định dạng hàng nghìn theo từng nhóm 3 chữ số

        String formattedPrice = decimalFormat.format(products.get(position).getPd_price());
        String formattedPrice2 = decimalFormat.format(products.get(position).getPd_price2());
        holder.txtProductPrice.setText(formattedPrice + " đ");
        holder.txtProductPrice2.setText(formattedPrice2 + " đ");


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
        TextView txtProductName, txtProductPrice, txtProductBrand, txtProductPrice2, txtProductDes, txtProductFeedback, txtId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
//            txtId = itemView.findViewById(R.id.txtId);
        }
    }
}
