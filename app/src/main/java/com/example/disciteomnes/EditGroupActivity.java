package com.example.disciteomnes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.disciteomnes.model.GroupRequest;
import com.example.disciteomnes.model.Group;
import com.example.disciteomnes.repository.GroupRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGroupActivity extends AppCompatActivity {

    private EditText editGroupName, editGroupDescription;
    private Button btnEditGroup;
    private GroupRepository repository;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Gruppe bearbeiten");
        }

        editGroupName = findViewById(R.id.editGroupName);
        editGroupDescription = findViewById(R.id.editGroupDescription);
        btnEditGroup = findViewById(R.id.btnCreateGroup);

        repository = new GroupRepository(this);

        groupId = getIntent().getStringExtra("groupId");
        editGroupName.setText(getIntent().getStringExtra("groupName"));
        editGroupDescription.setText(getIntent().getStringExtra("groupDescription"));

        btnEditGroup.setText("Gruppe speichern");

        btnEditGroup.setOnClickListener(v -> updateGroup());

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_groups);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                startActivity(new Intent(EditGroupActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.navigation_groups) {
                startActivity(new Intent(EditGroupActivity.this, GroupsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_studyplan) {
                startActivity(new Intent(EditGroupActivity.this, StudyActivity.class));
                return true;
            } else if (itemId == R.id.navigation_tasks) {
                startActivity(new Intent(EditGroupActivity.this, TasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_settings) {
                startActivity(new Intent(EditGroupActivity.this, SettingsActivity.class));
                return true;
            } else {
                return true;
            }
        });
    }

    // ab hier generiert!
    private void updateGroup() {
        String name = editGroupName.getText().toString().trim();
        String description = editGroupDescription.getText().toString().trim();

        if (name.isEmpty()) {
            editGroupName.setError("Name darf nicht leer sein");
            return;
        }

        GroupRequest request = new GroupRequest();
        request.name = name;
        request.description = description;

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "Kein Token gefunden! Bitte erneut einloggen.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
