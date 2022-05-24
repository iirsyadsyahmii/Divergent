package com.example.login;

import android.graphics.drawable.Drawable;

public class MainMenu{
    String name;
    int image;
Class activityName;
    public MainMenu(String name, int image,Class activityName) {
        this.name = name;
        this.image = image;
        this.activityName = activityName;
    }
    public MainMenu(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Class getActivityName() {
        return activityName;
    }

    public void setActivityName(Class activityName) {
        this.activityName = activityName;
    }

    public String getName() {
        return name;
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
