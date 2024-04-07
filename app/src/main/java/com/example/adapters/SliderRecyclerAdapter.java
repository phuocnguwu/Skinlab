package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Slider;
import com.example.skinlab.R;

import java.util.ArrayList;

public class SliderRecyclerAdapter extends RecyclerView.Adapter<SliderRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<Slider> sliders;

    public SliderRecyclerAdapter(Context context, ArrayList<Slider> sliders) {
        this.context = context;
        this.sliders = sliders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_slider,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imvSlider.setImageResource(sliders.get(position).getSliderThumb());

    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imvSlider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvSlider = itemView.findViewById(R.id.imvSlider);
            }

    }

}
