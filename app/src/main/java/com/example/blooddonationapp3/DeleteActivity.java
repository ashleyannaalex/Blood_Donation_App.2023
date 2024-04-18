package com.example.blooddonationapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeleteActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText;
    private Button deleteButton;
    private DatabaseReference donorsDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        donorsDbRef = FirebaseDatabase.getInstance().getReference("donors");

        nameEditText = findViewById(R.id.editTextName);
        phoneEditText = findViewById(R.id.editTextPhone);
        deleteButton = findViewById(R.id.buttonDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDonor();
            }
        });
    }

    private void deleteDonor() {
        final String name = nameEditText.getText().toString().trim();
        final String phoneNo = phoneEditText.getText().toString().trim();

        // Check if name and phone are not empty
        if (name.isEmpty() || phoneNo.isEmpty()) {
            Toast.makeText(this, "Please enter donor's name and phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a query to find the donor based on name and phone
        Query donorQuery = donorsDbRef.orderByChild("phoneNo").equalTo(phoneNo);

        donorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the donor exists
                if (dataSnapshot.exists()) {
                    for (DataSnapshot donorSnapshot : dataSnapshot.getChildren()) {
                        donorSnapshot.getRef().removeValue(); // Remove the donor from the database
                        Toast.makeText(DeleteActivity.this, "Donor deleted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after deletion
                    }
                } else {
                    Toast.makeText(DeleteActivity.this, "Donor not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(DeleteActivity.this, "Error deleting donor: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
