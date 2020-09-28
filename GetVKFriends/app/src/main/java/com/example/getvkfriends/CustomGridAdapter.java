package com.example.getvkfriends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGridAdapter extends BaseAdapter {

    ArrayList<String> arrayPhotos;
    Context context;

    public CustomGridAdapter(@NonNull Context context, ArrayList<String> arrayPhotos) {
        this.arrayPhotos = arrayPhotos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayPhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String photoUrl = arrayPhotos.get(position);
        ImageView imageView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.image_cell, parent, false);
            imageView = convertView.findViewById(R.id.imageCell);

            Picasso.get().load(photoUrl).into(imageView);

        }
        return convertView;
    }
}
