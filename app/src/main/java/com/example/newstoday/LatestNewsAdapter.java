package com.example.newstoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.viewholder> {
    private Context context;
    private ArrayList<NewsModel> list;

    public LatestNewsAdapter(Context context, ArrayList<NewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LatestNewsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.latest_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestNewsAdapter.viewholder holder, int position) {
        NewsModel model = list.get(position);
        holder.title.setText(model.title);
        holder.tag.setText(model.category);
        Picasso.get().load(model.imgLink).resize(80,80).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewholder extends RecyclerView.ViewHolder {
        TextView title,tag;
        ImageView img;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.allNewsImg);
            tag = itemView.findViewById(R.id.allNewsTag);
            title = itemView.findViewById(R.id.allNewsTitle);
        }
    }
}
