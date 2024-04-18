package com.example.blooddonationapp3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    // Set the delay time in milliseconds (e.g., 3000 milliseconds or 3 seconds)
    private static final int SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Use a Handler to delay the opening of the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the delay
                Intent mainIntent = new Intent(splash.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // Close the splash activity so the user can't go back to it
            }
        }, SPLASH_DELAY);
    }
}