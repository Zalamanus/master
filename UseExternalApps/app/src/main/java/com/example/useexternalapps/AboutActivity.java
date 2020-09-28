package com.example.useexternalapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void goOnAuthorSite(View view) {
        Intent intentToAuthorSite = new Intent(Intent.ACTION_VIEW, Uri.parse("http://usa.gov"));
        startActivity(intentToAuthorSite);
    }
}
