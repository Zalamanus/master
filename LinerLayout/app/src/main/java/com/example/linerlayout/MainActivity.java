package com.example.linerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSendClick(View view) {
        TextView name = findViewById(R.id.textView);
        TextView group = findViewById(R.id.textView2);
        TextView message = findViewById(R.id.textView3);
        Spinner names = findViewById(R.id.spinner1);
        Spinner gpoups = findViewById(R.id.spinner2);
        EditText editText = findViewById(R.id.editText);

        message.setText(editText.getText().toString());
        editText.setText("");
        name.setText(names.getSelectedItem().toString());
        group.setText(gpoups.getSelectedItem().toString());

    }
}
