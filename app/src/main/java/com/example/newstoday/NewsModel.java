package com.example.newstoday;

import androidx.annotation.NonNull;

public class NewsModel {
    public String title,imgLink,link,category;

    public NewsModel(String title, String imgLink, String link, String category) {
        this.title = title;
        this.imgLink = imgLink;
        this.link = link;
        this.category = category;
    }
    @NonNull
    @Override
    public String toString() {
        return title + "\n" + category;
    }
}
