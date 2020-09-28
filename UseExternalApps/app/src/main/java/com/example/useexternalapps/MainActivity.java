package com.example.useexternalapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber = findViewById(R.id.editPhoneNumber);
    }

    public void makeCall(View view) {
        Intent intentMakeCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.getText().toString()));
        startActivity(intentMakeCall);
    }

    public void goOnAboutPage(View view) {
        Intent intentAboutPage = new Intent(this, AboutActivity.class);
        startActivity(intentAboutPage);
    }
}
