package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.network.FirebaseUserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ChatPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.ChatPresenterImpl;
import edu.neu.cs5520.chatime.presentation.ui.adapters.ChatMessageAdapter;
import edu.neu.cs5520.chatime.storage.FirebaseMessageRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

public class ChatActivity extends AppCompatActivity implements ChatPresenter.View {

    @BindView(R.id.recycler_chat)
    RecyclerView mMessageRecycler;
    @BindView(R.id.field_chat_message)
    EditText mFieldMessage;

    private ChatPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        mPresenter = new ChatPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                getIntent().getStringExtra(getString(R.string.current_chat_room_id)),
                new FirebaseMessageRepository(),
                new FirebaseUserRepository()
        );
    }

    @Override
    public void setupAdapter(ChatMessageAdapter mMessageAdapter) {
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.button_chat_send)
    public void sendChatMessage() {
        mPresenter.sendMessage(mFieldMessage.getText().toString());
        mFieldMessage.getText().clear();
    }

    @Override
    protected void onResume() {
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