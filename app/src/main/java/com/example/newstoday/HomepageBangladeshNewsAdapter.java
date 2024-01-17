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

public class HomepageBangladeshNewsAdapter extends RecyclerView.Adapter<HomepageBangladeshNewsAdapter.viewholder> {

    interface onClickListener{
        void onItemClick(String url);
    }
    private Context context;
    private ArrayList<NewsModel> list;
    private onClickListener clickListener;

    public HomepageBangladeshNewsAdapter(Context context, ArrayList<NewsModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnClick(onClickListener listener){
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public HomepageBangladeshNewsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bangladesh_news, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageBangladeshNewsAdapter.viewholder holder, int position) {
        NewsModel model = list.get(position);
        holder.text.setText(model.title);

        Picasso.get().load(model.imgLink).resize(150,80).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    String url = model.link;
                    clickListener.onItemClick(url);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView img;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.bdText);
            img = itemView.findViewById(R.id.bdImg);
        }
    }
}