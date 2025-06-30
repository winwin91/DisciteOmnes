package com.example.disciteomnes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadActivity extends Activity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load); // dein Ladebildschirm-Layout

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                // Bereits eingeloggt
                startActivity(new Intent(LoadActivity.this, DashboardActivity.class));
            } else {
                // Nicht eingeloggt
                startActivity(new Intent(LoadActivity.this, LoginSignupActivity.class));
            }
            finish();
        }, 2000);
    }
}
