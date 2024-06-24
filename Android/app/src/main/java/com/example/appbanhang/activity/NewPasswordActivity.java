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

public class NewPasswordActivity extends AppCompatActivity {
    EditText password, confirmPass;
    Button resetBtn;
    TextView register;
    APIBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_password);
        initView();
        initContro();

    }

    private void initContro() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String value = intent.getStringExtra("email1");
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                String str_pass = password.getText().toString().trim();
                String str_confirmPass = confirmPass.getText().toString().trim();
                if(TextUtils.isEmpty(str_pass)){
                    Toast.makeText(getApplicationContext(), "ban chua nhap Password", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(str_confirmPass)) {
                    Toast.makeText(getApplicationContext(), "ban chua nhap ConfirmPassword", Toast.LENGTH_SHORT).show();
                }
                if(str_pass.equals(str_confirmPass)){
                    compositeDisposable.add(apiBanHang.newPassword(value,str_pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if (userModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent2);
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
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmPass);
        resetBtn = findViewById(R.id.btn_reset);
        register = findViewById(R.id.register);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}