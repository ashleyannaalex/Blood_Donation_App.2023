package com.example.blooddonationapp3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addDonor(View view) {
        Intent addIntent = new Intent(this, AddDonorActivity.class);
        startActivity(addIntent);
    }

    public void searchDonors(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }
    public void displayDonors(View view) {
        Intent displayIntent = new Intent(this, DisplayActivity.class);
        startActivity(displayIntent);
    }
    public void updateDonor(View view) {
        Intent updateIntent = new Intent(this, UpdateActivity.class);
        startActivity(updateIntent);
    }
    public void deleteDonor(View view) {
        Intent deleteIntent = new Intent(this, DeleteActivity.class);
        startActivity(deleteIntent);
    }
}
