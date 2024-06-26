package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Cart;
import com.example.appbanhang.model.Product;
import com.example.appbanhang.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
        TextView nameProduct , priceProduct, descriptionProduct;
        Button btn;
        ImageView image;
        Spinner spinner;
        Toolbar toolbar;
        Product product;
        NotificationBadge bage;
        FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
            }
        });
    }

    private void addCart() {
        if(Utils.carts.size()> 0){
            boolean flag = false;
            int count = Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i= 0 ; i< Utils.carts.size(); i++){
                if(Utils.carts.get(i).getIdProduct() == product.getId()){
                    Utils.carts.get(i).setCount(count + Utils.carts.get(i).getCount());
                    flag = true;
                }
            }
            if(flag == false){
                long price = Long.parseLong(String.valueOf(product.getPrice()));
                Cart cart = new Cart();
                cart.setPriceProduct(price);
                cart.setCount(count);
                cart.setIdProduct(product.getId());
                cart.setNameProduct(product.getName());
                cart.setImgProduct(product.getImage());
                Utils.carts.add(cart);
            }

        }
        else{
            int count = Integer.parseInt(spinner.getSelectedItem().toString());
            long price =Long.parseLong(String.valueOf(product.getPrice())) ;
            Cart cart = new Cart();
            cart.setCount(count);
            cart.setIdProduct(product.getId());
            cart.setImgProduct(product.getImage());
            cart.setNameProduct(product.getName());
            cart.setPriceProduct(price);
            Utils.carts.add(cart);
        }
        int totalItem = 0;
        for (int i=0; i< Utils.carts.size();i++){
            totalItem = totalItem + Utils.carts.get(i).getCount();
        }
        bage.setText(String.valueOf(totalItem));

    }

    private void initData() {
         product = (Product) getIntent().getSerializableExtra("detail");
        nameProduct.setText(product.getName());
        descriptionProduct.setText(product.getDescription());
//        Glide.with(getApplicationContext()).load(product.getImage()).into(image);
        if (product.getImage().contains("http")){

            Glide.with(getApplicationContext()).load(product.getImage()).into(image);

        }else{
            String img = Utils.BASR_URL+"uploads/"+product.getImage();
            Glide.with(getApplicationContext()).load(img).into(image);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        priceProduct.setText("Giá "+ decimalFormat.format(product.getPrice())+ "VNĐ");
        Integer[] count = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,count);
        spinner.setAdapter(adapter);
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

    private void initView() {
        nameProduct = findViewById(R.id.name_product);
        priceProduct = findViewById(R.id.price_product);
        descriptionProduct = findViewById(R.id.description_product);
        btn  = findViewById(R.id.btn_add_cart);
        spinner = findViewById(R.id.spinner);
        image = findViewById(R.id.detail_img);
        toolbar = findViewById(R.id.toolbar_detail);
        bage = findViewById(R.id.menu_count);
        frameLayout = findViewById(R.id.frameCart);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });
        if(Utils.carts != null) {
            int totalItem = 0;
            for (int i=0; i< Utils.carts.size();i++){
                totalItem = totalItem + Utils.carts.get(i).getCount();
            }
            bage.setText(String.valueOf(totalItem));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.carts != null) {
            int totalItem = 0;
            for (int i=0; i< Utils.carts.size();i++){
                totalItem = totalItem + Utils.carts.get(i).getCount();
            }
            bage.setText(String.valueOf(totalItem));
        }
    }
}