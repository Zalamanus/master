package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class myClass = null;
                Intent intent;
                if (position == 0) {
                    myClass = ProductsActivity.class;
                }
                if (position == 1) {
                    myClass = ClothesActivity.class;
                }
                if (position == 2) {
                    myClass = ToolsActivity.class;
                }
                if (position == 3) {
                    myClass = HouseholdActivity.class;
                }


                intent = new Intent(MainActivity.this, myClass);
                startActivity(intent);


            }
        });
    }
}
