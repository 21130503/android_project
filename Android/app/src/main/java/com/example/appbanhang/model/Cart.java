package com.example.appbanhang.model;

public class Cart {
    private int idProduct;
    private String nameProduct;
    private long priceProduct;
    private String imgProduct;
    private int count;

    public Cart() {
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(long priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
