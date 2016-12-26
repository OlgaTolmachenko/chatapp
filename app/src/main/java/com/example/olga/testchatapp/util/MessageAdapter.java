package com.example.olga.testchatapp.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olga.testchatapp.R;
import com.example.olga.testchatapp.model.Message;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by olga on 22.12.16.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Message> messageList;
    private int author;

    public MessageAdapter(List<Message> messageList, int author) {
        this.messageList = messageList;
        this.author = author;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int author) {
        int layout = -1;
        switch (author) {
            case 0:
                layout = R.layout.message_item_author;
                break;
            case 1:
                layout = R.layout.message_item_others;
                break;
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        MessageViewHolder messageVH = new MessageViewHolder(itemView);
        return messageVH;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.userName.setText(messageList.get(position).getUserName());
        holder.message.setText(messageList.get(position).getMessage());
        holder.time.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(messageList.get(position).getMessageTime()));
    }

    @Override
    public int getItemCount() {
        if (messageList == null || messageList.isEmpty()) {
            return 0;
        }
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView message;
        public TextView time;

        public MessageViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            message = (TextView) itemView.findViewById(R.id.messageText);
            time = (TextView) itemView.findViewById(R.id.messageTime);
        }
    }
}
