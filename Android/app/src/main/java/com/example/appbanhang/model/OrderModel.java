package com.example.appbanhang.model;

import java.util.List;

public class OrderModel {
    boolean success;
    String message;
    List<Order> results;

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

    public List<Order> getResults() {
        return results;
    }

    public void setResults(List<Order> results) {
        this.results = results;
    }
}
