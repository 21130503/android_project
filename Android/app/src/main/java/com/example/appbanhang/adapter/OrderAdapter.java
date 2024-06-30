package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewOrder> {
    Context context;
    List<Order> orderList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new MyViewOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewOrder holder, int position) {
        Order order = orderList.get(position);
        holder.order.setText("Đơn hàng: " + String.valueOf(order.getId()));
        holder.status_order.setText("Trạng thái đơn: " + order.getStatus());
        holder.address_order.setText("Địa chỉ: " + order.getAddress());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerViewOrder.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(order.getProducts().size());
        // adapter order
        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(context.getApplicationContext(), order.getProducts());
        holder.recyclerViewOrder.setLayoutManager(layoutManager);
        holder.recyclerViewOrder.setAdapter(detailOrderAdapter);
        holder.recyclerViewOrder.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewOrder extends  RecyclerView.ViewHolder {
        TextView order, status_order, address_order;

        RecyclerView recyclerViewOrder;
        public MyViewOrder(@NonNull View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.id_order);
            recyclerViewOrder = itemView.findViewById(R.id.recycleview_order);
            status_order = itemView.findViewById(R.id.status_order);
            address_order = itemView.findViewById(R.id.address_order);
        }
    }
}