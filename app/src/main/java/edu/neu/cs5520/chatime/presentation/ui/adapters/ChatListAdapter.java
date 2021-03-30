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
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.ChatViewModel;

@SuppressLint("NonConstantResourceId")
public class ChatListAdapter extends RecyclerView.Adapter {

    private final List<ChatViewModel> mItemList;
    private OnItemClickListener mOnItemClickListener;

    public ChatListAdapter(List<ChatViewModel> itemList, OnItemClickListener onItemClickListener) {
        mItemList = itemList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_row_item_chat, parent, false);
        return new ChatItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatViewModel model = mItemList.get(position);
        if (position == mItemList.size() - 1) {
            ((ChatItemViewHolder) holder).bindLast(model);
        } else {
            ((ChatItemViewHolder) holder).bind(model);
        }
        ((ChatItemViewHolder) holder).bindListener(model, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    /**
     * Interface definition for a callback to be invoked when a row item is clicked.
     */
    public interface OnItemClickListener {
        void onItemClick(ChatViewModel item);
    }

    public static class ChatItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_chat_recipient)
        TextView mTextChatRecipient;
        @BindView(R.id.text_chat_topic)
        TextView mTextChatTopic;
        @BindView(R.id.image_chat_profile)
        ImageView mImageProfile;
        @BindView(R.id.divider_chat_list)
        View mViewDivider;

        ChatItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindLast(ChatViewModel message) {
            mViewDivider.setVisibility(View.INVISIBLE);
            bind(message);
        }

        void bind(ChatViewModel message) {
            mTextChatRecipient.setText(message.getUsername());
            mTextChatTopic.setText(message.getTopic());
            // TODO
//            mImageProfile.setText(message.getTime());
        }

        public void bindListener(final ChatViewModel model, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(model));
        }
    }
}
