package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String dueDate;
    private String priority;
    private String timestamp;
    private boolean isCompleted;

    // Constructor
    public Task(String title, String dueDate, String priority, String timestamp, boolean isCompleted) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.timestamp = timestamp;
        this.isCompleted = isCompleted;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}


