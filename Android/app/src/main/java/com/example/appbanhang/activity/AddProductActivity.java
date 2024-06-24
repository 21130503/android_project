package com.example.appbanhang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddProductActivity extends AppCompatActivity {
    private static final String TAG = "AddProductActivity";

    private static final int MY_REQUEST_CODE = 10;

    APIBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<TypeProduct> typeProducts;
    int type = 0;
    private ActivityAddProductBinding binding;

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
                String str_hinhAnh = binding.showImage.toString().trim();
                String str_moTa = binding.description.getText().toString().trim();

                if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) ||
                    TextUtils.isEmpty(str_moTa) || TextUtils.isEmpty(str_hinhAnh) || type == 0){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();

                }else {
                    compositeDisposable.add(apiBanHang.addProduct(str_ten, str_gia, str_hinhAnh, str_moTa, (type-1))
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

//    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Log.e(TAG, "onActivityResult");
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        if (data != null) {
//                            Uri selectedImageUri = data.getData();
//                            loadImage(selectedImageUri);
//                        }
//
//                    }
//                }
//            }
//    );

//    private void loadImage(Uri uri) {
//        Glide.with(this)
//                .load(uri)
//                .apply(new RequestOptions()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE) // Tắt cache để lấy ảnh mới nhất
//                        .skipMemoryCache(true) // Bỏ qua bộ nhớ cache
//                )
//                .into(binding.showImage);
//    }



//    private void initContro() {
//        binding.type.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
////                builder.setTitle("Choose an item");
//
////                Observable<TypeProductModel> typeProduct = apiBanHang.getTypeProduct();
////                String[] items = typeProduct.set
////                builder.setItems(items, (dialog, which) -> {
////                    switch (which) {
////                        case 0:
////                            type.setText("Item 1");
////                            break;
////                        case 1:
////                            type.setText("Item 2");
////                            break;
////                        case 2:
////                            type.setText("Item 3");
////                            break;
////                    }
////                });
//
//
////                AlertDialog dialog = builder.create();
////                dialog.show();
//                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
//                compositeDisposable.add(apiBanHang.getTypeProduct()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                typeProductModel -> {
//                                    Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
//                                    if (typeProductModel.isSuccess()) {
//                                        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
//                                        typeProducts = typeProductModel.getResults();
//                                        showArrayListDialog(AddProductActivity.this, typeProducts);
//
//                                    }else {
//                                        Toast.makeText(getApplicationContext(), typeProductModel.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                },
//                                throwable -> {
//                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                        ));
//            }
//        });
//
//        binding.chooseImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickRequestPermission();
//            }
//        });
//    }
//    public void showArrayListDialog(Context context, List<TypeProduct> arrayList) {
//        // Chuyển đổi ArrayList thành mảng đơn giản của chuỗi
//         String[] items = new String[arrayList.size()];
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Danh sách các mục");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                // Xử lý sự kiện khi một mục được chọn
//                String selectedText = items[item].toString();
//                // Thực hiện các hành động phù hợp với mục đã chọn
//                // Ví dụ: hiển thị thông báo, thực hiện hành động liên quan đến mục này
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
//    private void onClickRequestPermission() {
////        Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
////        // kiểm tra phiên bản nếu nó nhỏ hơn android 6 thì khỏi xin cấp phép
////        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
////            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
////            openGallery();
////            return;
////        }
////        // cấp phép thành công
////        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
////            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
////            openGallery();
////        }else {
////            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
////            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
////            requestPermissions(permission, MY_REQUEST_CODE);
////        }
//        // Kiểm tra phiên bản Android, nếu nhỏ hơn 6.0 thì không cần xin cấp phép
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            openGallery();
//            return;
//        }
//
//        // Kiểm tra quyền hiện tại
//        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            openGallery();
//        } else {
//            // Nếu chưa có quyền, yêu cầu cấp phép
//            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//            requestPermissions(permissions, MY_REQUEST_CODE);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_REQUEST_CODE){
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                openGallery();
//            }else {
//                openGallery();
//                // Quyền bị từ chối
//                Toast.makeText(this, "Quyền bị từ chối", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        if (requestCode == MY_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openGallery();
//            } else {
//                openGallery();
////                // Quyền bị từ chối
////                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
////                    // Người dùng từ chối cấp phép nhưng không chọn "Don't ask again"
////                    Toast.makeText(this, "Quyền bị từ chối. Vui lòng cấp quyền để tiếp tục.", Toast.LENGTH_SHORT).show();
////                } else {
////                    // Người dùng từ chối cấp phép và chọn "Don't ask again"
////                    showPermissionDeniedDialog();
////                }
//            }
//        }
//    }

//    private void showPermissionDeniedDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("Quyền truy cập bị từ chối")
//                .setMessage("Ứng dụng cần quyền truy cập bộ nhớ để hoạt động. Vui lòng vào phần cài đặt và cấp quyền.")
//                .setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Mở cài đặt ứng dụng để người dùng có thể cấp quyền
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Đóng dialog nếu người dùng chọn hủy
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }

//    private void openGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Image"));
//
//    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}