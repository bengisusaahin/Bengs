package com.bengisu.bengs;

public class Shops {
    private String shopImage, shopName;

    public Shops() {
    }
    public Shops(String shopImage) {
        this.shopImage = shopImage;
    }
    public Shops(String shopImage, String shopName) {
        this.shopImage = shopImage;
        this.shopName = shopName;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
