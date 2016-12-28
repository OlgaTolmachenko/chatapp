package com.example.olga.testchatapp.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olga.testchatapp.R;
import com.example.olga.testchatapp.model.ReceivedMessage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by olga on 22.12.16.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<ReceivedMessage> messageList;
    private String myUserName;
    private final int TYPE_ME = 0, TYPE_OTHERS = 1;
    private Map userColorMap;
    private String currentEmail;

    public MessageAdapter(List<ReceivedMessage> messageList, String myUserName, Map userColorMap) {
        this.messageList = messageList;
        this.myUserName = myUserName;
        this.userColorMap = userColorMap;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = -1;
        switch (viewType) {
            case TYPE_ME:
                layout = R.layout.message_item_author;
                break;
            case TYPE_OTHERS:
                layout = R.layout.message_item_others;
                break;
        }

        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        itemView.setBackgroundColor(Integer.parseInt(userColorMap.get(currentEmail).toString()));
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
    public int getItemViewType(int position) {
        currentEmail = messageList.get(position).getUserName();
        if (messageList.get(position).getUserName().equals(myUserName)) {
            return TYPE_ME;
        } else {
            return TYPE_OTHERS;
        }
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
