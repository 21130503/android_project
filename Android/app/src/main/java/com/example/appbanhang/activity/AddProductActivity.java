package com.example.appbanhang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import android.Manifest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.appbanhang.adapter.TypeProductAdapter;
import com.example.appbanhang.databinding.ActivityAddProductBinding;
import com.example.appbanhang.model.ProductModel;
import com.example.appbanhang.model.TypeProduct;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Callback;

public class AddProductActivity extends AppCompatActivity {
    private static final String TAG = "AddProductActivity";

    private static final int MY_REQUEST_CODE = 10;

    APIBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<TypeProduct> typeProducts;
    int type = 0;
    private ActivityAddProductBinding binding;
    String mediaPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo View Binding
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());

        // Thiết lập nội dung cho Activity sử dụng root view từ binding
        setContentView(binding.getRoot());

        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        typeProducts = new ArrayList<>();
        initContro();
        getTypeProduct();
    }

    private void initContro() {
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_ten = binding.name.getText().toString().trim();
                String str_gia = binding.price.getText().toString().trim();
                String str_hinhAnh = binding.imageName.getText().toString().trim();
                String str_moTa = binding.description.getText().toString().trim();

                if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) ||
                    TextUtils.isEmpty(str_moTa) || TextUtils.isEmpty(str_hinhAnh) || type == 0){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();

                }else {
                    compositeDisposable.add(apiBanHang.addProduct(str_ten, str_gia, str_hinhAnh, str_moTa, (type))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    ProductModel -> {
                                        if (ProductModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), ProductModel.getMessage(), Toast.LENGTH_LONG).show();

                                        }else {
                                            Toast.makeText(getApplicationContext(), ProductModel.getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                            ));
                }

            }
        });


        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddProductActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(MY_REQUEST_CODE);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            binding.showImage.setImageURI(uri); // Gắn ảnh vào ImageView
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
        mediaPath = data.getDataString();
        uploadFile();
        Log.d(TAG, "onActivityResult: "+ mediaPath);
    }
    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null);
        if (cursor == null){
            result = uri.getPath();
        }else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;

    }
    public void getTypeProduct(){
        compositeDisposable.add(apiBanHang.getTypeProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        typeProductModel -> {

                            if(typeProductModel.isSuccess()){
                                typeProducts = typeProductModel.getResults();

                                List<String> stringList = new ArrayList<>();
                                stringList.add("Chọn loại sản phẩm");
                                for (TypeProduct typeProduct: typeProducts){
                                    stringList.add(typeProduct.getName());
                                }
                                Toast.makeText(getApplicationContext(), stringList.get(1), Toast.LENGTH_SHORT).show();
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
                                binding.type.setAdapter(adapter);
                                binding.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        type = position;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }else {
                                        Toast.makeText(getApplicationContext(), typeProductModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                ));
    }


    private void uploadFile() {
        Uri uri = Uri.parse(mediaPath);
        File file = new File(getPath(uri));
        // Parsing any Media type file      
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<ProductModel> call =  apiBanHang.uploadFile(fileToUpload);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, retrofit2.Response<ProductModel> response) {
                ProductModel serverResponse = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    if (serverResponse.isSuccess()) {
                        binding.imageName.setText(serverResponse.getName());
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("UploadFile", "Response unsuccessful or body null");
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }


        });
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}