package com.example.login;

import java.io.Serializable;

public class RewardClass implements Serializable{
    String Image;
    String Offer;
    String Points;
    String Restaurant;
    String Description;

    public RewardClass(){

    }


    public RewardClass(String Image, String Offer, String Points, String Restaurant, String Description){
        this.Image = Image;
        this.Offer = Offer;
        this.Points = Points;
        this.Restaurant = Restaurant;
        this.Description = Description;
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

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String restaurant) {
        Restaurant = restaurant;
    }
}
