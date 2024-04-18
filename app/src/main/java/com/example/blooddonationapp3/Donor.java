package com.example.blooddonationapp3;

// Donor.java
public class Donor {
    private String name;
    private String bloodGroup;
    private String locality;
    private String phoneNo;
    private String age;
    private String gender;

    // Default constructor for Firebase
    public Donor() {
        // Empty constructor required for Firebase
    }

    // Parameterized constructor
    public Donor(String name, String bloodGroup,  String age, String gender, String locality, String phoneNo) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.gender = gender;
        this.locality = locality;
        this.phoneNo = phoneNo;

    }

    // Getter and setter methods...

    public String getName() {
        return name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }

    public String getLocality() {
        return locality;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    // Additional methods, if needed
}
