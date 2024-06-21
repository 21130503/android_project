package com.example.appbanhang.utils;

import com.example.appbanhang.model.Cart;
import com.example.appbanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {

//     public  static  final String BASR_URL="http://192.168.1.132:8080/server_war/"; //quyen
    public static List<Cart> carts;

//     public  static  final String BASR_URL="http://192.168.43.233:8080/server_war/"; //nguyensang

//    public  static  final String BASR_URL="http://192.168.75.1:8080/server_war/";
    public static List<Cart> carts ;
    public static  List<Cart> purchases = new ArrayList<>();


    public static User currentUser = new User();
}
