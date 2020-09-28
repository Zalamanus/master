package com.example.movedatatonewactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent incomeIntent = getIntent();
        TextView textView = findViewById(R.id.textView4);
        int number = Integer.parseInt(incomeIntent.getStringExtra("someNumber"));
        
        if (number < 100) textView.setText("Your number is small");
        else if (number > 100) textView.setText("Your number is big");
        else textView.setText("You are great!");
    }
}
