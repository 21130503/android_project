package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class PhoneAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<Product> listProduct;
    private static  final int VIEW_TYPE_DATA = 0 ;
    private static  final  int VIEW_TYPE_lOADING = 1 ;
    public PhoneAdapter(Context context, List<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if(viewType == VIEW_TYPE_DATA){
          View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
          return new MyViewHolder(item);
      }
      else{
          View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
          return new LoadingViewHoler(item);
      }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Product product = listProduct.get(position);
            myViewHolder.name.setText(product.getName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.price.setText("Giá "+ decimalFormat.format(product.getPrice())+ "VNĐ");
            myViewHolder.description.setText(product.getDescription());
            Glide.with(context).load(product.getImage()).into(myViewHolder.image);
        }
        else{
            LoadingViewHoler loadingViewHoler = (LoadingViewHoler) holder;
            loadingViewHoler.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listProduct.get(position) == null ? VIEW_TYPE_lOADING: VIEW_TYPE_DATA;
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
    public class LoadingViewHoler extends  RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHoler(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
}
