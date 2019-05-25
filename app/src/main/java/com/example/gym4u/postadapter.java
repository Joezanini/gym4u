package com.example.gym4u;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class postadapter extends ArrayAdapter<Postdata> {
    private Context mContext;
    int mResource;
    public postadapter(Context context, int resource, ArrayList<Postdata> objects){
        super(context, resource,objects);
        mContext = context;
        mResource = resource;
    }
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent){
        String title = getItem(position).getName();
        //Bitmap img = getItem(position).getImg();
        String post = getItem(position).getPost();
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();


        //PicData info = new PicData();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txt = convertView.findViewById(R.id.textName);
       // ImageView pic = convertView.findViewById(R.id.img);
        TextView postDesc = convertView.findViewById(R.id.textPost);
        TextView dateDesc = convertView.findViewById(R.id.textDate);
        TextView timeDesc = convertView.findViewById(R.id.textTime);

        String log =  " Name: " + title;

        Log.d("String", log);

        txt.setText(title);
       // pic.setImageBitmap(img);
        postDesc.setText(post);
        dateDesc.setText(date);
        timeDesc.setText(time);
        return convertView;

    }
}