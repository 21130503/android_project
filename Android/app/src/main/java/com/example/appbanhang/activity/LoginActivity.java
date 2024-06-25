package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanhang.R;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    TextView registertxt, resetPass;
    Button btnLogin;
    EditText email, password;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        APIBanHang apiBanHang;
        boolean isLogin = false;
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

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();

        if (TextUtils.isEmpty(emailString)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            // Lưu thông tin đăng nhập cục bộ bằng Paper
            Paper.book().write("email", emailString);
            Paper.book().write("password", passwordString);

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
                                        Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra android: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            )
            );
            loginDelay(emailString, passwordString);


            // Kiểm tra nếu người dùng đã đăng nhập
            if (firebaseUser != null) {
                loginDelay(emailString, passwordString);
            } else {
                // Thực hiện đăng nhập bằng Firebase Authentication
                firebaseAuth.signInWithEmailAndPassword(emailString, passwordString)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Đăng nhập thành công
                                    loginDelay(emailString, passwordString);
                                } else {
                                    // Đăng nhập thất bại
                                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại. Vui lòng kiểm tra lại email và mật khẩu.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        }
    }


    public void Mapping(){
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        registertxt = findViewById(R.id.register);
        btnLogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);

        resetPass = findViewById(R.id.resetPass);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


//        Paper
        if(Paper.book().read("email") !=null && Paper.book().read("password") !=null){
            email.setText(Paper.book().read("email"));
            password.setText(Paper.book().read("password"));

            if(Paper.book().read("isLogin") != null){
                boolean flag =Paper.book().read("isLogin");
                if (flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loginDelay(Paper.book().read("email"),Paper.book().read("password"));
                        }
                    },1000);
                }
            }
        }
    }

    private void loginDelay(String email,String password) {
        compositeDisposable.add(apiBanHang.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                isLogin = true;
                                Paper.book().write("isLogin", isLogin);

                                Utils.currentUser = userModel.getResult().get(0);
                                Paper.book().write("user",  Utils.currentUser);
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
                            Log.e("API_ERROR", "Error occurred", throwable);
                            Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                )
        );
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