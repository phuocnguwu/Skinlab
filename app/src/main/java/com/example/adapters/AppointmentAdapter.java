package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Appointment;
import com.example.skinlab.Aboutskin_lichhen;
import com.example.models.Appointment;
import com.example.skinlab.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private Context context;
    private List<Appointment> appointmentList;

    public AppointmentAdapter(Context context, List<Appointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lichsuhen_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.txtHoten.setText(appointment.getUserName());
        holder.txtSdt.setText(appointment.getUserPhone());
        holder.txtDiachi.setText(appointment.getUserAddress());
        holder.txtNgayhen.setText(appointment.getUserDate());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHoten, txtSdt, txtDiachi, txtNgayhen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHoten = itemView.findViewById(R.id.txtHoten);
            txtSdt = itemView.findViewById(R.id.txtSdt);
            txtDiachi = itemView.findViewById(R.id.txtDiachi);
            txtNgayhen = itemView.findViewById(R.id.txtNgayhen);
        }
    }
}
