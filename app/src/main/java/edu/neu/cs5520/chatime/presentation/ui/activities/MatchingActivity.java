package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.presentation.presenters.MatchingPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.MatchingPresenterImpl;
import edu.neu.cs5520.chatime.storage.FirebaseTopicRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class MatchingActivity extends AppCompatActivity implements MatchingPresenter.View {

    @BindView(R.id.text_matching)
    TextView mTextMatching;
    @BindView(R.id.pb_matching)
    ProgressBar mProgressBarMatching;

    private MatchingPresenter mPresenter;
    private CountDownTimer mCountDownTimer;
    private BroadcastReceiver mRoomIdReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        ButterKnife.bind(this);

        mPresenter = new MatchingPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new FirebaseTopicRepository()
        );

        mCountDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String s = String.valueOf(millisUntilFinished / 1000);
                mTextMatching.setText(s);
            }

            @Override
            public void onFinish() {
                mTextMatching.setText(R.string.matching_failed);
                mPresenter.cancel();
            }
        };

        mRoomIdReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                String roomId = intent.getStringExtra("roomId");
                mPresenter.checkRoomId(roomId);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mRoomIdReceiver,
                new IntentFilter("room-id-event"));
    }

    @OnClick(R.id.button_matching_cancel)
    public void onCancel() {
        mPresenter.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void startMatching() {
        mCountDownTimer.start();
    }

    @Override
    public void cancelMatching() {
        mCountDownTimer.cancel();
    }

    @Override
    public void backToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void enterChatRoom(String roomId) {
        hideProgress();
        mTextMatching.setText("Found someone. Chat will be started soon...");
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(getString(R.string.current_chat_room_id), roomId);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        mProgressBarMatching.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBarMatching.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRoomIdReceiver);
        super.onDestroy();
    }
}