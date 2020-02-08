package com.e.uniqlosalewebscrapper;

public class Item {
    String imageLink;
    String brand;
    String name;
    String link;
    String origPrice;
    String salePrice;
    public Item(String name, String brand, String link, String imageLink, String origPrice, String salePrice) {
        this.name = name;
        this.brand = brand;
        this.link = link;
        this.imageLink = imageLink;
        this.origPrice = origPrice;
        this.salePrice = salePrice;
    }

    public String getName() {
        return name;
    }
    public String getBrand(){
        return brand;
    }
    public String getLink() {
        return link;
    }

    public String getImageLink(){
        return imageLink;
    }

    public String getOrigPrice() {
        return origPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }
    public String toString(){
        String str = "========================================= \n";
        str += "Product: " + getName() + "\n";
        str += "Link: " + getLink() + "\n";
        str += "Image: " + getImageLink() + "\n";
        str += "Price: " + origPrice + " -> " + salePrice;
        return str;
    }
}
