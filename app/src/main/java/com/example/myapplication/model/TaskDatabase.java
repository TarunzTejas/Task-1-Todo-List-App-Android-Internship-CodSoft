package com.example.myapplication.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Task;

@Database(entities = {Task.class}, version = 1 ,exportSchema =false)
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase instance;

    public abstract com.example.myapplication.data.TaskDao taskDao();


    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TaskDatabase.class,
                            "task_database"
                    ).fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
