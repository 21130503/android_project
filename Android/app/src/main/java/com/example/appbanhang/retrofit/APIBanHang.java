package com.example.appbanhang.retrofit;

import android.os.Message;

import com.example.appbanhang.model.MessageModel;
import com.example.appbanhang.model.NewProductModel;
import com.example.appbanhang.model.OrderModel;
import com.example.appbanhang.model.ProductModel;
import com.example.appbanhang.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("forgetPassword")
    Observable<UserModel> validationOTP(
            @Query("otp") int otp
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
    Observable<OrderModel> createOder(
            @Field("idUser") int idUser,
            @Field("totalPrice") String totalPrice,
            @Field("address") String address,
            @Field("carts") String cart
    );

    @POST("forgetPassword")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );
    @POST("newPassword")
    @FormUrlEncoded
    Observable<UserModel> newPassword(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("get-order")
    Observable<OrderModel> getViewOrder(
            @Query("idUser") int idUser
    );

    @GET("search")
    Observable<ProductModel> getSearch(
            @Query("key") String key
    );
<<<<<<< HEAD
    @POST("delete")
    Observable<MessageModel> deleteProduct(
            @Field("id") int id
    );
    @POST("insert")
    @FormUrlEncoded
    Observable<MessageModel> isertProduct(
            @Field("nameProduct") String nameProduct,
            @Field("price") String price,
            @Field("img") String img,
            @Field("description") String description,
            @Field("type") int type
    );
    @POST("edit")
    @FormUrlEncoded
    Observable<MessageModel> editProduct(
            @Field("nameProduct") String nameProduct,
            @Field("price") String price,
            @Field("img") String img,
            @Field("description") String description,
            @Field("type") int type,
            @Field("id") int id
    );

=======

    @POST("typeProduct")
    Observable<TypeProductModel> addTypeProduct(@Body TypeProduct typeProduct);
>>>>>>> main
}