package com.example.appbanhang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.model.TypeProduct;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TypeProductActivity extends AppCompatActivity {
    EditText editTextName, editTextImageUrl;
    Button btnCreate, btnCancel;
    APIBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_product);

        // Khởi tạo Retrofit và APIBanHang
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);

        // Khởi tạo view và control
        initView();
        initControl();
    }

    private void initView() {
        editTextName = findViewById(R.id.editTextText2);
        editTextImageUrl = findViewById(R.id.editTextText3);
        btnCreate = findViewById(R.id.btn_taoLSP);
        btnCancel = findViewById(R.id.btn_huy);
    }

    private void initControl() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTypeProduct();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createTypeProduct() {
        String name = editTextName.getText().toString().trim();
        String imageUrl = editTextImageUrl.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập URL hình ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng TypeProduct và gọi API để thêm mới
        TypeProduct newTypeProduct = new TypeProduct(name, imageUrl);
        addTypeProduct(newTypeProduct);
    }

    private void addTypeProduct(TypeProduct typeProduct) {
        compositeDisposable.add(apiBanHang.addTypeProduct(typeProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.isSuccess()) {
                                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                finish(); // Đóng Activity sau khi thêm thành công
                            } else {
                                Toast.makeText(getApplicationContext(), "Thêm thất bại: " + response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}