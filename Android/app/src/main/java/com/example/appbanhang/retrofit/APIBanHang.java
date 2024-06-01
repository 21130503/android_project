package com.example.appbanhang.retrofit;

import com.example.appbanhang.model.NewProductModel;
import com.example.appbanhang.model.ProductModel;
import com.example.appbanhang.model.TypeProduct;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIBanHang {
    @GET("typeProduct")
    Observable<TypeProductModel> getTypeProduct();
    @GET("newProduct")
    Observable<NewProductModel> getNewProducts();
    @GET("getProductByType")
    Observable<ProductModel> getProducts(
            @Query("page") int page,
            @Query("type") int type
    );
//    Observable<ProductModel> getProducts(
//            @Field("page") int page,
//            @Field("type") int type
//    );

}