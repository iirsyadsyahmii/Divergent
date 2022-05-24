package com.example.login;

public class CompanyMainMenu {
    String name;
    int image;
    Class activityName;

    public CompanyMainMenu(String name, int image, Class activtyName) {
        this.name = name;
        this.image = image;
        this.activityName = activtyName;
    }

    public Class getActivityName() { return activityName; }

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
