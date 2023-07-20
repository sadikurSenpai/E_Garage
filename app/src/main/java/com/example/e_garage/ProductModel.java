package com.example.e_garage;

public class ProductModel {
    private String contactNumber,type,productName,productCompanyName,productSellingPrice,productMRP,productDescription,pictureLink;

    public ProductModel(String contactNumber, String type, String productName, String productCompanyName, String productSellingPrice, String productMRP, String productDescription, String pictureLink) {
        this.contactNumber = contactNumber;
        this.type = type;
        this.productName = productName;
        this.productCompanyName = productCompanyName;
        this.productSellingPrice = productSellingPrice;
        this.productMRP = productMRP;
        this.productDescription = productDescription;
        this.pictureLink = pictureLink;
    }

    public ProductModel() {
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getProductSellingPrice() {
        return productSellingPrice;
    }

    public void setProductSellingPrice(String productSellingPrice) {
        this.productSellingPrice = productSellingPrice;
    }

    public String getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(String productMRP) {
        this.productMRP = productMRP;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
