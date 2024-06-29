//package com.example.appbanhang.service;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.appbanhang.utils.Utils;
//import com.google.auth.oauth2.AccessToken;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.gson.JsonObject;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class FCMSender {
//    private final  String userFCMToken;
//    private final  String title;
//    private final  String body;
//    private final Context context;
//    private final  String postUrl = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send";
//
//    public FCMSender(String userFCMToken, String title, String body, Context context) {
//        this.userFCMToken = userFCMToken;
//        this.title = title;
//        this.body = body;
//        this.context = context;
//    }
//    public void sendNotification(String key) {
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        JSONObject mainObj = new JSONObject();
//        try{
//            JSONObject messageObject  = new JSONObject();
//            JSONObject notificationObject  = new JSONObject();
//
//            notificationObject.put("title", title);
//            notificationObject.put("body", body);
//
//            messageObject.put("token", userFCMToken);
//            messageObject.put("notification", notificationObject);
//
//            mainObj.put("message", messageObject);
//
//            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, response ->{
//            },
//            volleyError -> {
//
//            }){
//                @NonNull
//                public Map<String, String> getHeader(){
//
//                    Map<String, String> header = new HashMap<>();
//                    header.put("content-type", "application/json");
//                    header.put("authorization", "Bearer " + Utils.token);
//                    return  header;
//                }
//            };
//            requestQueue.add(request);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
//
