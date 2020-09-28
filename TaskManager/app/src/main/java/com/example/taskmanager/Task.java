package com.example.taskmanager;

import org.joda.time.LocalDate;


class Task {
    private String taskTitle;
    private String taskDescription;
    private Long creationDate;
    private Long modifyDate;
    private boolean done;

    Task(String taskTitle, String taskDescription) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        creationDate = new LocalDate().toDate().getTime();
        modifyDate = creationDate;
        done = false;
    }

    String getTaskTitle() {
        return taskTitle;
    }

    void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
        modifyDate = new LocalDate().toDate().getTime();
    }

    String getTaskDescription() {
        return taskDescription;
    }

    void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
        modifyDate = new LocalDate().toDate().getTime();
    }

    Long getCreationDate() {
        return creationDate;
    }

    Long getModifyDate() {
        return modifyDate;
    }

    boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }
}
