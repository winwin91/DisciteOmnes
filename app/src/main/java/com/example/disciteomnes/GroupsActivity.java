package com.example.disciteomnes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.disciteomnes.adapters.GroupsAdapter;
import com.example.disciteomnes.model.Group;
import com.example.disciteomnes.repository.GroupRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GroupsAdapter adapter;
    private GroupRepository repository;
    private FloatingActionButton fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#5F6FA9"));
        setContentView(R.layout.activity_groups);


        recyclerView = findViewById(R.id.recyclerViewGroups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new GroupRepository(this);

        loadGroups();

        fabMenu = findViewById(R.id.fabMenu);
        fabMenu.setOnClickListener(v -> showFabMenu());

        BottomNavigationView bottomNav = findViewById(R.id.naviBar);
        bottomNav.setSelectedItemId(R.id.navigation_groups);

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
                return true;
            }
        });
    }

    private void loadGroups() {
        // ✅ Token aus Storage holen
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");


        repository.getGroups(token, new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()) {
                    List<Group> groups = response.body();
                    adapter = new GroupsAdapter(groups);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("GroupsActivity", "Fehler: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showFabMenu() {
        String[] options = {
                "Gruppe erstellen",
                "Gruppe beitreten",
                "Gruppe bearbeiten",
                "Gruppe löschen"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aktion wählen");
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    startActivity(new Intent(this, CreateGroupActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, JoinGroupActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(this, EditGroupActivity.class));
                    break;
                case 3:
                    showDeleteGroupMenu();
                    break;
            }
        });
        builder.show();
    }


    private void showDeleteGroupMenu() {
        if (adapter == null || adapter.getGroupList().isEmpty()) {
            Toast.makeText(this, "Keine Gruppen zum Löschen vorhanden.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hole alle Gruppennamen in ein String[]
        List<Group> groups = adapter.getGroupList();
        String[] groupNames = new String[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            groupNames[i] = groups.get(i).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gruppe wählen");

        builder.setItems(groupNames, (dialog, which) -> {
            Group selectedGroup = groups.get(which);

            // Bestätigung
            new AlertDialog.Builder(this)
                    .setTitle("Gruppe löschen")
                    .setMessage("Möchtest du die Gruppe \"" + selectedGroup.getName() + "\" wirklich löschen?")
                    .setPositiveButton("Löschen", (d, w) -> deleteSelectedGroup(selectedGroup))
                    .setNegativeButton("Abbrechen", null)
                    .show();
        });

        builder.show();
    }

    private void deleteSelectedGroup(Group group) {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "Kein Token gefunden! Bitte erneut einloggen.", Toast.LENGTH_SHORT).show();
            return;
        }

        repository.deleteGroup(token, group.getId(), new retrofit2.Callback<com.example.disciteomnes.model.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.disciteomnes.model.ApiResponse> call, Response<com.example.disciteomnes.model.ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GroupsActivity.this, "Gruppe gelöscht!", Toast.LENGTH_SHORT).show();
                    loadGroups();
                } else {
                    Toast.makeText(GroupsActivity.this, "Fehler: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.disciteomnes.model.ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(GroupsActivity.this, "Netzwerkfehler", Toast.LENGTH_SHORT).show();
            }
        });
    }


}