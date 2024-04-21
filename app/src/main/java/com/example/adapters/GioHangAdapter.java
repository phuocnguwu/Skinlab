package com.example.adapters;

import android.content.Context;
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

    public GioHangAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
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

        // Trả về listItemView đã được thiết lập dữ liệu
        return listItemView;
    }
}
