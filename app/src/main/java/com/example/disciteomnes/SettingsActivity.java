package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    // .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(SettingsActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(SettingsActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(SettingsActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(SettingsActivity.this, TasksActivity.class));
                return true;
            } else {
                // Bleib hier
                return true;
            }
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}