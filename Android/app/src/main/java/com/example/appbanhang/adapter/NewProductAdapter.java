package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.ItemClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.DetailActivity;
import com.example.appbanhang.model.NewProduct;
import com.example.appbanhang.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.MyViewAdapter> {
    Context context;
    List<Product> listNewProduct;

    public NewProductAdapter(Context context, List<Product> listNewProduct) {
        this.context = context;
        this.listNewProduct = listNewProduct;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_product, parent, false);
        return new MyViewAdapter(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        Product newProduct = listNewProduct.get(position);
        holder.name.setText(newProduct.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText("Giá "+ decimalFormat.format(newProduct.getPrice())+ "VNĐ");
        Glide.with(context).load(newProduct.getImage()).into(holder.image);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick ){

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("detail", newProduct);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNewProduct.size();
    }

    public class MyViewAdapter  extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView price , name;
        ImageView image;
        private ItemClickListener itemClickListener;
        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price_new_product);
            name = itemView.findViewById(R.id.name_new_product);
            image = itemView.findViewById(R.id.image_new_product);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(), false);
        }
    }
}
