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
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;

@SuppressLint("NonConstantResourceId")
public class BottleListAdapter extends RecyclerView.Adapter {

    private final List<DriftBottleViewModel> mItemList;
    private OnItemClickListener mOnItemClickListener;

    public BottleListAdapter(List<DriftBottleViewModel> itemList,
            OnItemClickListener onItemClickListener) {
        mItemList = itemList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_row_item_bottle, parent, false);
        return new BottleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DriftBottleViewModel model = mItemList.get(position);
        if (position == mItemList.size() - 1) {
            ((BottleItemViewHolder) holder).bindLast(model);
        } else {
            ((BottleItemViewHolder) holder).bind(model);
        }
        ((BottleItemViewHolder) holder).bindListener(model, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    /**
     * Interface definition for a callback to be invoked when a row item is clicked.
     */
    public interface OnItemClickListener {
        void onItemClick(DriftBottleViewModel item);
    }

    public static class BottleItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_bottle_list_picked_at)
        TextView mTextPickedAt;
        @BindView(R.id.text_bottle_list_from)
        TextView mTextPickedFrom;
        @BindView(R.id.image_bottle_list_item_audio)
        ImageView mImageAudio;
        @BindView(R.id.image_bottle_list_item_photo)
        ImageView mImagePhoto;
        @BindView(R.id.image_bottle_list_item_location)
        ImageView mImageLocation;
        @BindView(R.id.divider_bottle_list)
        View mViewDivider;

        BottleItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindLast(DriftBottleViewModel model) {
            mViewDivider.setVisibility(View.INVISIBLE);
            bind(model);
        }

        void bind(DriftBottleViewModel model) {
            mTextPickedAt.setText(
                    itemView.getContext().getString(R.string.fmt_picked_at, model.getPickedAt()));
            mTextPickedFrom.setText(
                    itemView.getContext().getString(R.string.fmt_from, model.getCreatorUsername()));
            if (model.getAudioUrl() == null || model.getAudioUrl().isEmpty()) {
                mImageAudio.setVisibility(View.GONE);
            }
            if (model.getPhotoUrl() == null || model.getPhotoUrl().isEmpty()) {
                mImagePhoto.setVisibility(View.GONE);
            }
            if (model.getLatitude() == null || model.getLongitude() == null) {
                mImageLocation.setVisibility(View.GONE);
            }
        }

        public void bindListener(final DriftBottleViewModel model,
                final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(model));
        }
    }
}
