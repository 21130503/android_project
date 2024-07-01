package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context ;
    private List<ChatMessage> chatMessageList;
    private  String sendId;
    private  static  final  int TYPE_SEND = 1;
    private  static  final  int TYPE_RECEICVE = 2;

    public ChatAdapter(Context context, List<ChatMessage> chatMessageList, String sendId) {
        this.context = context;
        this.chatMessageList = chatMessageList;
        this.sendId = sendId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_SEND){
            view = LayoutInflater.from(context).inflate(R.layout.item_send_chat, parent, false);
            return new SendMessViewHolder(view);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.item_reciver_chat, parent,false);
            return  new ReceivedViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_SEND){
            ((SendMessViewHolder) holder).textMess.setText(chatMessageList.get(position).mess);
            ((SendMessViewHolder) holder).txtTime.setText(chatMessageList.get(position).dateTime);
        }else {
            ((ReceivedViewHolder) holder).textReceived.setText(chatMessageList.get(position).mess);
            ((ReceivedViewHolder) holder).timeReceived.setText(chatMessageList.get(position).dateTime);

        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (chatMessageList.get(position).sendId.equals(sendId)) {
            return TYPE_SEND;
        } else {
            return TYPE_RECEICVE;
        }
    }

    class  SendMessViewHolder extends RecyclerView.ViewHolder{
        TextView textMess, txtTime;

        public SendMessViewHolder(@NonNull View itemView) {
            super(itemView);
            textMess = itemView.findViewById(R.id.text_send);
            txtTime = itemView.findViewById(R.id.time_send);

        }
    }
    class  ReceivedViewHolder extends  RecyclerView.ViewHolder{
        TextView textReceived , timeReceived;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            textReceived = itemView.findViewById(R.id.text_received);
            timeReceived = itemView.findViewById(R.id.time_received);
        }
    }
}
