package com.example.adapters;

import android.content.Context;
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
import com.example.skinlab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GioHangAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private int mResource;
    Product item;
    private ArrayList<Product> gioHangItemList;
    private int totalPrice = 0;
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


        // Đặt giá trị cho các view
        Picasso.get().load(item.getPd_photo()).into(imgProduct);
        txtProductName.setText(item.getPd_name());
        txtProductQuantity.setText("Số lượng:");
        txtProductPrice.setText(String.valueOf(item.getPd_price()));

        // Đặt giá trị cho các view
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
                    int newQuantity = Integer.parseInt(s.toString());
                    item.setQuantity(newQuantity); // Cập nhật số lượng cho sản phẩm
                    notifyDataSetChanged(); // Cập nhật ListView
                    Log.d("Adapter", "item quantity: " + newQuantity );
                    calculateTotalPrice(); // Tính lại tổng giá trị đơn hàng
                    Log.d("Adapter", "Total quantity: " + String.valueOf(totalPrice));
                } catch (NumberFormatException e) {
                    // Xử lý ngoại lệ nếu người dùng nhập không phải là số
                    Log.e("GioHangAdapter", "NumberFormatException: " + e.getMessage());
                }
            }
        });

        // Trả về listItemView đã được thiết lập dữ liệu
        return listItemView;
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
