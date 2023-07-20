package com.example.e_garage;

public class GarageModel {
    private String division,district,name,pictureLink,description,sellerMail;

    public GarageModel(String division, String district, String name, String pictureLink, String description, String sellerMail) {
        this.division = division;
        this.district = district;
        this.name = name;
        this.pictureLink = pictureLink;
        this.description = description;
        this.sellerMail = sellerMail;
    }

    public String getSellerMail() {
        return sellerMail;
    }

    public void setSellerMail(String sellerMail) {
        this.sellerMail = sellerMail;
    }

    public GarageModel() {
    }

    public String getDivision() {
        return division;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
