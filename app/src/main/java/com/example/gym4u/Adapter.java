package com.example.gym4u;

import android.app.DownloadManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class Adapter extends
        RecyclerView.Adapter<Adapter.ViewHolder> {

    private final RequestManager glide;

    List<Postdata> list;

    Adapter(List<Postdata> newList, RequestManager glide){
        this.list = newList;
        this.glide = glide;
    }


    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.listviewscreen, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Postdata obj = list.get(position);
        String safeName = obj.getDate() + obj.getTime();
        Log.d("TAG", "safename : " + safeName);
        try {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("PostImages").child(safeName+".jpg");
            if(storageReference != null){
            glide.load(storageReference).into(viewHolder.imgOfPost);
            Log.d("TAG", "Picture Exists");}
            else{
                Log.d("TAG", "No Picture exists for this");
            }
        }
        catch (Exception e){
            Log.d("TAG", "No Picture for " + safeName);
        }

        // Set item views based on your views and data model
        viewHolder.name.setText(obj.getName());
        viewHolder.post.setText(obj.getPost());
        viewHolder.date.setText(obj.getDate());
        viewHolder.time.setText(obj.getTime());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public  TextView name;
        public TextView post;
        public TextView date;
        public TextView time;
        public ImageView imgOfPost;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            post = (TextView) itemView.findViewById(R.id.textPost);
            date = itemView.findViewById(R.id.textDate);
            time = (TextView) itemView.findViewById(R.id.textTime);
            imgOfPost = itemView.findViewById(R.id.PostImg);
        }
    }

}