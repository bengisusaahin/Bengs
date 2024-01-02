package com.bengisu.bengs;

public class Products {
    private String productImage,productName,productPrice;

    public Products() {
    }

    public Products(String productImage, String productName, String productPrice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Products(String image) {
        this.productImage = image;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
