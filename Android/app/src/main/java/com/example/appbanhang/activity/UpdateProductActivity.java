package com.example.appbanhang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.databinding.ActivityUpdateProductBinding;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateProductActivity extends AppCompatActivity {

    private ActivityUpdateProductBinding binding;
    private APIBanHang apiBanHang;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);

        productId = getIntent().getIntExtra("product_id", -1);

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });
    }

    private void updateProduct() {
        String name = binding.name.getText().toString().trim();
        String price = binding.price.getText().toString().trim();
        String image = binding.image.getText().toString().trim();
        String description = binding.description.getText().toString().trim();
        int type = binding.type.getSelectedItemPosition();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(image) || TextUtils.isEmpty(description) || type == 0) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposable.add(apiBanHang.updateProduct(productId, name, price, image, description, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if (productModel.isSuccess()) {
                                Toast.makeText(this, productModel.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, productModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
