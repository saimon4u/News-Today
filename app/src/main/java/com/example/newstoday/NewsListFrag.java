package com.example.newstoday;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsListFrag extends Fragment {

    private RecyclerView allRecentBdNewsRV;
    private ArrayList<NewsModel> news;
    private AllNewsAdapter adapter;

    private final String bangladeshAPIURL = "https://newsdata.io/api/1/news?apikey=pub_36371f8e138bd591a34730c8e1233162ad978&country=bd&language=en&prioritydomain=medium";

    public NewsListFrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_news_list, container, false);
        allRecentBdNewsRV = view.findViewById(R.id.allRecentBdNewsRV);
        news = new ArrayList<>();
        adapter = new AllNewsAdapter(container.getContext(), news);
        allRecentBdNewsRV.setLayoutManager(new LinearLayoutManager(container.getContext()));
        allRecentBdNewsRV.setAdapter(adapter);

        getBangladeshNews(container.getContext());
        return view;
    }

    private void getBangladeshNews(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, bangladeshAPIURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    JSONArray result = mainObj.getJSONArray("results");
                    for(int i=0; i<result.length() && i < 15; i++){
                        JSONObject eachObj = (JSONObject) result.get(i);
                        String title = eachObj.getString("title");
                        Log.d("TAG", "onResponse: " + title);
                        String imgLink = eachObj.getString("image_url");
                        String link = eachObj.getString("link");
                        String category = "";
                        JSONArray arr = eachObj.getJSONArray("category");
                        if (arr.length() > 0) category = arr.get(0).toString();
                        NewsModel model = new NewsModel(title, imgLink, link, category);
                        news.add(model);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d("TAG", "onResponse: ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error.toString());
            }
        });
        queue.add(stringRequest);
    }
}