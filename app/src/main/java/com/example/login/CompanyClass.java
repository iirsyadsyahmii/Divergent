package com.example.login;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;

public class CompanyClass implements Serializable {
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    String Image;
    String Description;
    String Company;
    String Location;
    String companyID;



    //    Class Activity;
    public CompanyClass(){

    }



    public CompanyClass(String Image, String Description, String Company,String Location){
        this.Image = Image;
        this.Description = Description;
        this.Company = Company;
        this.Location = Location;

//        this.Activity = Activity;
    }

//    public Class getActivity() {
//        return Activity;
//    }
//
//    public void setActivity(Class activity) {
//        Activity = activity;
//    }


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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }
}
