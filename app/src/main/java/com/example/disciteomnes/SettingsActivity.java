package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private Switch notificationsSwitch;
    private Spinner languageSpinner;
    private Button helpButton, logoutButton, deleteAccountButton;
    FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();

        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        languageSpinner = findViewById(R.id.languageSpinner);
        helpButton = findViewById(R.id.helpButton);
        logoutButton = findViewById(R.id.logoutButton);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);

        // Beispiel: Switch-Handler
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, "Benachrichtigungen " + (isChecked ? "aktiviert" : "deaktiviert"), Toast.LENGTH_SHORT).show();
            // Hier kannst du es auch in SharedPreferences speichern
        });

        // Spinner: Sprache
        String[] languages = {"Deutsch", "English"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        // Hilfe
        helpButton.setOnClickListener(v -> {
            Toast.makeText(this, "Hilfe wird geöffnet...", Toast.LENGTH_SHORT).show();
            // Öffne evtl. eine Hilfeseite
        });

        // Logout
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Erfolgreich ausgeloggt!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this, LoginSignupActivity.class));
            finish();
        });

        // Konto löschen
        deleteAccountButton.setOnClickListener(v -> {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                user.delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Konto gelöscht", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SettingsActivity.this, LoginSignupActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Fehler beim Löschen: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // NavigationBar
        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_settings);


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
                return true; // Bleib hier
            }
        });
    }
}