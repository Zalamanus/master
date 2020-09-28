package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private static final String PREF_TAG = "TasksSharedPrefs";
    private static final String TASK_TAG = "Tasks";
    public static final DateTimeFormatter shortDateFormat = DateTimeFormat.forPattern("dd-MM-YYYY");
    public static final DateTimeFormatter longDateFormat = DateTimeFormat.forPattern("d MMM, YYYY");
    public static HashMap<Long, ArrayList<Task>> taskMap;
    private CalendarPickerView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE);
        String json = sharedPref.getString(TASK_TAG, "");
        if (!json.equals("")) {
            Type type = new TypeToken<HashMap<Long, ArrayList<Task>>>() {
            }.getType();

            taskMap = gson.fromJson(json, type);
        }


        calendar = findViewById(R.id.calendar_view);
    }

    private static DateFormatSymbols dateFormatSymbols = new DateFormatSymbols() {
        @Override
        public String[] getMonths() {
            return new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        }
    };

    private ArrayList<SubTitle> getSubTitles() {
        ArrayList<SubTitle> usedDatesList = new ArrayList<>();
        ArrayList<Task> tasksList;
        if (taskMap != null) {
            for (Long key : taskMap.keySet()) {
                int allTasks = 0;
                int doneTasks = 0;
                tasksList = taskMap.get(key);
                for (Task task : tasksList) {
                    allTasks++;
                    if (task.isDone()) doneTasks++;

                }
                usedDatesList.add(new SubTitle(new Date(key), doneTasks + "/" + allTasks));
            }
        }
        return usedDatesList;

    }

    public void showTasks(View view) {
        Intent intent = new Intent(this, TasksListActivity.class);
        Date dateToSend = calendar.getSelectedDate();
        intent.putExtra("date", dateToSend.getTime());
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveInstanceState();

    }

    @Override
    protected void onResume() {
        super.onResume();
        GregorianCalendar lastYear = new GregorianCalendar();
        lastYear.add(Calendar.YEAR, -1);
        GregorianCalendar nextYear = new GregorianCalendar();
        nextYear.add(Calendar.YEAR, 1);
        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM YYYY", dateFormatSymbols))
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date())
                .withSubTitles(getSubTitles());
    }

    private void saveInstanceState() {
        System.out.println(taskMap == null);
        if (taskMap != null) {
            //taskMap in not null
            Gson gson = new Gson();
            String json = gson.toJson(taskMap);
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREF_TAG, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString(TASK_TAG, json);
            editor.commit();
        }
    }
}
