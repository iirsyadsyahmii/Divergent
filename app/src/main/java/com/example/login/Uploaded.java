package com.example.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Uploaded implements Serializable {
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    String Image;
    String Offer;
    String Restaurant;
    String Description;
    String voucherID;

    public Uploaded(){
    }

    public Uploaded(String Image, String Offer, String Restaurant, String Description) {
        this.Image = Image;
        this.Offer = Offer;
        this.Restaurant = Restaurant;
        this.Description = Description;

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
    }

}
