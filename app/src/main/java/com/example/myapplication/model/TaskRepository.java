package com.example.myapplication.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.TaskDao;
import com.example.myapplication.model.TaskDatabase;

import java.util.List;
import androidx.lifecycle.LiveData;


public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks; // ✅ place this OUTSIDE any method

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks(); // ✅ this works only if taskDao returns LiveData
    }

    public void insert(Task task) {
        taskDao.insert(task);
    }

    public void update(Task task) {
        taskDao.update(task);
    }

    public void delete(Task task) {
        taskDao.delete(task);
    }

    public void deleteAllTasks() {
        new Thread(() -> taskDao.deleteAllTasks()).start(); // background thread to avoid blocking UI
    }


    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
}
