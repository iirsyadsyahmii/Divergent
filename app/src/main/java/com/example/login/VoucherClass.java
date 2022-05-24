package com.example.login;

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

public class VoucherClass implements Serializable {
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    String Image;
    String Offer;
    String Restaurant;
    String Description;
    String voucherID;



    //    Class Activity;
    public VoucherClass(){

    }



    public VoucherClass(String Image, String Offer, String Restaurant,String Description){
        this.Image = Image;
        this.Offer = Offer;
        this.Restaurant = Restaurant;
        this.Description = Description;

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
    public String getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
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

    public String getOffer() {
        return Offer;
    }

    public void setOffer(String offer) {
        Offer = offer;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String restaurant) {
        Restaurant = restaurant;
    }
}
