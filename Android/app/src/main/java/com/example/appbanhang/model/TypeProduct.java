package com.example.appbanhang.model;

public class TypeProduct {
    int id;
    String name;
    String image;

    public TypeProduct() {
    }

    public TypeProduct(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameProduct) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imageProduct) {
        this.image = image;
    }
}
