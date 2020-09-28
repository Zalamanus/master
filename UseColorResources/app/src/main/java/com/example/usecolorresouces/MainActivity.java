package com.example.usecolorresouces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME);
    }

    public void onSelectAvto(View view) {
        Spinner spinner = findViewById(R.id.spinner);
        long selectedId = spinner.getSelectedItemId();
        Intent intent = null;
        if (selectedId == 0) intent = new Intent(this, LadaGrantaActivity.class);
        if (selectedId == 1) intent = new Intent(this, LadaPrioraActivity.class);
        if (selectedId == 2) intent = new Intent(this, LadaVestaActivity.class);
        startActivity(intent);

    }
}
