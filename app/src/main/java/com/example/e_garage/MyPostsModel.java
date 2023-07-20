package com.example.e_garage;

public class MyPostsModel {
    private String type,randomKey,productName,productPrice,contactNumber;

    public MyPostsModel(String type, String randomKey, String productName, String productPrice, String contactNumber) {
        this.type = type;
        this.randomKey = randomKey;
        this.productName = productName;
        this.productPrice = productPrice;
        this.contactNumber = contactNumber;
    }

    public MyPostsModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
