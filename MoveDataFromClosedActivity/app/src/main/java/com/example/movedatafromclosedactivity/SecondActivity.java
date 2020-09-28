package com.example.movedatafromclosedactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    EditText name;
    EditText surname;
    EditText phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intentToMainActivity = new Intent();
        intentToMainActivity.putExtra("key", "Confirm the data!");
        setResult(RESULT_CANCELED, intentToMainActivity);

        name = findViewById(R.id.editName);
        surname = findViewById(R.id.editSurname);
        phonenumber = findViewById(R.id.editPhone);
    }

    public void onConfirm(View view) {
        Intent intentToMainActivity = new Intent();
        intentToMainActivity.putExtra("name", name.getText().toString());
        intentToMainActivity.putExtra("surname", surname.getText().toString());
        intentToMainActivity.putExtra("phonenumber", phonenumber.getText().toString());
        setResult(RESULT_OK,intentToMainActivity);
        this.finish();
    }
}
