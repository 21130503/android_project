package com.example.appbanhang.retrofit;

import com.example.appbanhang.model.NewProductModel;
import com.example.appbanhang.model.ProductModel;
import com.example.appbanhang.model.TypeProduct;
import com.example.appbanhang.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("register")
    @FormUrlEncoded
    Observable<UserModel> register(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("phoneNumber") String phoneNumber
    );
    @POST("login")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("create-order")
    @FormUrlEncoded
    Observable<UserModel> createOder(
            @Field("idUser") int idUser,
            @Field("totalPrice") String totalPrice,
            @Field("address") String address,
            @Field("carts") String cart
    );

}