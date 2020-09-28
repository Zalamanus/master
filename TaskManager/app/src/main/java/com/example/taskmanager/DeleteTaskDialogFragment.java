package com.example.taskmanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.joda.time.LocalDate;

import java.util.List;

public class DeleteTaskDialogFragment extends DialogFragment {
    private List<Task> taskList;
    private int position;
    private LocalDate date;
    private ArrayAdapter adapter;
    DeleteTaskDialogFragment(List<Task> taskList, int position, LocalDate date, ArrayAdapter adapter) {
        super();
        this.taskList = taskList;
        this.position = position;
        this.date = date;
        this.adapter = adapter;
    }


    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_deleteTask)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete Task
                        taskList.remove(position);
                        // Если после удаления задачи список задач пуст, то удаляем лист из мапы задач
                        if (taskList.size()==0) {
                            MainActivity.taskMap.remove(date.toDate().getTime());
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
