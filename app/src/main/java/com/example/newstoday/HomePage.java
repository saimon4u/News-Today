package com.example.newstoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePage extends AppCompatActivity {
    private RecyclerView BangladeshNewsRV, HotTopicsRV, LatestNewsRV;
    private HomepageBangladeshNewsAdapter bangladeshNewsAdapter;
    private HotTopicsAdapter hotTopicsAdapter;
    private LatestNewsAdapter latestNewsAdapter;
    private CircleImageView recentBdNews;
    private ArrayList<NewsModel> latestNews;
    private ArrayList<NewsModel> bangladeshTodayList;
    private final String bangladeshAPIURL = "https://newsdata.io/api/1/news?apikey=pub_36371f8e138bd591a34730c8e1233162ad978&country=bd&language=en&prioritydomain=medium";

    private String latestNewsAPIURL = "https://newsdata.io/api/1/news?apikey=pub_36371f8e138bd591a34730c8e1233162ad978&category=sports,crime,business,politics,food&language=en&prioritydomain=medium";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BangladeshNewsRV = findViewById(R.id.BangladeshNewsRV);
        HotTopicsRV = findViewById(R.id.HotTopicsRV);
        LatestNewsRV = findViewById(R.id.LatestNewsRV);
        latestNews = new ArrayList<>();
        bangladeshTodayList = new ArrayList<>();
        bangladeshNewsAdapter = new HomepageBangladeshNewsAdapter(this, bangladeshTodayList);
        bangladeshNewsAdapter.setOnClick(new HomepageBangladeshNewsAdapter.onClickListener() {
            @Override
            public void onItemClick(String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


//        hotTopicsAdapter = new HotTopicsAdapter(this, list);
        latestNewsAdapter = new LatestNewsAdapter(this, latestNews);
        BangladeshNewsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        HotTopicsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        LatestNewsRV.setLayoutManager(new LinearLayoutManager(this));
        BangladeshNewsRV.setAdapter(bangladeshNewsAdapter);
//        HotTopicsRV.setAdapter(hotTopicsAdapter);
        LatestNewsRV.setAdapter(latestNewsAdapter);



        recentBdNews = findViewById(R.id.recentBdNews);
        recentBdNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, BangladeshNewsList.class);
                startActivity(intent);
            }
        });


        getBangladeshNews();
        getLatestNews();
    }

    private void getBangladeshNews(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, bangladeshAPIURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    JSONArray result = mainObj.getJSONArray("results");
                    for(int i=0; i<result.length() && i < 6; i++){
                        JSONObject eachObj = (JSONObject) result.get(i);
                        String title = eachObj.getString("title");
                        Log.d("TAG", "onResponse: " + title);
                        String imgLink = eachObj.getString("image_url");
                        String link = eachObj.getString("link");
                        String category = "";
                        JSONArray arr = eachObj.getJSONArray("category");
                        if (arr.length() > 0) category = arr.get(0).toString();
                        NewsModel model = new NewsModel(title, imgLink, link, category);
                        bangladeshTodayList.add(model);
                    }
                    bangladeshNewsAdapter.notifyDataSetChanged();
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

    private void getLatestNews(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, latestNewsAPIURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    JSONArray result = mainObj.getJSONArray("results");
                    for(int i=0; i<result.length() && i < 20; i++){
                        JSONObject eachObj = (JSONObject) result.get(i);
                        String title = eachObj.getString("title");
                        Log.d("TAG", "onResponse: " + title);
                        String imgLink = eachObj.getString("image_url");
                        String link = eachObj.getString("link");
                        String category = "";
                        JSONArray arr = eachObj.getJSONArray("category");
                        if (arr.length() > 0) category = arr.get(0).toString();
                        NewsModel model = new NewsModel(title, imgLink, link, category);
                        latestNews.add(model);
                    }
                    latestNewsAdapter.notifyDataSetChanged();
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