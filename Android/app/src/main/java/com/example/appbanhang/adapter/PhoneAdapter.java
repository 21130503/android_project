package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class PhoneAdapter  extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder>{
    Context context;
    List<Product> listProduct;

    public PhoneAdapter(Context context, List<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.MyViewHolder holder, int position) {
        Product product = listProduct.get(position);
        holder.name.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText("Giá "+ decimalFormat.format(product.getPrice())+ "VNĐ");
        holder.description.setText(product.getDescription());
        Glide.with(context).load(product.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView name, price,description;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.item_phone_price);
            name = itemView.findViewById(R.id.item_phone_name);
            image = itemView.findViewById(R.id.item_phone_image);
            description = itemView.findViewById(R.id.item_phone_description);
        }
    }
}
