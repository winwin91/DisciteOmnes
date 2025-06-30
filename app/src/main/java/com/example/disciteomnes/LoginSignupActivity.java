package com.example.disciteomnes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.disciteomnes.R;

public class LoginSignupActivity extends AppCompatActivity {

    private Button loginButton, signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        loginButton = findViewById(R.id.loginbtn);
        signinButton = findViewById(R.id.signinbtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}