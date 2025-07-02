package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_groups);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(GroupsActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(GroupsActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(GroupsActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(GroupsActivity.this, SettingsActivity.class));
                return true;
            } else {
                // Bleib hier
                return true;
            }
        });
    }
}