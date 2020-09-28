package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.LocalDate;

public class TasksListActivity extends AppCompatActivity {
    LocalDate date;
    Long dateLong;
    ListView lvTasksList;
    CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);


        Intent incomeIntent = getIntent();
        date = new LocalDate(incomeIntent.getLongExtra("date", 0l));
        dateLong = date.toDate().getTime();
        ((TextView) findViewById(R.id.tvTasksListTitle)).setText(getString(R.string.tasksFrom) + " "
                + date.toString(MainActivity.shortDateFormat));

        lvTasksList = findViewById(R.id.lvTasks);
        if (MainActivity.taskMap != null) {
            if (!MainActivity.taskMap.containsKey(dateLong)) {
                addTask(getCurrentFocus());
            }
        } else {
            addTask(getCurrentFocus());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivity.taskMap != null) {

            if (MainActivity.taskMap.containsKey(dateLong)) {
                if (adapter == null) {
                    //   taskMap contains a key
                    CustomListAdapter adapter = new CustomListAdapter(
                            getApplicationContext(), R.layout.taskcell, MainActivity.taskMap.get(dateLong), date, this
                    );
                    lvTasksList.setAdapter(adapter);
                }

            } else {
                finish();
            }

        } else finish();
    }

    public void addTask(View view) {
        Intent addTaskIntent = new Intent(this, AddTaskActivity.class);
        addTaskIntent.putExtra("date", date.toDate().getTime());
        startActivity(addTaskIntent);
    }
}

