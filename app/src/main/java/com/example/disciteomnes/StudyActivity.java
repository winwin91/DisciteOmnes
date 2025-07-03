package com.example.disciteomnes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;



//         Diese Activity habe ich leider nicht mehr geschafft...
//       ...Dafür gibt es eine lustige Katze!


public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#5F6FA9"));
        setContentView(R.layout.activity_study);

        ImageView gifView = findViewById(R.id.studyGif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.funnycat)
                .into(gifView);

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_studyplan);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(StudyActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(StudyActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(StudyActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(StudyActivity.this, SettingsActivity.class));
                return true;
            } else {
                return true;
            }
        });
    }
}