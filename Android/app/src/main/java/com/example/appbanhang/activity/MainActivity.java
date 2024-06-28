package com.example.appbanhang.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.NewProductAdapter;
import com.example.appbanhang.adapter.TypeProductAdapter;
import com.example.appbanhang.model.NewProduct;
import com.example.appbanhang.model.NewProductModel;
import com.example.appbanhang.model.Product;
import com.example.appbanhang.model.TypeProduct;
import com.example.appbanhang.model.User;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.retrofit.TypeProductModel;
import com.example.appbanhang.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    TypeProductAdapter typeProductAdapter;
    List<TypeProduct> typeProducts;
    List<Product> listNewProduct;
    NewProductAdapter newProductAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APIBanHang apiBanHang;
    NotificationBadge bage;
    FrameLayout frameLayout;
    ImageView imageSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        Mapping(); //ánh xạ
        getToken();
        ActionBar();
        ActionViewFlipper();
        getToken();
        Paper.init(this);
        if(Paper.book().read("user") !=null){
            User user = Paper.book().read("user");
            Utils.currentUser = user;
        }
        if(isConnected(this)){
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            getTypeProduct();
            getNewProduct();
            getEventClick();
        }
        else {
            Toast.makeText(getApplicationContext(), "Not connected internet", Toast.LENGTH_LONG).show();

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(home);
                        break;
                    case 1:
                        Intent laptop = new Intent(getApplicationContext(), PhoneActivity.class);
                        laptop.putExtra("type",2);

                        startActivity(laptop);
                        break;
                    case 2:
                        Intent phone = new Intent(getApplicationContext(), PhoneActivity.class);
                        phone.putExtra("type",1);

                        startActivity(phone);
                        break;
                    case 3:
                        Intent viewOrder = new Intent(getApplicationContext(), ViewOrder.class);
                        startActivity(viewOrder);
                        break;
                    case 4:
                        if(Utils.currentUser.isAdmin()){
                            Intent viewManager = new Intent(getApplicationContext(), ManagerActivity.class);
                            Toast.makeText(getApplicationContext(), "OK-admin", Toast.LENGTH_LONG).show();
                            startActivity(viewManager);
                            break;
                        }else{
                            Paper.book().delete("user");
                            Paper.book().delete("email");
                            Paper.book().delete("password");
                            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                            FirebaseAuth.getInstance().signOut();
                            startActivity(login);
                            break;
                        }
                    case 5:
                        Paper.book().delete("user");
                        Paper.book().delete("email");
                        Paper.book().delete("password");
                        Toast.makeText(getApplicationContext(), "OK-admin", Toast.LENGTH_LONG).show();

                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login);
                        FirebaseAuth.getInstance().signOut();
                        break;
                }
            }
        });
    }

    public void  getNewProduct(){
        compositeDisposable.add(apiBanHang.getNewProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newProductModel -> {
                            listNewProduct = newProductModel.getResults();

                            System.out.println(listNewProduct);
                            newProductAdapter = new NewProductAdapter(getApplicationContext(), listNewProduct);
                            recyclerViewManHinhChinh.setAdapter(newProductAdapter);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server"+ throwable.getMessage(),Toast.LENGTH_LONG).show(); ;
                        }
                )
        );
    }
    public  void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                if(!TextUtils.isEmpty(s)){
                    compositeDisposable.add(apiBanHang.updateToken(String.valueOf(Utils.currentUser.getId()),s)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    typeProductModel -> {

                                    },
                                    throwable -> {
                                        Log.d("log", throwable.getMessage());
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
                                if(Utils.currentUser.isAdmin()) {
                                    typeProducts.add(new TypeProduct(200, "Quản lí","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRO0TX2jK340clC6Pje4lC4ikd7L8Vzhb091w&s"));

                                }
                                typeProducts.add(new TypeProduct(300, "Đăng xuất","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRO0TX2jK340clC6Pje4lC4ikd7L8Vzhb091w&s"));
//                                typeProducts.add()
                                System.out.println(typeProducts.size());
                                System.out.println(typeProducts);
                                typeProductAdapter = new TypeProductAdapter(typeProducts, getApplicationContext());
                                typeProductAdapter.notifyDataSetChanged();
                                listViewManHinhChinh.setAdapter(typeProductAdapter);

                            }
                        }
                ));
   }


    public void Mapping() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
//        lab8
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById((R.id.drawerlayout));
        bage = findViewById(R.id.menu_count);
        frameLayout = findViewById(R.id.frameCart_main);
        imageSearch = findViewById(R.id.image_search);
//        Khởi tạo list
        typeProducts = new ArrayList<>();
//        Khoi tạo list
        listNewProduct = new ArrayList<>();
//        cart
        if(Utils.carts == null){
            Utils.carts =new ArrayList<>();
        }else{
            int totalItem = 0;
            for (int i=0; i< Utils.carts.size();i++){
                totalItem = totalItem + Utils.carts.get(i).getCount();
            }
            bage.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i=0; i< Utils.carts.size();i++){
            totalItem = totalItem + Utils.carts.get(i).getCount();
        }
        bage.setText(String.valueOf(totalItem));
    }

    // Check connect internet
    public boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi !=null && wifi.isConnected()) || (mobile !=null && mobile.isConnected())){
            return  true;
        }
        return  false;

    }
    public void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    public void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}