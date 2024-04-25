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


import com.example.models.Product;
import com.example.skinlab.Donhang_dathang;
import com.example.skinlab.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class GioHangAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private int mResource;
    Product item;
    private ArrayList<Product> gioHangItemList;
    int totalPrice;
    private int newQuantity = 0;
    public GioHangAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        gioHangItemList = objects; // Gán danh sách sản phẩm
    }


    public interface TotalPriceListener {
        void onTotalPriceChanged(int totalPrice);
    }


    private TotalPriceListener totalPriceListener;
    public void setTotalPriceListener(TotalPriceListener listener) {
        this.totalPriceListener = listener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;


        if (listItemView == null) {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(mContext);
            listItemView = inflater.inflate(mResource, parent, false);
        }


        // Lấy ra item tại vị trí position
        Product item = getItem(position);


        // Ánh xạ các view trong layout giohang_item.xml
        ImageView imgProduct = listItemView.findViewById(R.id.imgProduct);
        TextView txtProductName;
        txtProductName = listItemView.findViewById(R.id.txtProductname);
        TextView txtProductQuantity = listItemView.findViewById(R.id.txtProductquantity);
        EditText edtProductQuantity = listItemView.findViewById(R.id.edtProductQuantity);
        TextView txtProductPrice = listItemView.findViewById(R.id.txtProductprice);
        TextView txtProductquantity1 = listItemView.findViewById(R.id.txtProductquantity1);

        // Đặt giá trị cho các view
        Picasso.get().load(item.getPd_photo()).into(imgProduct);
        txtProductName.setText(item.getPd_name());
        //TextView txtProductQuantity = listItemView.findViewById(R.id.txtProductquantity);
        if (txtProductQuantity != null) {
            txtProductQuantity.setText("Số lượng:");
        }
        if (txtProductquantity1 != null) {
            txtProductquantity1.setText("x" + item.getQuantity());
            Log.d("Test adapter", "item quantity: " + item.getQuantity());
        }
        txtProductPrice.setText(String.valueOf(item.getPd_price()));


        // Đặt giá trị cho các view
        if (edtProductQuantity != null) {
            edtProductQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Không cần xử lý trước khi thay đổi
                }


                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Không cần xử lý khi đang thay đổi
                }


                @Override
                public void afterTextChanged(Editable s) {
                    // Xử lý sau khi dữ liệu đã thay đổi
                    try {
                        newQuantity = Integer.parseInt(s.toString());
                        item.setQuantity(newQuantity); // Cập nhật số lượng cho sản phẩm
                        Log.d("test after", "quantity after: " + item.getQuantity());


                        // Update SharedPreferences
                        updateSharedPreferences(item);


                        notifyDataSetChanged(); // Cập nhật ListView
                        Log.d("Adapter", "item quantity: " + newQuantity);
                        // Cập nhật lại số lượng hiển thị
                        //txtProductquantity1.setText("x" + newQuantity); // Cập nhật lại số lượng hiển thị
                        calculateTotalPrice(); // Tính lại tổng giá trị đơn hàng
                        Log.d("Adapter", "Total quantity: " + String.valueOf(totalPrice));
                        Log.d("test lưu giỏ hàng", "item: " + gioHangItemList);
                    } catch (NumberFormatException e) {
                        // Xử lý ngoại lệ nếu người dùng nhập không phải là số
                        Log.e("GioHangAdapter", "NumberFormatException: " + e.getMessage());
                    }
                }
            });


            // Trả về listItemView đã được thiết lập dữ liệu
            //return listItemView;
        }
        // Trả về listItemView đã được thiết lập dữ liệu
        return listItemView;
    };


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


    // Phương thức này sẽ cập nhật số lượng của sản phẩm vào SharedPreferences
//    private void updateQuantityInSharedPreferences(Product product) {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(product.getPd_id(), product.getQuantity());// Lưu số lượng của sản phẩm với key là pd_id
//        editor.apply();
//    }
//
//    public int getNewQuantity() {
//        return newQuantity;
//    }


    // Cập nhật giá trị newQuantity khi dữ liệu thay đổi
    private void updateNewQuantity(int quantity) {
        newQuantity = quantity;
    }




    private void calculateTotalPrice() {
        int totalPrice = 0;
        for (Product product : gioHangItemList) {
            totalPrice += product.getPd_price() * product.getQuantity();
        }
        if (totalPriceListener != null) {
            totalPriceListener.onTotalPriceChanged(totalPrice);
        }
    }
}
