package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.disciteomnes.R;

public class LoginSignupActivity extends AppCompatActivity {

    private Button loginButton, signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        // Buttons aus dem Layout holen
        loginButton = findViewById(R.id.loginbtn);
        signinButton = findViewById(R.id.signinbtn);

        // LOGIN Button → LoginActivity
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginSignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // SIGNIN Button → SigninActivity
        signinButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginSignupActivity.this, SigninActivity.class);
            startActivity(intent);

        });

        loginButton.setOnClickListener(view -> {
            Log.d("BUTTON_TEST", "Login Button clicked!");
            Intent intent = new Intent(LoginSignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        signinButton.setOnClickListener(view -> {
            Log.d("BUTTON_TEST", "Signin Button clicked!");
            Intent intent = new Intent(LoginSignupActivity.this, SigninActivity.class);
            startActivity(intent);
        });

    }
}