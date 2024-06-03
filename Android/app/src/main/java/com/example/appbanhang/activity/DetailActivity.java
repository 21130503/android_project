package com.example.appbanhang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.appbanhang.model.Product;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
        TextView nameProduct , priceProduct, descriptionProduct;
        Button btn;
        ImageView image;
        Spinner spinner;
        Toolbar toolbar;
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
    }

    private void initData() {
        Product product = (Product) getIntent().getSerializableExtra("detail");
        nameProduct.setText(product.getName());
        descriptionProduct.setText(product.getDescription());
        Glide.with(getApplicationContext()).load(product.getImage()).into(image);
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
    }
}