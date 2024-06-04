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
import com.example.appbanhang.Interface.IImageClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Cart;
import com.example.appbanhang.model.EventBus.CalcTotalEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> carts;

    public CartAdapter(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.item_cart_name.setText(cart.getNameProduct());
        holder.item_cart_quantity.setText(String.valueOf(cart.getCount()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_cart_price.setText(decimalFormat.format(cart.getPriceProduct()) + "VNĐ");
        Glide.with(context).load(cart.getImgProduct()).into(holder.item_cart_image);
        long price = cart.getCount() * cart.getPriceProduct();
        holder.item_cart_price_2.setText(decimalFormat.format(price) +"VNĐ");
        holder.setListener(new IImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int value) {
                if(value == 1) {
                    if(carts.get(pos).getCount() > 1){
                        int new_quantity = carts.get(pos).getCount() -1;
                        carts.get(pos).setCount(new_quantity);
                    }
                }
                else if(value==2){
                    if(carts.get(pos).getCount() <11){
                        int new_quantity = carts.get(pos).getCount() +1;
                        carts.get(pos).setCount(new_quantity);
                    }
                }
                holder.item_cart_quantity.setText(String.valueOf(carts.get(pos).getCount()));
                long price = carts.get(pos).getCount() * carts.get(pos).getPriceProduct();
                holder.item_cart_price_2.setText(decimalFormat.format(price) +"VNĐ");
                EventBus.getDefault().postSticky(new CalcTotalEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class MyViewHolder  extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_cart_image, item_cart_decrement,item_cart_increment;
        TextView item_cart_name,item_cart_price,item_cart_quantity,item_cart_price_2;
        IImageClickListener listener;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cart_image = itemView.findViewById(R.id.item_cart_image);
            item_cart_name = itemView.findViewById(R.id.item_cart_name);
            item_cart_price = itemView.findViewById(R.id.item_cart_price);
            item_cart_quantity = itemView.findViewById(R.id.item_cart_quantity);
            item_cart_price_2 = itemView.findViewById(R.id.item_cart_price_2);
            item_cart_increment = itemView.findViewById(R.id.item_cart_increment);
            item_cart_decrement = itemView.findViewById(R.id.item_cart_decrement);
            item_cart_increment.setOnClickListener(this);
            item_cart_decrement.setOnClickListener(this);
        }
        public void setListener(IImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if(v == item_cart_decrement ){
                listener.onImageClick(itemView, getAdapterPosition(), 1);
            }
            if(v == item_cart_increment ){
                listener.onImageClick(itemView, getAdapterPosition(), 2);
            }
        }
    }
}
