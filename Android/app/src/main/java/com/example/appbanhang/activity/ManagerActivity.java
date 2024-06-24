package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanhang.R;

import soup.neumorphism.NeumorphCardView;

public class ManagerActivity extends AppCompatActivity {
    NeumorphCardView addProduct, updateProduct, deleteProduct;
    Toolbar toolbarManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manager);
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