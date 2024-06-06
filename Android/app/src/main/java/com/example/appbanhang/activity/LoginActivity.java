package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    TextView registertxt;
    Button btnLogin;
    EditText email, password;
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        APIBanHang apiBanHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        initControl();
    }

    private void initControl() {
        registertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        if(TextUtils.isEmpty(emailString)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(passwordString)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else{
            compositeDisposable.add(apiBanHang.login(emailString, passwordString)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()){
                                            Utils.currentUser = userModel.getResult().get(0);
                                            Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    }

    public void Mapping(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        registertxt = findViewById(R.id.register);
        btnLogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.currentUser.getEmail() !=null){
            email.setText(Utils.currentUser.getEmail());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}