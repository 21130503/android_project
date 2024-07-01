package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.Interface.ItemClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.model.EventBus.OrderEvent;
import com.example.appbanhang.model.Order;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class OrderManagerAdapter extends RecyclerView.Adapter<OrderManagerAdapter.MyViewOrder> {
    Context context;
    List<Order> orderList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public OrderManagerAdapter(Context context, List<Order> orderList) {
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
    public OrderManagerAdapter.MyViewOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new MyViewOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderManagerAdapter.MyViewOrder holder, int position) {
        Order order = orderList.get(position);
        holder.order.setText("Đơn hàng: " + String.valueOf(order.getId()));
        holder.status_order.setText("Trạng thái đơn: " + order.getStatus());
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
        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(isLongClick){
                    EventBus.getDefault().postSticky(new OrderEvent(order));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewOrder extends  RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView order, status_order;
        RecyclerView recyclerViewOrder;
        ItemClickListener listener;
        public MyViewOrder(@NonNull View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.id_order);
            recyclerViewOrder = itemView.findViewById(R.id.recycleview_order);
            status_order = itemView.findViewById(R.id.status_order);
            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onClick(v,getAdapterPosition(), true);
            return false;
        }
    }
}