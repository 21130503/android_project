package com.example.appbanhang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanhang.R;
import com.example.appbanhang.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class CheckoutActivity extends AppCompatActivity {
    Button checkoutBtn;
    EditText address;
    TextView totalPrice,email, phoneNumber;
    Toolbar toolbarCheckout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        initControl();
    }

    private void initControl() {
        setSupportActionBar(toolbarCheckout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCheckout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long priceIntent = getIntent().getLongExtra("totalPrice",0);
        totalPrice.setText(String.valueOf(decimalFormat.format(priceIntent)));
        email.setText(Utils.currentUser.getEmail());
        phoneNumber.setText(Utils.currentUser.getPhoneNumber());
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressString = address.getText().toString().trim();
                if(TextUtils.isEmpty(addressString)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();

                }
                else{
                    Log.d("test", new Gson().toJson(Utils.carts));
                }
            }
        });
    }

    public void Mapping(){
        checkoutBtn = findViewById(R.id.checkout_btn);
        address = findViewById(R.id.address_checkout);
        totalPrice = findViewById(R.id.price_checkout);
        email = findViewById(R.id.email_checkout);
        phoneNumber= findViewById(R.id.phoneNumber_checkout);
        toolbarCheckout = findViewById(R.id.toolbar_checkout);
    }
}