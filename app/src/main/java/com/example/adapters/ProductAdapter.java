package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Product;
import com.example.skinlab.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>  {
    Context context;
    ArrayList<Product> products;

    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }


    public ProductAdapter(Context context, ArrayList<Product> products) {
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

        final Product product = products.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener !=null) listener.onItemClick(product);
            }
        });
        holder.bind(product);
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

        public void bind(Product product) {
            // Load hình ảnh từ đường link được lưu trong cột pd_Photo bằng Picasso
            if (product.getPd_photo() != null && !product.getPd_photo().isEmpty()) {
                Picasso.get()
                        .load(product.getPd_photo())
                        .into(imvProduct);
            }
            txtProductName.setText(product.getPd_name());

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.'); // Dấu chấm làm dấu phân tách hàng nghìn

            DecimalFormat decimalFormat = new DecimalFormat("#,##0", symbols);
            decimalFormat.setGroupingSize(3); // Định dạng hàng nghìn theo từng nhóm 3 chữ số

            String formattedPrice = decimalFormat.format(product.getPd_price());

            txtProductName.setText(product.getPd_name());
            txtProductPrice.setText(formattedPrice + " đ");
        }
    }

    public interface OnClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}