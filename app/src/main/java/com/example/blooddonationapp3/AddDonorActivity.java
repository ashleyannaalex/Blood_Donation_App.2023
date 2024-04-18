package com.example.blooddonationapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDonorActivity extends AppCompatActivity {

    private EditText nameEditText, bloodGroupEditText, ageEditText, genderEditText,  localityEditText, phoneNoEditText;
    private DatabaseReference donorsDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        nameEditText = findViewById(R.id.editTextName);
        bloodGroupEditText = findViewById(R.id.editTextBloodGroup);
        ageEditText = findViewById(R.id.editTextAge);
        genderEditText = findViewById(R.id.editTextGender);
        localityEditText = findViewById(R.id.editTextLocality);
        phoneNoEditText = findViewById(R.id.editTextPhoneNo);
        donorsDbRef = FirebaseDatabase.getInstance().getReference("donors");
    }

    public void addDonor(View view) {
        String name = nameEditText.getText().toString();
        String bloodGroup = bloodGroupEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String locality = localityEditText.getText().toString();
        String phoneNo = phoneNoEditText.getText().toString();

        Donor newDonor = new Donor(name, bloodGroup, age, gender,  locality, phoneNo);

        String donorId = donorsDbRef.push().getKey();
        donorsDbRef.child(donorId).setValue(newDonor);
        Toast.makeText(this, "Donor Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // You can add a Toast or other UI feedback here
    }
}
