package com.example.login;

public class DonationCompany {
    private String companyName;
    private String companyLocation;
    private Integer companyImage;

    public DonationCompany(String companyName, String companyLocation, Integer companyImage) {
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companyImage = companyImage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public Integer getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(Integer companyImage) {
        this.companyImage = companyImage;
    }
}
