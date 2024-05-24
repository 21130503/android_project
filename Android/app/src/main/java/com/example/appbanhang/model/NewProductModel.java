package com.example.appbanhang.model;

import java.util.List;

public class NewProductModel {
    private  boolean  success;
    private  String message;
    List<NewProduct> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NewProduct> getResults() {
        return results;
    }

    public void setResults(List<NewProduct> results) {
        this.results = results;
    }
}
