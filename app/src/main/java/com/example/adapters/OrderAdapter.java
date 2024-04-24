package com.example.adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.models.Order;
import com.example.models.Product;
import com.example.skinlab.Donhang_dathang;
import com.example.skinlab.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class OrderAdapter extends ArrayAdapter<Order> {
    private Context mContext;
    private int mResource;
    Product item;
    private ArrayList<Product> gioHangItemList;
    int totalPrice;
    private int newQuantity = 0;
    public OrderAdapter(Context context, int resource, ArrayList<Order> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            listItemView = inflater.inflate(mResource, parent, false);
        }

        // Lấy đơn hàng tại vị trí position
        Order currentOrder = getItem(position);

        // Ánh xạ dữ liệu vào các TextView trong layout donhang_listbelowcategory
        TextView txtStatus = listItemView.findViewById(R.id.txtStatus);
        TextView txtProductname = listItemView.findViewById(R.id.txtProductname);
        TextView txtProductprice = listItemView.findViewById(R.id.txtProductprice);

        txtStatus.setText(currentOrder.getStatus());
        txtProductname.setText(currentOrder.getProduct_order2());
        txtProductprice.setText(String.valueOf(currentOrder.getQuantity_product2()));

        return listItemView;
    }


    // Add a method to update SharedPreferences
    private void updateSharedPreferences(Product product) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        // Convert product to a String using a suitable format
        String productString = product.getPd_id() + "," + product.getPd_name() + "," +
                product.getPd_price() + "," + product.getPd_price2() + "," +
                product.getPd_cate() + "," + product.getPd_brand() + "," +
                product.getPd_photo() + "," + product.getPd_des() + "," +
                product.getQuantity(); // Add quantity to the format


        // Get the existing set of product strings
        Set<String> existingProducts = sharedPreferences.getStringSet("cart_items", new HashSet<String>());


        // Update the set (add or remove based on changes)
        if (product.getQuantity() > 0) {
            existingProducts.add(productString);
        } else {
            existingProducts.remove(productString);
        }


        editor.putStringSet("cart_items", existingProducts);
        editor.apply();
    }
}
