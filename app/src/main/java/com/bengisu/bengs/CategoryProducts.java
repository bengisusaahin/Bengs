package com.bengisu.bengs;

public class CategoryProducts {
    private String catProductImage,catProductName,catProductPrice;

    public CategoryProducts() {
    }

    public CategoryProducts(String catProductImage, String catProductName, String catProductPrice) {
        this.catProductImage = catProductImage;
        this.catProductName = catProductName;
        this.catProductPrice = catProductPrice;
    }

    public CategoryProducts(String image) {
        this.catProductImage = image;
    }

    public String getProductImage() {
        return catProductImage;
    }

    public void setProductImage(String productImage) {
        this.catProductImage = productImage;
    }

    public String getProductName() {
        return catProductName;
    }

    public void setProductName(String productName) {
        this.catProductName = productName;
    }

    public String getProductPrice() {
        return catProductPrice;
    }

    public void setProductPrice(String productPrice) {
        this.catProductPrice = productPrice;
    }
}
