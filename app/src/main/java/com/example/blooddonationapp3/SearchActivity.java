package com.example.blooddonationapp3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SearchActivity extends AppCompatActivity {

    private EditText bloodGroupEditText;
    private DatabaseReference donorsDbRef;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        donorsDbRef = FirebaseDatabase.getInstance().getReference("donors");
        bloodGroupEditText = findViewById(R.id.editTextBloodGroup);
        //localityEditText = findViewById(R.id.editTextLocality);
        searchButton = findViewById(R.id.buttonSearchDonors);

        // Set a click listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SearchActivity", "Search button clicked");
                String bloodGroup = bloodGroupEditText.getText().toString();
                // String locality = localityEditText.getText().toString();
                createDonorsQuery(bloodGroup); // Call the method to create the query
            }
        });
    }

    private void createDonorsQuery(String bloodGroup) {
        // Create a query to filter the donors based on the blood group
        Query donorsQuery = donorsDbRef.orderByChild("bloodGroup").startAt(bloodGroup).endAt(bloodGroup + "\uf8ff");

        // Add a ValueEventListener to the query
        donorsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> matchingDonors = new ArrayList<>();

                for (DataSnapshot donorSnapshot : dataSnapshot.getChildren()) {
                    // Add the donor details to the list
                    matchingDonors.add(donorSnapshot.getValue(Donor.class).getName());
                }

                // Check if any matching donors were found
                if (matchingDonors.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "No matching donors found", Toast.LENGTH_SHORT).show();
                } else {
                    // Pass the query parameters
                    Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                    intent.putExtra("bloodGroup", bloodGroup);
                    intent.putStringArrayListExtra("matchingDonors", (ArrayList<String>) matchingDonors);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e("SearchActivity", "Error in database query: " + databaseError.getMessage());
            }
        });
    }
}