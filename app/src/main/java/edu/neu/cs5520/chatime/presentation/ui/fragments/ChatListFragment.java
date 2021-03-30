package edu.neu.cs5520.chatime.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.network.FirebaseUserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ChatListPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.HomePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.ChatListPresenterImpl;
import edu.neu.cs5520.chatime.presentation.presenters.impl.HomePresenterImpl;
import edu.neu.cs5520.chatime.presentation.ui.activities.ChatActivity;
import edu.neu.cs5520.chatime.presentation.ui.adapters.ChatListAdapter;
import edu.neu.cs5520.chatime.presentation.ui.adapters.ChatMessageAdapter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.ChatListViewModel;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.MessageViewModel;
import edu.neu.cs5520.chatime.storage.FirebaseChatroomRepository;
import edu.neu.cs5520.chatime.storage.FirebaseTopicRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

public class ChatListFragment extends Fragment implements ChatListPresenter.View {

    @BindView(R.id.recycler_chat_list)
    RecyclerView mChatListRecycler;

    private ChatListPresenter mPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, root);

        // create a presenter for this view
        mPresenter = new ChatListPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new FirebaseChatroomRepository(),
                new FirebaseUserRepository()
        );
        return root;
    }

    @Override
    public void setupAdapter(ChatListAdapter mAdapter) {
        mChatListRecycler.setAdapter(mAdapter);
        mChatListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void enterChatRoom(String roomId) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(getString(R.string.current_chat_room_id), roomId);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}