package com.example.disciteomnes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.disciteomnes.adapters.TasksAdapter;
import com.example.disciteomnes.model.ApiResponse;
import com.example.disciteomnes.repository.TaskRepository;
import com.example.disciteomnes.model.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksActivity extends AppCompatActivity {

    private FloatingActionButton fabMenu;
    private List<Task> taskList;
    private TasksAdapter adapter;
    private TaskRepository repository;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#5F6FA9"));
        setContentView(R.layout.activity_tasks);

        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabMenu = findViewById(R.id.fabMenu);
        fabMenu.setOnClickListener(v -> showFabMenu());

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_tasks);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(TasksActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(TasksActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(TasksActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(TasksActivity.this, SettingsActivity.class));
                return true;
            } else {
                return true;
            }
        });
    }

    private void showFabMenu() {
        String[] options = {
                "Aufgabe erstellen",
                "Aufgaben bearbeiten",
                "Aufgaben löschen"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aktion wählen");
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    startActivity(new Intent(this, CreateTaskActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, EditTaskActivity.class));
                    break;
                case 2:
                    showDeleteTaskMenu();
                    break;
            }
        });
        builder.show();
    }

    private void showDeleteTaskMenu() {
        if (adapter == null || adapter.getTaskList().isEmpty()) {
            Toast.makeText(this, "Keine Aufgaben zum Löschen vorhanden.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1) Hole alle Task-Titel in ein String[]
        List<Task> tasks = adapter.getTaskList();
        String[] taskTitles = new String[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            taskTitles[i] = tasks.get(i).getTitle();
        }

        // 2) Zeige Auswahl-Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aufgabe wählen");
        builder.setItems(taskTitles, (dialog, which) -> {
            Task selectedTask = tasks.get(which);

            // 3) Bestätigen
            new AlertDialog.Builder(this)
                    .setTitle("Aufgabe löschen")
                    .setMessage("Möchtest du die Aufgabe \"" + selectedTask.getTitle() + "\" wirklich löschen?")
                    .setPositiveButton("Löschen", (d, w) -> deleteSelectedTask(selectedTask))
                    .setNegativeButton("Abbrechen", null)
                    .show();
        });

        builder.show();
    }

    private void deleteSelectedTask(Task task) {
        // ✅ Token laden
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "Kein Token gefunden! Bitte erneut einloggen.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Repository aufrufen
        repository.deleteTask(token, task.getId(), new retrofit2.Callback<com.example.disciteomnes.model.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.disciteomnes.model.ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TasksActivity.this, "Aufgabe gelöscht!", Toast.LENGTH_SHORT).show();
                    loadTasks();
                } else {
                    Toast.makeText(TasksActivity.this, "Fehler: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TasksActivity.this, "Netzwerkfehler", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTasks() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "Kein Token gefunden! Bitte erneut einloggen.", Toast.LENGTH_SHORT).show();
            return;
        }

        String groupId = "DEINE_GROUP_ID"; // Falls du gruppenbasiert arbeitest

        repository.getTasks(token, groupId, new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    List<Task> tasks = response.body();
                    adapter = new TasksAdapter(tasks);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(TasksActivity.this, "Fehler: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TasksActivity.this, "Netzwerkfehler", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


