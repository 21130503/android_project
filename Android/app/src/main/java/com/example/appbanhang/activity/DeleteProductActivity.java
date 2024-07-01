package com.example.appbanhang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.databinding.ActivityDeleteProductBinding;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeleteProductActivity extends AppCompatActivity {

    private ActivityDeleteProductBinding binding;
    private APIBanHang apiBanHang;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        productId = getIntent().getIntExtra("product_id", -1);

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
    }

    private void deleteProduct() {
        compositeDisposable.add(apiBanHang.deleteProduct(productId)
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
