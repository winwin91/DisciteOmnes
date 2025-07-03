package com.example.disciteomnes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.disciteomnes.model.TaskRequest;
import com.example.disciteomnes.model.Task;
import com.example.disciteomnes.repository.TaskRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTitle, editDescription, editDueDate, editAssignedTo;
    private Button btnEditTask;

    private TaskRepository repository;
    private String taskId; // Zum Bearbeiten

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task); // Du kannst dasselbe Layout wiederverwenden!

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Aufgabe bearbeiten");
        }

        editTitle = findViewById(R.id.editTaskTitle);
        editDescription = findViewById(R.id.editTaskDescription);
        editDueDate = findViewById(R.id.editDueDate);
        editAssignedTo = findViewById(R.id.editAssignedTo);
        btnEditTask = findViewById(R.id.btnCreateTask);

        repository = new TaskRepository(this);

        // Hole Daten aus Intent
        taskId = getIntent().getStringExtra("taskId");
        editTitle.setText(getIntent().getStringExtra("taskTitle"));
        editDescription.setText(getIntent().getStringExtra("taskDescription"));
        editDueDate.setText(getIntent().getStringExtra("taskDueDate"));
        editAssignedTo.setText(getIntent().getStringExtra("taskAssignedTo"));

        editDueDate.setOnClickListener(v -> showDatePicker());

        btnEditTask.setText("Aufgabe speichern");
        btnEditTask.setOnClickListener(v -> updateTask());

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_tasks);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(EditTaskActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(EditTaskActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(EditTaskActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(EditTaskActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(EditTaskActivity.this, SettingsActivity.class));
                return true;
            } else {
                return true;
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                    editDueDate.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    private void updateTask() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String dueDate = editDueDate.getText().toString().trim();
        String assignedTo = editAssignedTo.getText().toString().trim();

        if (title.isEmpty()) {
            editTitle.setError("Titel darf nicht leer sein");
            return;
        }

        TaskRequest request = new TaskRequest();
        request.title = title;
        request.description = description;
        request.dueDate = dueDate;
        request.assignedTo = assignedTo;

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");


        repository.updateTask(token, taskId, request, new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditTaskActivity.this, "Aufgabe aktualisiert!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditTaskActivity.this, "Fehler: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(EditTaskActivity.this, "Netzwerkfehler", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
