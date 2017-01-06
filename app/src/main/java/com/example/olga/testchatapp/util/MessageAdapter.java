package com.example.olga.testchatapp.util;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olga.testchatapp.R;
import com.example.olga.testchatapp.model.ReceivedMessage;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.olga.testchatapp.util.Constants.DATE_FORMAT;

/**
 * Created by olga on 22.12.16.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    public static final int TYPE_ME = 0;
    public static final int TYPE_OTHERS = 1;
    private List<ReceivedMessage> messageList;
    private final String myUserName;
    private String currentEmail;

    public MessageAdapter(List<ReceivedMessage> messageList, String myUserName) {
        this.messageList = messageList;
        this.myUserName = myUserName;
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
        holder.rootContainer.setCardBackgroundColor(messageList.get(position).getUser().getColor());
        holder.userName.setText(messageList.get(position).getUser().getEmail());
        holder.message.setText(messageList.get(position).getMessage());
        holder.time.setText(new SimpleDateFormat(DATE_FORMAT).format(messageList.get(position).getMessageTime()));
    }

    @Override
    public int getItemViewType(int position) {
        currentEmail = messageList.get(position).getUser().getEmail();
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

        public TextView userName;
        public TextView message;
        public TextView time;
        public CardView rootContainer;

        public MessageViewHolder(View itemView) {
            super(itemView);
            rootContainer = (CardView) itemView.findViewById(R.id.rootContainer);
            userName = (TextView) itemView.findViewById(R.id.userName);
            message = (TextView) itemView.findViewById(R.id.messageText);
            time = (TextView) itemView.findViewById(R.id.messageTime);
        }
    }
}
