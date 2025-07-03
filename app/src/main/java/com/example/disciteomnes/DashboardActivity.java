package com.example.disciteomnes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.disciteomnes.model.Group;
import com.example.disciteomnes.repository.GroupRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    TextView usernameTextView;
    FirebaseAuth mAuth;
    GroupRepository repository;

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

        repository = new GroupRepository(this);
        loadGroupCount();

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
                return true;
            }
        });
    }

    private void loadGroupCount() {
        // ✅ Hole Token aus SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");


        repository.getGroups(token, new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()) {
                    List<Group> groups = response.body();
                    int count = (groups != null) ? groups.size() : 0;

                    // ✅ Setze die Zahl in dein TextView (Box 1)
                    TextView groupsCountText = findViewById(R.id.groupsCountText);
                    groupsCountText.setText(String.valueOf(count));
                } else {
                    Toast.makeText(DashboardActivity.this, "Fehler: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(DashboardActivity.this, "Netzwerkfehler", Toast.LENGTH_SHORT).show();
            }
        });
    }
}