package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;

public class AddTaskActivity extends AppCompatActivity {
    EditText etTaskTitle;
    EditText etTaskDescription;
    LocalDate date;
    int listPosition;
    Task taskToEdit;
    boolean isEdit;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Intent incomeIntent = getIntent();
        date = new LocalDate(incomeIntent.getLongExtra("date", 0l));
        listPosition = incomeIntent.getIntExtra("listpos", -1);
        isEdit = incomeIntent.getBooleanExtra("isEdit", false);

        etTaskTitle = (EditText) findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        tvTitle = findViewById(R.id.tvAddTaskTitle);

        if (isEdit)
            tvTitle.setText(getString(R.string.textEdit) + " " + date.toString(MainActivity.shortDateFormat));
        else
            tvTitle.setText(getString(R.string.addTask) + " " + date.toString(MainActivity.shortDateFormat));

        if (listPosition != -1) {
            ArrayList<Task> taskArrayList = (ArrayList<Task>) MainActivity.taskMap.get(date.toDate().getTime());
            taskToEdit = taskArrayList.get(listPosition);
            etTaskTitle.setText(taskToEdit.getTaskTitle());
            etTaskDescription.setText(taskToEdit.getTaskDescription());
        }
    }

    public void saveTask(View view) {
        if (taskToEdit != null) {
            taskToEdit.setTaskTitle(etTaskTitle.getText().toString());
            taskToEdit.setTaskDescription(etTaskDescription.getText().toString());
        } else {

            Task newTask = new Task(etTaskTitle.getText().toString(), etTaskDescription.getText().toString());
            if (MainActivity.taskMap == null) {

                ArrayList<Task> taskList = new ArrayList();
                taskList.add(newTask);

                MainActivity.taskMap = new HashMap<>();
                MainActivity.taskMap.put(date.toDate().getTime(), taskList);


            } else {
                Long dateLong = date.toDate().getTime();
                if (MainActivity.taskMap.containsKey(dateLong)) {
                    ArrayList<Task> taskList = MainActivity.taskMap.get(dateLong);
                    taskList.add(newTask);
                } else {
                    ArrayList<Task> taskList = new ArrayList();
                    taskList.add(newTask);
                    MainActivity.taskMap.put(dateLong, taskList);

                }
            }
        }
        finish();
    }
}
