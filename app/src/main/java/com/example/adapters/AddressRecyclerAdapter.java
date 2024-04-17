package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Address;
import com.example.skinlab.R;

import java.util.List;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Address> addressList;

    public AddressRecyclerAdapter(Context context, List<Address> addressList) {
        this.context = context;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public AddressRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diachi_layout, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder( AddressRecyclerAdapter.ViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.txtName.setText(address.getName());
        holder.txtphone.setText(address.getPhone());
        holder.txtaddress.setText(address.getAddress());

    }
    @Override
    public int getItemCount() {
        return addressList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  txtName, txtphone, txtaddress;
        public ViewHolder( View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtUserName);
            txtphone = itemView.findViewById(R.id.txtSdt);
            txtaddress = itemView.findViewById(R.id.txtDiachicuthe);
        }
    }
}
