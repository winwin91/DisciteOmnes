package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);

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
                // Bleib hier
                return true;
            }
        });
    }
}