package com.example.appbanhang.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.NewProductAdapter;
import com.example.appbanhang.adapter.PhoneAdapter;
import com.example.appbanhang.model.NewProduct;
import com.example.appbanhang.model.Product;
import com.example.appbanhang.model.ProductModel;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhoneActivity extends AppCompatActivity {
    RecyclerView recyclerViewPhone;
    Toolbar toolbar;
CompositeDisposable compositeDisposable = new CompositeDisposable();
APIBanHang apiBanHang;
List<Product> listProduct;
ProductModel productModel;
PhoneAdapter phoneAdapter;

int type ;
int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phone);
        type = getIntent().getIntExtra("type", 1);
        System.out.println("Hello");
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        ActionToolBar();
        getProduct();
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getProduct() {
        compositeDisposable.add(apiBanHang.getProducts(page, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if (productModel.isSuccess()) {
                                listProduct = productModel.getResults();
                                System.out.println("Hello world");
                                phoneAdapter = new PhoneAdapter(getApplicationContext(), listProduct);
                                recyclerViewPhone.setAdapter(phoneAdapter);
                            } else {
                                Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            // Log the error message
                            Log.e("API_ERROR", "Error connecting to server", throwable);
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                )
        );
    }


    public void Mapping(){
        toolbar = findViewById(R.id.toolbar);
        recyclerViewPhone = findViewById(R.id.recyclerview_phone);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listProduct = new ArrayList<>();
        recyclerViewPhone.setLayoutManager(layoutManager);
        recyclerViewPhone.setHasFixedSize(true);
    }
}