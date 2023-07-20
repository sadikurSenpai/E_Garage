package com.example.e_garage;

public class ServiceModel {
    private String randomKey,serviceName,servicePrice,sellerMail,contactNumberOfSeller,pictureLink,companyName;

    public ServiceModel(String randomKey, String serviceName, String servicePrice, String sellerMail, String contactNumberOfSeller, String pictureLink, String companyName) {
        this.randomKey = randomKey;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.sellerMail = sellerMail;
        this.contactNumberOfSeller = contactNumberOfSeller;
        this.pictureLink = pictureLink;
        this.companyName = companyName;
    }

    public ServiceModel() {
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getSellerMail() {
        return sellerMail;
    }

    public void setSellerMail(String sellerMail) {
        this.sellerMail = sellerMail;
    }

    public String getContactNumberOfSeller() {
        return contactNumberOfSeller;
    }

    public void setContactNumberOfSeller(String contactNumberOfSeller) {
        this.contactNumberOfSeller = contactNumberOfSeller;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
