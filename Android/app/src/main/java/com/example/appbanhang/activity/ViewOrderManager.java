package com.example.appbanhang.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.OrderAdapter;
import com.example.appbanhang.adapter.OrderManagerAdapter;
import com.example.appbanhang.model.EventBus.OrderEvent;
import com.example.appbanhang.model.Order;
import com.example.appbanhang.retrofit.APIBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewOrderManager extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APIBanHang apiBanHang;
    RecyclerView recyclerView;
    Toolbar toolbar_order;
    String status = "Đang chuẩn bị";
    int status_int = 0;
    Spinner spinner;
    Order order;
    AlertDialog dialog;
    String[] count = new String[]{"Đang chuẩn bị","Đã giao cho đơn vị vận chuyển","Đang chờ nhận", "Đã giao", "Đã hủy" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_order_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Mapping();
        ActionToolBar();
        getOrder(status);
    }
    private void getOrder(String status) {
        compositeDisposable.add(apiBanHang.getViewOrderManager(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            if(orderModel.isSuccess()){
                                OrderManagerAdapter orderAdapter = new OrderManagerAdapter(getApplicationContext(), orderModel.getResults());
                                recyclerView.setAdapter(orderAdapter);
                                Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            // Log the error and show a Toast message
                            throwable.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                )
        );
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_order.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Mapping() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASR_URL).create(APIBanHang.class);
        recyclerView = findViewById(R.id.recyclerview_order);
        toolbar_order= findViewById(R.id.toolbar_view_order);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,count);
        spinner.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = count[position];
                getOrder(status);
                Log.d("SpinnerSelection", "Giá trị đã chọn: " + status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public  void eventOrder(OrderEvent event){
        if(event !=null){
            order = event.getOrder();
            showCustomDialog();
        }
    }

    private void showCustomDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view= layoutInflater.inflate(R.layout.dialog_order, null);
        Spinner spinner1 = view.findViewById(R.id.spinner_dialog);
        Button button = view.findViewById(R.id.btn_dialog);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,count);
        spinner1.setAdapter(adapter);
//        spinner1.setSelection();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status_int = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrder(count[status_int]);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

    }

    private void updateOrder(String s) {
        compositeDisposable.add(apiBanHang.updateStatusOrder(order.getId(), s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        typeProductModel -> {
                            getOrder(status);
                            dialog.dismiss();

                        },
                        throwable -> {
                            // Log the error and show a Toast message
                            throwable.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra: " + throwable.getMessage(), Toast.LENGTH_LONG).show();

                        }
                )
        );
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
}