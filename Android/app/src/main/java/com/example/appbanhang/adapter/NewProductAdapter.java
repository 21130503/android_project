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
import com.example.appbanhang.model.NewProduct;

import java.text.DecimalFormat;
import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.MyViewAdapter> {
    Context context;
    List<NewProduct> listNewProduct;

    public NewProductAdapter(Context context, List<NewProduct> listNewProduct) {
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
        NewProduct newProduct = listNewProduct.get(position);
        holder.name.setText(newProduct.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText("Giá "+ decimalFormat.format(newProduct.getPrice())+ "VNĐ");
        Glide.with(context).load(newProduct.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listNewProduct.size();
    }

    public class MyViewAdapter  extends  RecyclerView.ViewHolder{
        TextView price , name;
        ImageView image;

        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price_new_product);
            name = itemView.findViewById(R.id.name_new_product);
            image = itemView.findViewById(R.id.image_new_product);
        }
    }
}
