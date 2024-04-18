package com.example.blooddonationapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private ListView resultListView;
    private ArrayAdapter<String> resultAdapter;
    private List<String> matchingDonors;
    private Query donorsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        donorsQuery = FirebaseDatabase.getInstance().getReference("donors")
                .orderByChild("bloodGroup");
        resultListView = findViewById(R.id.resultListView);
        matchingDonors = new ArrayList<>();
        resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, matchingDonors);
        resultListView.setAdapter(resultAdapter);
        // Call the method to listen for changes in the query
        listenForQueryChanges();
    }
    private void listenForQueryChanges() {
        if (donorsQuery != null) {
            donorsQuery.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // matchingDonors.clear();
                    for (DataSnapshot donorSnapshot : dataSnapshot.getChildren()) {
                        Donor donor = donorSnapshot.getValue(Donor.class);
                        if (donor != null) {
                            matchingDonors.add(donor.getName() +"\nBlood Group:" + donor.getBloodGroup()+"\nPhone No:" + donor.getPhoneNo()+"\nPlace:" + donor.getLocality());
                        }
                    }
                    resultAdapter.notifyDataSetChanged(); // Notify the adapter of changes
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }
}