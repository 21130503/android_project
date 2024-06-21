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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OTPActivity extends AppCompatActivity {

    EditText otp;

    Button resPass;

    APIBanHang apiBanHang;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpactivity);
       initView();
       initContro();
    }
    private void initContro() {
        resPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_otp = otp.getText().toString().trim();
                if(TextUtils.isEmpty(str_otp)){
                    Toast.makeText(getApplicationContext(), "ban chua nhap OTP", Toast.LENGTH_SHORT).show();
                }else {
                    compositeDisposable.add(apiBanHang.validationOTP(str_otp)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(

                                    userModel -> {
                                        if (userModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), NewPasswordActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });

    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        otp = findViewById(R.id.otp);
        resPass = findViewById(R.id.resPassword);
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}