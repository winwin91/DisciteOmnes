package com.example.disciteomnes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.disciteomnes.model.ApiResponse;
import com.example.disciteomnes.repository.GroupRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinGroupActivity extends AppCompatActivity {

    private EditText editGroupId;
    private Button btnJoinGroup;

    private GroupRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        editGroupId = findViewById(R.id.editGroupId);
        btnJoinGroup = findViewById(R.id.btnJoinGroup);

        repository = new GroupRepository(this);

        btnJoinGroup.setOnClickListener(v -> joinGroup());

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_groups);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(JoinGroupActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(JoinGroupActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(JoinGroupActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(JoinGroupActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(JoinGroupActivity.this, SettingsActivity.class));
                return true;
            } else {
                return true;
            }
        });
    }

    private void joinGroup() {
        String groupId = editGroupId.getText().toString().trim();

        if (groupId.isEmpty()) {
            editGroupId.setError("Bitte Gruppen-ID eingeben");
            return;
        }

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "Keine Gruppen mit diesem Token gefunden!", Toast.LENGTH_SHORT).show();
            return;
        }

        repository.joinGroup(token, groupId, new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(JoinGroupActivity.this, "Beigetreten!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(JoinGroupActivity.this, "Fehler: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(JoinGroupActivity.this, "Netzwerkfehler", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
