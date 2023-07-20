package com.example.e_garage;

public class ModelUser {
   private String pictureLink,name,contactNumber,garageName,occupation,logInAs,division,district,garagePictureLink;

    public ModelUser(String pictureLink, String name, String contactNumber, String garageName, String occupation, String logInAs, String division, String district, String garagePictureLink) {
        this.pictureLink = pictureLink;
        this.name = name;
        this.contactNumber = contactNumber;
        this.garageName = garageName;
        this.occupation = occupation;
        this.logInAs = logInAs;
        this.division = division;
        this.district = district;
        this.garagePictureLink = garagePictureLink;
    }

    public ModelUser() {
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLogInAs() {
        return logInAs;
    }

    public void setLogInAs(String logInAs) {
        this.logInAs = logInAs;
    }

    public String getDivision() {
        return division;
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

    public String getGaragePictureLink() {
        return garagePictureLink;
    }

    public void setGaragePictureLink(String garagePictureLink) {
        this.garagePictureLink = garagePictureLink;
    }
}
