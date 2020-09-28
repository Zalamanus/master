package com.example.sharedpreference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToolsActivity extends AppCompatActivity {
    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    int choosenItem = -1;
    private static final String prefName = "TOOLS_PREFERENCES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        arrayList = new ArrayList<>();
        SharedPreferences preferencesToRestore = getSharedPreferences(prefName,MODE_PRIVATE);
        for (int i = 0; i < preferencesToRestore.getInt("listSize", 0); i++) {
            arrayList.add(preferencesToRestore.getString(String.valueOf(i), ""));
        }

        listView = findViewById(R.id.toolsList);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosenItem = position;
            }
        });

    }
    public void onClickAdd(View view) {
        EditText editText = findViewById(R.id.editTools);
        String textToAdd = editText.getText().toString();
        if (!textToAdd.equals("")) {
            arrayList.add(editText.getText().toString());
            arrayAdapter.notifyDataSetChanged();
            editText.setText("");
        } else {
            Toast toast = Toast.makeText(getBaseContext(),"Впишите инструмент для добавления",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    public void onClickRemove(View view) {
        if (!arrayList.isEmpty()) {
            if (choosenItem >= 0) arrayList.remove(choosenItem);
            choosenItem = -1;
            arrayAdapter.notifyDataSetChanged();
        } else {
            Toast toast = Toast.makeText(getBaseContext(),"Список уже пуст",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePreferences();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        savePreferences();
    }

    private void savePreferences() {
        SharedPreferences preferences = getSharedPreferences(prefName, MODE_PRIVATE );
        SharedPreferences.Editor editor= preferences.edit();
        int listSize = arrayList.size();
        editor.putInt("listSize", listSize);
        for (int i = 0; i < listSize; i++ ) {
            editor.putString(String.valueOf(i), arrayList.get(i));
        }
        editor.apply();

    }
}
