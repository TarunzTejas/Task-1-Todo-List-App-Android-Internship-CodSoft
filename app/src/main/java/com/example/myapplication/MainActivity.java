package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.TaskAdapter;
import com.example.myapplication.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList = new ArrayList<>();
    private FloatingActionButton fabAdd;

    private SharedPreferences prefs;
    private static final String PREF_NAME = "todo_prefs";
    private static final String TASK_LIST_KEY = "task_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        recyclerView = findViewById(R.id.recycler_view);
        fabAdd = findViewById(R.id.fab_add);

        adapter = new TaskAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        taskList = loadTasks();
        adapter.setTasks(taskList);

        fabAdd.setOnClickListener(view -> showAddTaskDialog());

        adapter.setOnItemClickListener(task -> {
            toggleTaskCompletion(task);
            saveTasks();
        });
    }

    private void showAddTaskDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        EditText editText = dialogView.findViewById(R.id.edit_text_task_title);
        EditText editDueDate = dialogView.findViewById(R.id.edit_text_due_date);
        EditText editPriority = dialogView.findViewById(R.id.edit_text_priority);
        EditText editDescription = dialogView.findViewById(R.id.edit_text_description); // make sure this is in your layout

        new AlertDialog.Builder(this)
                .setTitle("Add New Task")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = editText.getText().toString().trim();
                    String dueDate = editDueDate.getText().toString().trim();
                    String priority = editPriority.getText().toString().trim();
                    String description = editDescription.getText().toString().trim();

                    if (!title.isEmpty()) {
                        Task newTask = new Task(
                                title,
                                description.isEmpty() ? "No Description" : description,
                                dueDate.isEmpty() ? "No Due Date" : dueDate,
                                priority.isEmpty() ? "Normal" : priority,
                                false
                        );

                        taskList.add(newTask);
                        adapter.setTasks(taskList);
                        saveTasks();
                    } else {
                        Toast.makeText(this, "Task title cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void toggleTaskCompletion(Task task) {
        task.setCompleted(!task.isCompleted());
        adapter.setTasks(taskList);
    }

    private void saveTasks() {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString(TASK_LIST_KEY, json);
        editor.apply();
    }

    private List<Task> loadTasks() {
        String json = prefs.getString(TASK_LIST_KEY, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Task>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }
}

