package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Product;
import com.example.appbanhang.utils.Utils;

import java.util.List;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.MyViewHolder> {
    Context context;
    List<Product> productList;

    public DetailOrderAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public DetailOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailOrderAdapter.MyViewHolder holder, int position) {
//        MyViewHolder myViewHolder = (MyViewHolder) holder;
        for (Product product:productList
             ) {
            System.out.println(product.getId());
        }
        Product product = productList.get(position);
        holder.name_order_detail.setText(product.getName());
        holder.quantity_order_detail.setText("Số lượng: " +String.valueOf(product.getCount()));
//        Glide.with(context).load(product.getImage()).into(holder.image_order_detail);
        if (product.getImage().contains("http")){
            Glide.with(context).load(product.getImage()).into(holder.image_order_detail);

        }else{
            String img = Utils.BASR_URL+"uploads/"+product.getImage();
            Glide.with(context).load(img).into(holder.image_order_detail);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_order_detail;
        TextView name_order_detail, quantity_order_detail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_order_detail = itemView.findViewById(R.id.item_image_detail_order);
            name_order_detail = itemView.findViewById(R.id.item_name_order);
            quantity_order_detail = itemView.findViewById(R.id.item_quantity_order);

        }
    }
}
