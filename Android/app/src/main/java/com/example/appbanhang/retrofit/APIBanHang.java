package com.example.appbanhang.retrofit;

import com.example.appbanhang.model.NewProductModel;
import com.example.appbanhang.model.TypeProduct;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIBanHang {
    @GET("typeProduct")
    Observable<TypeProductModel> getTypeProduct();
    @GET("newProduct")
    Observable<NewProductModel> getNewProducts();

}