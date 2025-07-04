package com.example.disciteomnes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteGroupActivity extends AppCompatActivity {

    private Button btnConfirmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_group);

        btnConfirmDelete = findViewById(R.id.btnConfirmDelete);

        btnConfirmDelete.setOnClickListener(v -> {
            Toast.makeText(this, "Keine Gruppe zum Löschen vorhanden!", Toast.LENGTH_SHORT).show();
        });
    }
}
