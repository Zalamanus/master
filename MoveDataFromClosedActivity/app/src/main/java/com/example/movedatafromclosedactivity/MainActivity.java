package com.example.movedatafromclosedactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int CODEFORSECONDACTIVITY = 1;
    private static final int CODEFORTHIRDACTIVITY = 2;
    TextView nameLabel;
    TextView surnameLabel;
    TextView phoneNumberLabel;
    TextView groupLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameLabel = findViewById(R.id.NameLabel);
        surnameLabel = findViewById(R.id.SurnameLabel);
        phoneNumberLabel = findViewById(R.id.PhoneNumberLabel);
        groupLabel = findViewById(R.id.Group);
    }

    public void onAddClick(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, CODEFORSECONDACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODEFORSECONDACTIVITY) {
            if (resultCode == RESULT_CANCELED) {
                nameLabel.setText(null);
                surnameLabel.setText(null);
                phoneNumberLabel.setText(data.getStringExtra("key"));
                phoneNumberLabel.setTextColor(Color.RED);
            } else if (resultCode == RESULT_OK) {
                nameLabel.setText(data.getStringExtra("name"));
                nameLabel.setTextColor(Color.BLUE);
                surnameLabel.setText(data.getStringExtra("surname"));
                surnameLabel.setTextColor(Color.BLUE);
                phoneNumberLabel.setText(data.getStringExtra("phonenumber"));
                phoneNumberLabel.setTextColor(Color.BLUE);
            }
        }
        if (requestCode == CODEFORTHIRDACTIVITY) {
            if (resultCode == RESULT_OK) {
                groupLabel.setText(data.getStringExtra("groupname"));
            }
        }
    }

    public void onAddGroup(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivityForResult(intent, CODEFORTHIRDACTIVITY);
    }
}
