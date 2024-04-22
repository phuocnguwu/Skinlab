//
//package com.example.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.models.Address;
//import com.example.skinlab.DatabaseHelper;
//import com.example.skinlab.Myaccount_Diachi;
//import com.example.skinlab.R;
//
//import java.util.List;
//
//public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {
//    private Context context;
//    private List<Address> addressList;
//    private DatabaseHelper databaseHelper;
//
//    public AddressRecyclerAdapter(Context context, List<Address> addressList, DatabaseHelper databaseHelper) {
//        this.context = context;
//        this.addressList = addressList;
//        this.databaseHelper = databaseHelper;
//    }
//
//    @NonNull
//    @Override
//    public AddressRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diachi_layout, parent, false);
//        return new ViewHolder(view);
//    }
//
//
//private void deleteAddress(ViewHolder holder) {
//    int position = holder.getAdapterPosition();
//    if (position != RecyclerView.NO_POSITION) {
//        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//        String loggedInPhone = getLoggedInPhone();
//        if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
//            Address address = addressList.get(position);
//            if (position == 0) {
//                // Xóa address1 và cập nhật user_address thành null
//                databaseHelper.updateAddressFields(loggedInPhone, null, null, null, 1);
//            } else {
//                // Xóa address2 và cập nhật user_name2foraddress2, user_phone2foraddress2, và user_address2 thành null
//                databaseHelper.updateAddressFields(loggedInPhone, null, null, null, 2);
//            }
//            addressList.remove(position); // Xóa địa chỉ khỏi danh sách
//            notifyItemRemoved(position); // Cập nhật RecyclerView
//            db.close();
//        } else {
//            Toast.makeText(context, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Address address = addressList.get(position);
//        holder.txtName.setText(address.getName());
//        holder.txtphone.setText(address.getPhone());
//        holder.txtaddress.setText(address.getAddress());
//        holder.imbtnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteAddress(holder); // Pass the ViewHolder to retrieve the position later
//            }
//        });
//    }
//
//
//    private String getLoggedInPhone() {
//        return ((Myaccount_Diachi) context).getLoggedInPhone();
//    }
//
//    @Override
//    public int getItemCount() {
//        return addressList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView txtName, txtphone, txtaddress;
//        ImageButton imbtnDelete;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            txtName = itemView.findViewById(R.id.txtUserName);
//            txtphone = itemView.findViewById(R.id.txtSdt);
//            txtaddress = itemView.findViewById(R.id.txtDiachicuthe);
//            imbtnDelete = itemView.findViewById(R.id.btndelete);
//        }
//    }
//}

package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Address;
import com.example.skinlab.DatabaseHelper;
import com.example.skinlab.Myaccount_Diachi;
import com.example.skinlab.R;

import java.util.List;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Address> addressList;
    private DatabaseHelper databaseHelper;

    public AddressRecyclerAdapter(Context context, List<Address> addressList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.addressList = addressList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public AddressRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diachi_layout, parent, false);
        return new ViewHolder(view);
    }


    private void deleteAddress(ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            String loggedInPhone = getLoggedInPhone();
            if (loggedInPhone != null && !loggedInPhone.isEmpty()) {
                Address address = addressList.get(position);
                if (position == 0) {
                    // Xóa address1 và cập nhật user_address thành null
                    databaseHelper.deleteAddress(loggedInPhone, 1);
                    databaseHelper.updateAddressFields(loggedInPhone, null, null, null, 1);
                } else {
                    // Xóa address2 và cập nhật user_name2foraddress2, user_phone2foraddress2, và user_address2 thành null
                    databaseHelper.deleteAddress(loggedInPhone, 2);
                    databaseHelper.updateAddressFields(loggedInPhone, null, null, null, 2);
                }
                addressList.remove(position); // Xóa địa chỉ khỏi danh sách
                notifyItemRemoved(position); // Cập nhật RecyclerView
                db.close();
            } else {
                Toast.makeText(context, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.txtName.setText(address.getName());
        holder.txtphone.setText(address.getPhone());
        holder.txtaddress.setText(address.getAddress());
        holder.imbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAddress(holder); // Pass the ViewHolder to retrieve the position later
            }
        });
    }


    private String getLoggedInPhone() {
        return ((Myaccount_Diachi) context).getLoggedInPhone();
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtphone, txtaddress;
        ImageButton imbtnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtUserName);
            txtphone = itemView.findViewById(R.id.txtSdt);
            txtaddress = itemView.findViewById(R.id.txtDiachicuthe);
            imbtnDelete = itemView.findViewById(R.id.btndelete);
        }
    }
}
