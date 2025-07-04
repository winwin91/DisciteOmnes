package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView helpText = findViewById(R.id.helpText);

        String helpContent = "📚 Hilfe & Support\n\n" +
                "Willkommen beim DisciteOmnes Hilfe-Center!\n\n" +
                "✅ Häufige Fragen:\n\n" +
                "- Wie logge ich mich aus?\n" +
                "  > In den Einstellungen auf 'Ausloggen' klicken.\n\n" +
                "- Wie lösche ich mein Konto?\n" +
                "  > In den Einstellungen auf 'Konto löschen' klicken.\n\n" +
                "✅ Support Kontakt:\n" +
                "fabio.cuccu@students.hs-mainz.de\n\n" +
                "✅ Datenschutz:\n" +
                "Wir schützen deine Daten nach DSGVO.\n\n" +
                "✅ Version:\n" +
                "DisciteOmnes v1.0";

        helpText.setText(helpContent);


        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_settings);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(HelpActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(HelpActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(HelpActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(HelpActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(HelpActivity.this, SettingsActivity.class));
                return true;
            } else {
                return true;
            }
        });
    }
}
