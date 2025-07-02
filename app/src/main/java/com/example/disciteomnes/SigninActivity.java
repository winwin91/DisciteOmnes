package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SigninActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    FirebaseAuth mAuth;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        editTextUsername = findViewById(R.id.username_input);
        editTextEmail = findViewById(R.id.email_input);
        editTextPassword = findViewById(R.id.password_input);
        editTextConfirmPassword = findViewById(R.id.confirm_password_input);
        registerButton = findViewById(R.id.signupbtn);

        registerButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(SigninActivity.this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(SigninActivity.this, "Passwörter stimmen nicht überein!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Registrierung erfolgreich
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Hole den Benutzernamen aus deinem EditText
                            String username = editTextUsername.getText().toString().trim();

                            // Jetzt den DisplayName setzen
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();

                            if (user != null) {
                                user.updateProfile(profileUpdates).addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Toast.makeText(SigninActivity.this, "Registrierung erfolgreich!", Toast.LENGTH_SHORT).show();

                                        // Weiter zur DashboardActivity
                                        Intent intent = new Intent(SigninActivity.this, DashboardActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SigninActivity.this, "Name konnte nicht gespeichert werden: "
                                                        + updateTask.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            // Fehler
                            Toast.makeText(SigninActivity.this, "Registrierung fehlgeschlagen: "
                                            + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

        });
    }
}