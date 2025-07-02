package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    TextView usernameTextView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        TextView usernameTextView = findViewById(R.id.username);

        if (user != null) {
            String displayName = user.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                usernameTextView.setText(displayName);
            } else {
                usernameTextView.setText("Kein Name gefunden");
            }
        } else {
            usernameTextView.setText("Nicht eingeloggt");
        }

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(DashboardActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(DashboardActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(DashboardActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
                return true;
            } else {
                // Bleib im Dashboard
                return true;
            }
        });
    }

}