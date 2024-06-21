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
    List<TypeProduct> typeProductList;
    Context context;

    public TypeProductAdapter(List<TypeProduct> typeProductList, Context context) {
        this.typeProductList = typeProductList;
        this.context = context;
    }

    @Override
    public int getCount() {
        // Kiểm tra null trước khi gọi size()
        if (typeProductList != null) {
            return typeProductList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView textNameProduct;
        ImageView imageProduct;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_product, null);
            viewHolder.imageProduct = convertView.findViewById(R.id.item_image);
            viewHolder.textNameProduct = convertView.findViewById(R.id.item_name_product);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textNameProduct.setText(typeProductList.get(position).getName());
        Glide.with(context).load(typeProductList.get(position).getImage()).into(viewHolder.imageProduct);

        return convertView;
    }
}
