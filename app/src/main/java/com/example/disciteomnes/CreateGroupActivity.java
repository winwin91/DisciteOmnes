package com.example.disciteomnes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.disciteomnes.api.ApiClient;
import com.example.disciteomnes.model.GroupRequest;
import com.example.disciteomnes.model.Group;
import com.example.disciteomnes.repository.GroupRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText editGroupName, editGroupDescription;
    private Button btnCreateGroup;

    private GroupRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Gruppe erstellen");
        }

        editGroupName = findViewById(R.id.editGroupName);
        editGroupDescription = findViewById(R.id.editGroupDescription);
        btnCreateGroup = findViewById(R.id.btnCreateGroup);

        repository = new GroupRepository(this);

        btnCreateGroup.setOnClickListener(v -> createGroup());

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_groups);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(CreateGroupActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(CreateGroupActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(CreateGroupActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(CreateGroupActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(CreateGroupActivity.this, SettingsActivity.class));
                return true;
            } else {
                return true;
            }
        });
    }

    private void createGroup() {
        String name = editGroupName.getText().toString().trim();
        String description = editGroupDescription.getText().toString().trim();

        if (name.isEmpty()) {
            editGroupName.setError("Name darf nicht leer sein");
            return;
        }

        GroupRequest request = new GroupRequest();
        request.name = name;
        request.description = description;

        // generiert ....
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");
        // ...


        repository.createGroup(token, request, new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateGroupActivity.this, "Gruppe erstellt!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateGroupActivity.this, "Fehler: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CreateGroupActivity.this, "Netzwerkfehler", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
