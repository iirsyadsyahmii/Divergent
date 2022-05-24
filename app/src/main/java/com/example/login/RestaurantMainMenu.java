package com.example.login;

public class RestaurantMainMenu {
    String name;
    int image;
    Class activityName;

    public RestaurantMainMenu(String name, int image, Class activityName) {
        this.name = name;
        this.image = image;
        this.activityName = activityName;
    }

    public String getName() {
        return name;
    }

    public Class getActivityName() {
        return activityName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
