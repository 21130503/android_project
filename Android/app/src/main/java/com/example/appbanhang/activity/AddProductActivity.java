package com.example.appbanhang.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.databinding.ActivityAddProductBinding;
import com.example.appbanhang.model.Product;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import soup.neumorphism.NeumorphCardView;

public class AddProductActivity extends AppCompatActivity {
    Spinner spinner;
    int type =0;
    Product productEdit;
    ActivityAddProductBinding binding;
    APIBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        initView();
        initData();
        Intent intent = getIntent();
        productEdit = (Product) intent.getSerializableExtra("sua");
        if(productEdit == null){
            flag = false;
        } else {
            flag = true;
            binding.btnproduct.setText("Sửa Sản Phẩm");
            binding.description.setText(productEdit.getDescription());
            binding.price.setText(productEdit.getPrice()+"");
            binding.nameProduct.setText(productEdit.getName());
            binding.img.setText(productEdit.getImage());
            binding.spinnerType.setSelection(productEdit.getType());
        }

    }

    private void initView() {
        List<String> list = new ArrayList<>();
        list.add("Chọn loại");
        list.add("Loại 1");
        list.add("Loại 2");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(flag == false){
                   addProduct();
               } else {
                   editProduct();
               }
            }
        });
    }

    private void editProduct() {
        String str_name = binding.nameProduct.getText().toString().trim();
        String str_price = binding.price.getText().toString().trim();
        String str_description = binding.description.getText().toString().trim();
        String str_img = binding.img.getText().toString().trim();
        if(TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_price) || TextUtils.isEmpty(str_description) || TextUtils.isEmpty(str_img) || type == 0 ){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiBanHang.editProduct(str_name, str_price, str_img, str_description, type, productEdit.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    )
            );
        }
    }

    private void addProduct() {
        String str_name = binding.nameProduct.getText().toString().trim();
        String str_price = binding.price.getText().toString().trim();
        String str_description = binding.description.getText().toString().trim();
        String str_img = binding.img.getText().toString().trim();
        if(TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_price) || TextUtils.isEmpty(str_description) || TextUtils.isEmpty(str_img) || type == 0 ){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiBanHang.isertProduct(str_name, str_price, str_img, str_description, (type))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    )
            );
        }
    }

    private void initData() {
        spinner = findViewById(R.id.spinner_type);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}