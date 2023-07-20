package com.example.e_garage;

public class CartModel {
    private String productImage,productName,productCompanyName,contactNumber,productPrice,randomKey;

    public CartModel(String productImage, String productName, String productCompanyName, String contactNumber, String productPrice, String randomKey) {
        this.productImage = productImage;
        this.productName = productName;
        this.productCompanyName = productCompanyName;
        this.contactNumber = contactNumber;
        this.productPrice = productPrice;
        this.randomKey = randomKey;
    }

    public CartModel() {
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCompanyName() {
        return productCompanyName;
    }

    public void setProductCompanyName(String productCompanyName) {
        this.productCompanyName = productCompanyName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }
}
