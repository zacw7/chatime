package edu.neu.cs5520.chatime.presentation.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.MessageViewModel;

@SuppressLint("NonConstantResourceId")
public class ChatMessageAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private final List<MessageViewModel> mItemList;
    private final String mUid;

    public ChatMessageAdapter(String uid, List<MessageViewModel> itemList) {
        mUid = uid;
        mItemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_row_item_message_sent, parent, false);
            return new SentMessageViewHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_row_item_message_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageViewModel message = mItemList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageViewHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageViewHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        MessageViewModel message = mItemList.get(position);

        if (message.getFromUid().equals(mUid)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    public static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_chat_message_received)
        TextView mTextMessage;
        @BindView(R.id.text_chat_date_received)
        TextView mTextDate;
        @BindView(R.id.text_chat_timestamp_received)
        TextView mTextTime;
        @BindView(R.id.text_chat_username_received)
        TextView mTextUsername;
        @BindView(R.id.image_chat_profile_received)
        ImageView mImageProfile;

        ReceivedMessageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(MessageViewModel message) {
            mTextMessage.setText(message.getContent());
            mTextDate.setText(message.getDate());
            mTextTime.setText(message.getTime());
            mTextUsername.setText(message.getFromUsername());
//            mImageProfile.setImageResource(message.getProfileUrl());

            if (message.isDateShowing()) {
                mTextDate.setVisibility(View.VISIBLE);
            } else {
                mTextDate.setVisibility(View.GONE);
            }

            // TODO: Insert the profile image from the URL into the ImageView.
//            Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(),
//            profileImage);
        }
    }

    public static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_chat_message_sent)
        TextView mTextMessage;
        @BindView(R.id.text_chat_date_sent)
        TextView mTextDate;
        @BindView(R.id.text_chat_timestamp_sent)
        TextView mTextTime;

        SentMessageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(MessageViewModel message) {
            mTextMessage.setText(message.getContent());
            mTextDate.setText(message.getDate());
            mTextTime.setText(message.getTime());

            if (message.isDateShowing()) {
                mTextDate.setVisibility(View.VISIBLE);
            } else {
                mTextDate.setVisibility(View.GONE);
            }
        }
    }
}
