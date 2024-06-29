package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.NewProductAdapter;
import com.example.appbanhang.model.EventBus.EditDeleteEvent;
import com.example.appbanhang.model.NewProductModel;
import com.example.appbanhang.model.Product;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ManagerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APIBanHang apiBanHang;
    List<Product> list;
    NewProductAdapter adapter;
    Product productEditDelete;

    NeumorphCardView addProduct, updateProduct, deleteProduct;
    Toolbar toolbarManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manager);
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        initView();
        getNewProduct();
        initControl();
    }

    private void initControl() {
        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        intent.putExtra("sua", productEditDelete);
        startActivity(intent);
    }
    public void  getNewProduct(){
        compositeDisposable.add(apiBanHang.getNewProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newProductModel -> {
                            if(newProductModel.isSuccess()) {
                                list = newProductModel.getResults();
                                adapter = new NewProductAdapter(getApplicationContext(), list);
                                recyclerView.setAdapter(adapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server"+ throwable.getMessage(),Toast.LENGTH_LONG).show(); ;
                        }
                )
        );
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView_manager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventEditDelete(EditDeleteEvent event){
        if(event != null){
            productEditDelete = event.getNewProduct();
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            editProduct();
        }else if(item.getTitle().equals("Xóa")){
            deleteProduct();
        }
        return super.onContextItemSelected(item);
    }
//
    private void deleteProduct() {
        compositeDisposable.add(apiBanHang.deleteProduct(productEditDelete.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess()) {
                                Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                getNewProduct();
                            } else {
                                Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Log.d("log", throwable.getMessage());
                        }
                ));
    }

    private void editProduct() {
        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        startActivity(intent);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        Control();
        ActionToolBar();
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarManager.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Control() {
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Mapping() {
        addProduct = findViewById(R.id.add_product);
        updateProduct = findViewById(R.id.update_product);
        deleteProduct = findViewById(R.id.delete_product);
        toolbarManager = findViewById(R.id.toolbar_manager);


    }
}