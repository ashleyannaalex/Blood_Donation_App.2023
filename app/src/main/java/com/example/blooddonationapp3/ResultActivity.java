package com.example.blooddonationapp3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ListView resultListView;
    private ArrayAdapter<String> resultAdapter;
    private List<String> matchingDonors;
    private Query donorsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Retrieve the blood group from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("bloodGroup")) {
            String bloodGroup = extras.getString("bloodGroup");

            // Reconstruct the query in ResultActivity
            donorsQuery = FirebaseDatabase.getInstance().getReference("donors")
                    .orderByChild("bloodGroup")
                    .equalTo(bloodGroup);
        } else {
            // Handle the case where bloodGroup is not provided in the intent
            Toast.makeText(this, "Blood group not provided", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }

        resultListView = findViewById(R.id.resultListView);
        matchingDonors = new ArrayList<>();
        resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, matchingDonors);
        resultListView.setAdapter(resultAdapter);

        // Call the method to listen for changes in the query
        listenForQueryChanges();
    }

    private void listenForQueryChanges() {
        if (donorsQuery != null) {
            donorsQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    matchingDonors.clear();
                    for (DataSnapshot donorSnapshot : dataSnapshot.getChildren()) {
                        Donor donor = donorSnapshot.getValue(Donor.class);
                        if (donor != null) {
                            matchingDonors.add(donor.getName() + "\nPhone No:" + donor.getPhoneNo() + "\nPlace:" + donor.getLocality());
                        }
                    }
                    resultAdapter.notifyDataSetChanged(); // Notify the adapter of changes
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                    Toast.makeText(ResultActivity.this, "Error in database query", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
