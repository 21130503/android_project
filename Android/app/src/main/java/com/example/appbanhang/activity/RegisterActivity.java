package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanhang.R;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    EditText email, password, confirmPassword, phoneNumber, username ;
    Button registerBtn;
    APIBanHang apiBanHang;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        initControl();
    }

    private void initControl() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();
        String usernameStr = username.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        if(TextUtils.isEmpty(emailStr)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(passwordStr)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(confirmPass)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else if(!passwordStr.equals(confirmPass)){
            Toast.makeText(getApplicationContext(), "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(usernameStr)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập tên người dùng", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(phone)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }else{
            registerData(emailStr,usernameStr,passwordStr,phone);

        }

    }
    private  void  registerData(String emailStr, String usernameStr, String passwordStr,String phone ){
        compositeDisposable.add(apiBanHang.register(emailStr, usernameStr, passwordStr, phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    System.out.println(userModel);
                                    if(userModel.isSuccess()){
                                        Utils.currentUser.setEmail(emailStr);
//                                    Utils.currentUser.set
                                        Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    // Log the error and show a Toast message
                                    throwable.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                }
                        )
        );
    }

    private void Mapping() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.repassword);
        registerBtn = findViewById(R.id.btn_register);
        username = findViewById(R.id.username);
        phoneNumber = findViewById(R.id.phoneNumber);
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}