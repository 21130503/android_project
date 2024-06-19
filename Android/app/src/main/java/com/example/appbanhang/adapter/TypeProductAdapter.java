package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.TypeProduct;

import java.util.List;

public class TypeProductAdapter extends BaseAdapter {
    private List<TypeProduct> typeProductList;
    private Context context;

    public TypeProductAdapter(List<TypeProduct> typeProductList, Context context) {
        this.typeProductList = typeProductList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return typeProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textNameProduct = convertView.findViewById(R.id.item_name_product);
            viewHolder.imageProduct = convertView.findViewById(R.id.item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TypeProduct typeProduct = typeProductList.get(position);
        viewHolder.textNameProduct.setText(typeProduct.getName());
        Glide.with(context).load(typeProduct.getImage()).into(viewHolder.imageProduct);

        return convertView;
    }

    static class ViewHolder {
        TextView textNameProduct;
        ImageView imageProduct;
    }
}
