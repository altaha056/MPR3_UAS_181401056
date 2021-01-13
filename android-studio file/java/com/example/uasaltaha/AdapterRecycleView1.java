package com.example.uasaltaha;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecycleView1 extends RecyclerView.Adapter<AdapterRecycleView1.Holder> {

    List<ModelData>data=new ArrayList<>();

    public AdapterRecycleView1(List<ModelData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ModelData modelData=data.get(position);
        holder.title.setText(modelData.getTitle());
        holder.description.setText(modelData.getDescription());
        Picasso.get().load(modelData.getImage()).into(holder.bgcard);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView title, description;
        ImageView bgcard;
        public Holder(@NonNull View itemView) {

            super(itemView);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            bgcard=itemView.findViewById(R.id.bgcard);

        }
    }
}
