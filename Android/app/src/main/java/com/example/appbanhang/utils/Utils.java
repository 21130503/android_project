package com.example.appbanhang.utils;

import com.example.appbanhang.model.Cart;
import com.example.appbanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public  static  final String BASR_URL="http://192.168.75.1:8080/server_war/";
    public static List<Cart> carts ;
    public static  List<Cart> purchases = new ArrayList<>();
    public static User currentUser = new User();
    public static String token ;
    public static  String  ID_RECEIVER;
    public static final  String SENDID = "idSend";
    public static  final String RECEIVEDID = "idReceived";
    public static  final String MESS = "message";
    public static  final String PATH_CHAT = "chat";
    public static  final String DATETIME = "dateTime";


//    public static  serviceAccountJson ={
//        "type": "service_account",
//                "project_id": "heart-ba13d",
//                "private_key_id": "3f4b0cb283320bf7dc89bbbdd4cf34487511dc41",
//                "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC3PqPjfpRWXdLK\n2b50U2/YGdrXM9Jh00+ljUNI7Wyy56uiV2oJE5w6gfJ05xG4tpJsuieYRlbAl34f\n7M/W5cLTR4uHWhqofVnxw5CJYF9wKWFEBq8KL2wldgcw2V4YpzSyYpUYyzHzctnr\nslDtCeC3IGj0YSONZVl2tlqkJKlJvLIV/E0RiS3pO3kYTJaGO8l+gHekEV/iodB+\nFioM5aXMdjI+Whjn3oe1KJsjoBHtsc2Q6EXDo3vN/Tg9GLCc2t2wd3CvjzWo/gvo\nNRrwFqgw7Ra1iGBPI2a2OMKxdBVIDZsp/sgWLB9l56Xuav/YL/NwY+bOPp1WOyyj\nf0IEF6nFAgMBAAECggEAE89czmJHzRJ1kGJ72X2PsNASvYMnslWy3ZXo+UBZqPms\nr2890l9hW13jBUpZuxtZtxFk6GxqQoXeJ7tlOLk8F7U7W1WdXiu3BpD61i8qAikn\nD8CdOb+otGAPJPPK3WVuJ/7KWZTWAJihAH8VipqvxXxmfEzoGAgl5NSP9gDUoFjr\n/OTLZhBQcsxMDkAvfLp8Y9ADllRO9WUS2j2+h0Td4wspiznGTO0Ek1v1zaF9f0hr\nOXV7uI+DG8rG9Qls8tLQTViqQ7eWbrJWBAQRRRJVlux00U3bu2R9JHnYQBG2983q\nsTyD7Kg5AOKiwyAvx1FcAd/fuEMaQ0gIHa6VWT3HwQKBgQDpnJiK+vYnyJ2azANz\nIC8QhQZRiU7DZZiZnuiRersPl2mJbIo6UJWn99LKhRRvsATOTZAcen2d56PigiLm\n7DirHKNlAfv98hayKzXK1VfWjY21HublVs+lgMzvF/FpfT8+QSdTwJFvEpDpnt3J\nh3C8NDDuxD0JmsWZvpo1ZmoHFQKBgQDIzlhwJPcqSsruW3Pkh+gwKlmdcDe+Nf98\nV/K1QoQCbYXmRGnP0tHe95kNTQT/sC7pcX8R1YPS3kumUBoaCkBPQtHth5GfVSxW\nlP7VNlQ0IGPVujuxfDQVSmosojg8hVz/LQQ3jZGF+WGpD4a/dPJnbX0E+Dw80b4T\n5erUU9rD8QKBgQDTmbZKXNtOWdb3+cc+Jm67BeMdQ8QGFSZOL7P04jhB/SHpClmC\nTK/h/wX9zuEd8U/JFlRslz7lIoY9iybWCC2kQEJ4IX4BHaEVLTKCURBJFNrArgby\nwNlAzhPSldtjc8EvQJDAufKBq017bxzxiC7DKnsQKswmwPwvMIg5ylRLqQKBgDCe\npZMhoCbBTTlBM2JV9cYTuvb/wL9xW5bDxKRWopfj5NPIJMaJCP+ve4NY7qjqA7HK\nJTYwOGbKJA70vh0n9kI6r5GPjZbCjKW6FamSKfklDoclPGbQ59tv1R1RecRNliLC\nx46b3GxP4XGJrNqWekrIm8ikQhGHxzoSJTWbyBBBAoGBAJc8gnv80CDo4I3N5Y/0\nN0028zuG/JTH65xrY3yyR3VPID5sD40m7afytzzc+VGfyLEYuTLn/40+rexq89t/\n7hos2p7VNWN2KysvSE4ZvwGMg19hUxifP8jZe+gOFk0P0MYD9sr0LFXCRPQnA+Lw\naBHMk9Kr2Qd0i0e1i3jYv1sC\n-----END PRIVATE KEY-----\n",
//                "client_email": "huuquyadmin@heart-ba13d.iam.gserviceaccount.com",
//                "client_id": "110205038973594704621",
//                "auth_uri": "https://accounts.google.com/o/oauth2/auth",
//                "token_uri": "https://oauth2.googleapis.com/token",
//                "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
//                "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/huuquyadmin%40heart-ba13d.iam.gserviceaccount.com",
//                "universe_domain": "googleapis.com"
//    }
}
