package com.example.appbanhang.model.EventBus;

import com.example.appbanhang.model.NewProduct;
import com.example.appbanhang.model.Product;

public class EditDeleteEvent {
    Product newProduct;

    public EditDeleteEvent(Product newProduct) {
        this.newProduct = newProduct;
    }

    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }
}
