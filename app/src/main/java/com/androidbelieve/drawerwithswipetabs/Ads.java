package com.androidbelieve.drawerwithswipetabs;
public class Ads {
    private String status;
    private String specs;
    private String price;
    private String date;
    private int image_ads;

    public Ads() {
    }

    public Ads(String status,String specs,String price,String date,int image_ads) {
        this.status = status;
        this.specs = specs;
        this.price = price;
        this.date = date;
        this.image_ads=image_ads;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImage_ads() {
        return image_ads;
    }

    public void setImage_ads(int image_ads) {
        this.image_ads = image_ads;
    }

}