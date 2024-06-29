package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.CartAdapter;
import com.example.appbanhang.model.Cart;
import com.example.appbanhang.model.EventBus.CalcTotalEvent;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView titleCartEmpty , totalPrice;
    Toolbar toolbarCart;
    RecyclerView recyclerView;
    Button buyBtn, incrementBtn, decrementBtn;
    CartAdapter cartAdapter;
    List<Cart> cartList;
    long tottalPrice_calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        Mapping();
        initControl();

        if(Utils.purchases != null){
            Utils.purchases.clear();
        }
        caluclateTotalPrice();
    }

    private void caluclateTotalPrice() {
        tottalPrice_calc  = 0;

        for(int i = 0; i<Utils.purchases.size() ; i++){
            tottalPrice_calc = tottalPrice_calc + Utils.purchases.get(i).getCount()* Utils.purchases.get(i).getPriceProduct();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        totalPrice.setText(decimalFormat.format(tottalPrice_calc));
    }

    private void initControl() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if( Utils.carts == null || Utils.carts.size() ==0 ){
            titleCartEmpty.setVisibility(View.VISIBLE);
        }else{
            cartAdapter = new CartAdapter(getApplicationContext(), Utils.carts);
            recyclerView.setAdapter(cartAdapter);
        }
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                intent.putExtra("totalPrice", tottalPrice_calc);
                startActivity(intent);
            }
        });
    }

    private void Mapping() {
        titleCartEmpty = findViewById(R.id.cart_title_empty);
        toolbarCart = findViewById(R.id.toolbar_cart);
        recyclerView = findViewById(R.id.recycleviewCart);
        totalPrice = findViewById(R.id.total_price);
        buyBtn = findViewById(R.id.btn_buy);
//        incrementBtn = findViewById(R.id.item_cart_increment);
//        decrementBtn = findViewById(R.id.item_cart_decrement);
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
    public void  eventCalcPrice(CalcTotalEvent event){
        if(event != null){
            caluclateTotalPrice();
        }
    }
}