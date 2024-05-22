package com.example.appbanhang.retrofit;

import com.example.appbanhang.model.TypeProduct;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class TypeProductModel {
    boolean success;
    String message;
    List<TypeProduct> results;

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

    public List<TypeProduct> getResults() {
        return results;
    }

    public void setResults(List<TypeProduct> results) {
        this.results = results;
    }
    public static TypeProductModel fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, TypeProductModel.class);
    }
}
