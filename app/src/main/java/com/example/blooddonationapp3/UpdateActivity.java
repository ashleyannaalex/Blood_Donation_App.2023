package com.example.blooddonationapp3;

import android.os.Bundle;
import android.text.TextUtils;
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

public class UpdateActivity extends AppCompatActivity {

    private EditText donorNameEditText, donorPhoneEditText, updatedAgeEditText, updatedLocalityEditText;
    private Button updateButton;
    private DatabaseReference donorsDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        donorsDbRef = FirebaseDatabase.getInstance().getReference("donors");

        donorNameEditText = findViewById(R.id.editTextDonorName);
        donorPhoneEditText = findViewById(R.id.editTextDonorPhone);
        updatedAgeEditText = findViewById(R.id.editTextUpdatedAge);
        updatedLocalityEditText = findViewById(R.id.editTextUpdatedLocality);
        updateButton = findViewById(R.id.buttonUpdate);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDonorDetails();
            }
        });
    }

    private void updateDonorDetails() {
        final String donorName = donorNameEditText.getText().toString().trim();
        final String donorPhone = donorPhoneEditText.getText().toString().trim();
        final String updatedAge = updatedAgeEditText.getText().toString().trim();
        final String updatedLocality = updatedLocalityEditText.getText().toString().trim();

        if (TextUtils.isEmpty(donorName) || TextUtils.isEmpty(donorPhone) ||
                TextUtils.isEmpty(updatedAge) || TextUtils.isEmpty(updatedLocality)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the donor exists in the database
        Query query = donorsDbRef.orderByChild("phoneNo").equalTo(donorPhone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Donor exists, update the details
                    for (DataSnapshot donorSnapshot : dataSnapshot.getChildren()) {
                        DatabaseReference donorRef = donorSnapshot.getRef();
                        donorRef.child("age").setValue(updatedAge);
                        donorRef.child("locality").setValue(updatedLocality);
                        Toast.makeText(UpdateActivity.this, "Donor details updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after deletion
                    }
                } else {
                    Toast.makeText(UpdateActivity.this, "Donor not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
