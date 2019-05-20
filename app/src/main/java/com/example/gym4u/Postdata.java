package com.example.gym4u;

import android.graphics.Bitmap;

class Postdata {
    private String name;
    //private Bitmap img;
    private String img;
    //private int id;
    private  String date;
    private String time;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    private String post;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
/*
    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    */
}
