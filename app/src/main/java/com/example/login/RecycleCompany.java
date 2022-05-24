package com.example.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class RecycleCompany implements Serializable {
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
     String Name;
     String Location;
     String Description;
     String Image;
     String companyID;
    public RecycleCompany(){

    }

    public RecycleCompany(String Name, String Location, String Image, String Description) {
        this.Name = Name;
        this.Location = Location;
        this.Description = Description;
        this.Image = Image;
    }
    @Exclude
    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }


    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
}
