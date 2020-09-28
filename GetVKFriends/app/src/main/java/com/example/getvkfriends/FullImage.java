package com.example.getvkfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullImage extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        imageView = findViewById(R.id.imgFullImage);
        String imgUrl = getIntent().getStringExtra("imageUrl");
        Picasso.get().load(imgUrl).into(imageView);

    }
}
