package com.example.movetonewactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void toThirdActivity(View view) {
        Intent thirdActivityIntent = new Intent(this, ThirdActivity.class);
        startActivity(thirdActivityIntent);
    }
}
