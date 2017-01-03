package com.example.olga.testchatapp.util;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olga.testchatapp.R;
import com.example.olga.testchatapp.model.ReceivedMessage;
import com.example.olga.testchatapp.model.User;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static com.example.olga.testchatapp.util.Constants.CURRENT_USER;
import static com.example.olga.testchatapp.util.Constants.DATE_FORMAT;

/**
 * Created by olga on 22.12.16.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    public static final int TYPE_ME = 0;
    public static final int TYPE_OTHERS = 1;
    private List<ReceivedMessage> messageList;
    private String myUserName;
    private HashMap<String, User> userColorMap;
    private String currentEmail;

    public MessageAdapter(List<ReceivedMessage> messageList, String myUserName, HashMap<String, User> userColorMap) {
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
        MessageViewHolder messageVH = new MessageViewHolder(itemView);
        return messageVH;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        holder.itemViewChild.setBackgroundColor((userColorMap.get(CURRENT_USER).getColor()));
        holder.itemViewChild.setBackgroundResource(R.drawable.rounded_corners);
        holder.userName.setText(messageList.get(position).getUserName());
        holder.message.setText(messageList.get(position).getMessage());
        holder.time.setText(new SimpleDateFormat(DATE_FORMAT).format(messageList.get(position).getMessageTime()));
    }

    @Override
    public int getItemViewType(int position) {
        currentEmail = messageList.get(position).getUserName();
        if (currentEmail.equals(myUserName)) {
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

        public View itemViewChild;
        public TextView userName;
        public TextView message;
        public TextView time;
        public GradientDrawable drawable;

        public MessageViewHolder(View itemView) {
            super(itemView);
            itemViewChild = itemView.findViewById(R.id.messageLayout);
            userName = (TextView) itemViewChild.findViewById(R.id.userName);
            message = (TextView) itemViewChild.findViewById(R.id.messageText);
            time = (TextView) itemViewChild.findViewById(R.id.messageTime);
        }
    }
}
