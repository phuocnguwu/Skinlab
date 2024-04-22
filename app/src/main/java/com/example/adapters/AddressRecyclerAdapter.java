//package com.example.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.models.Address;
//import com.example.skinlab.Myaccount_Diachi;
//import com.example.skinlab.Myaccount_Editdiachi;
//import com.example.skinlab.R;
//
//import java.util.List;
//
//public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {
//    private Context context;
//    private List<Address> addressList;
//
//    public AddressRecyclerAdapter(Context context, List<Address> addressList) {
//        this.context = context;
//        this.addressList = addressList;
//    }
//
//    @NonNull
//    @Override
//    public AddressRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diachi_layout, parent, false);
//        return new ViewHolder(view);    }
//
//    @Override
//    public void onBindViewHolder( AddressRecyclerAdapter.ViewHolder holder, int position) {
//
//        Address address = addressList.get(position);
//        holder.txtName.setText(address.getName());
//        holder.txtphone.setText(address.getPhone());
//        holder.txtaddress.setText(address.getAddress());
//        holder.imbtnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Myaccount_Editdiachi.class);
//                intent.putExtra("selectedAddress", address);
//                context.startActivity(intent);
//            }
//        });
//        holder.imbtndelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Xử lý sự kiện xóa
//            }
//        });
//    }
//    @Override
//    public int getItemCount() {
//        return addressList.size();
//    }
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView  txtName, txtphone, txtaddress;
//        ImageButton imbtnEdit, imbtndelete;
//        public ViewHolder( View itemView) {
//            super(itemView);
//            txtName = itemView.findViewById(R.id.txtUserName);
//            txtphone = itemView.findViewById(R.id.txtSdt);
//            txtaddress = itemView.findViewById(R.id.txtDiachicuthe);
//            imbtnEdit = itemView.findViewById(R.id.btnedit);
//            imbtndelete = itemView.findViewById(R.id.btndelete);
//
//
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.txtName.setText(address.getName());
        holder.txtphone.setText(address.getPhone());
        holder.txtaddress.setText(address.getAddress());
        holder.imbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAddress(address);
            }
        });
    }

    private void deleteAddress(Address address) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        if (address.getName().equals(addressList.get(0).getName())) {
            // Nếu là address1, cập nhật cột user_address thành null
            db.execSQL("UPDATE " + DatabaseHelper.USER + " SET " + DatabaseHelper.COLUMN_USER_ADDRESS + " = NULL WHERE " +
                    DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{getLoggedInPhone()});
        } else {
            // Nếu là address2, cập nhật các cột user_name2foraddress2, user_phone2foraddress2, user_address2 thành null
            db.execSQL("UPDATE " + DatabaseHelper.USER + " SET " + DatabaseHelper.COLUMN_USER_NAME2FORADDRESS2 + " = NULL, " +
                    DatabaseHelper.COLUMN_USER_PHONE2FORADDRESS2 + " = NULL, " + DatabaseHelper.COLUMN_USER_ADDRESS2 + " = NULL WHERE " +
                    DatabaseHelper.COLUMN_USER_PHONE + " = ?", new String[]{getLoggedInPhone()});
        }
        db.close();

        // Load lại danh sách địa chỉ sau khi xóa
        ((Myaccount_Diachi) context).loadUserAddresses();
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
