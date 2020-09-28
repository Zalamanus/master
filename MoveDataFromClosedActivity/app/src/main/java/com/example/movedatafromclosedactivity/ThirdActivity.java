package com.example.movedatafromclosedactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ThirdActivity extends AppCompatActivity {
    EditText groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        groupName = findViewById(R.id.editGroup);
    }

    public void onOK(View view) {
        Intent intent = new Intent();
        intent.putExtra("groupname", groupName.getText().toString());
        setResult(RESULT_OK,intent);
        this.finish();
    }
}
