package com.example.newstoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.viewholder> {
    private Context context;
    private ArrayList<NewsModel> news;

    public AllNewsAdapter(Context context, ArrayList<NewsModel> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public AllNewsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.latest_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNewsAdapter.viewholder holder, int position) {
        NewsModel model = news.get(position);
        holder.title.setText(model.title);
        holder.tag.setText(model.category);
        Picasso.get().load(model.imgLink).resize(80,80).into(holder.img);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (clickListener != null) {
//                    String url = model.link;
//                    clickListener.onItemClick(url);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return news.size();
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
