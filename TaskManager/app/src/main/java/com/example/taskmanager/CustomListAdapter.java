package com.example.taskmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.joda.time.LocalDate;

import java.util.List;



public class CustomListAdapter extends ArrayAdapter<Task> {
    private List<Task> taskList;
    private Context context;
    private int resource;
    private LocalDate date;
    private AppCompatActivity parentActivity;
    private ArrayAdapter adapter = this;

    public CustomListAdapter(@NonNull Context context, int resource, @NonNull List<Task> taskList, LocalDate date, AppCompatActivity parentActivity) {
        super(context, resource, taskList);
        this.taskList = taskList;
        this.context = context;
        this.resource = resource;
        this.date = date;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resource, parent, false);
        }
        final Task task = getItem(position);

        TextView tvTaskTitle = (TextView) convertView.findViewById(R.id.tvTaskTitle);
        tvTaskTitle.setText(task.getTaskTitle());

        TextView tvTaskDescription = (TextView) convertView.findViewById(R.id.tvTaskDescription);
        tvTaskDescription.setText(task.getTaskDescription());

        TextView tvTaskCreated = (TextView) convertView.findViewById(R.id.tvTaskCreated);
        LocalDate creationDate = new LocalDate(task.getCreationDate());
        tvTaskCreated.setText(parentActivity.getString(R.string.created) + ": " + creationDate.toString(MainActivity.shortDateFormat));

        TextView tvTaskModified = (TextView) convertView.findViewById(R.id.tvTaskModified);
        LocalDate modifyDate = new LocalDate(task.getModifyDate());
        tvTaskModified.setText(parentActivity.getString(R.string.modified) + ": " + modifyDate.toString(MainActivity.shortDateFormat));

        final CheckBox cbDone = (CheckBox) convertView.findViewById(R.id.cbDone);
        cbDone.setChecked(task.isDone());
        cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setDone(cbDone.isChecked());
            }
        });

        ImageButton ibEditTask = (ImageButton) convertView.findViewById(R.id.ibEdit);
        ibEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editTaskIntent = new Intent(getContext(), AddTaskActivity.class);
                editTaskIntent.putExtra("date", date.toDate().getTime());
                editTaskIntent.putExtra("listpos", position);
                editTaskIntent.putExtra("isEdit", true);
                context.startActivity(editTaskIntent);


            }
        });

        ImageButton ibDeleteTask = (ImageButton) convertView.findViewById(R.id.ibDelete);
        ibDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteTaskDialogFragment deleteTaskDialogFragment = new DeleteTaskDialogFragment(taskList, position, date, adapter);
                deleteTaskDialogFragment.show(parentActivity.getSupportFragmentManager(), "dialog");

            }
        });

        return convertView;
    }

}
