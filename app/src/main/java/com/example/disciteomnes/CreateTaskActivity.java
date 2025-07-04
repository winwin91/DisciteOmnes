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
import com.example.disciteomnes.repository.TaskRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText editTitle, editDescription, editDueDate, editAssignedTo;
    private CheckBox checkboxCompleted;
    private Button btnCreateTask;
    private TaskRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        editTitle = findViewById(R.id.editTaskTitle);
        editDescription = findViewById(R.id.editTaskDescription);
        editDueDate = findViewById(R.id.editDueDate);
        editAssignedTo = findViewById(R.id.editAssignedTo);
        btnCreateTask = findViewById(R.id.btnCreateTask);

        repository = new TaskRepository(this);

        editDueDate.setOnClickListener(v -> showDatePicker());

        btnCreateTask.setOnClickListener(v -> createTask());

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_groups);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(CreateTaskActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(CreateTaskActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(CreateTaskActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(CreateTaskActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(CreateTaskActivity.this, SettingsActivity.class));
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

    private void createTask() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String dueDate = editDueDate.getText().toString().trim();
        String assignedTo = editAssignedTo.getText().toString().trim();

        TaskRequest request = new TaskRequest();
        request.title = title;
        request.description = description;
        request.dueDate = dueDate;
        request.assignedTo = assignedTo;

        // generiert
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");
        // ...

        String groupId = "DEINE_GROUP_ID"; // Hole aus Context!

        repository.createTask(token, groupId, request, new Callback<>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateTaskActivity.this, "Aufgabe erstellt!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateTaskActivity.this, "Fehler: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            // Diese Methode ebenfalls!
            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CreateTaskActivity.this, "Netzwerkfehler", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
