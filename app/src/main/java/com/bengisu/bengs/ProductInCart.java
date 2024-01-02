package com.bengisu.bengs;

public class ProductInCart {
    private String productName,productImage,productPrice;
    int amount;

    public ProductInCart() {
    }

    public ProductInCart(String productName, String productImage, String productPrice, int amount) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.amount = amount;
    }
    public ProductInCart(ProductInCart products) {
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
