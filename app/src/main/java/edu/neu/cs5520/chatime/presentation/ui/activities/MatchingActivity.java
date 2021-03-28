package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.domain.repository.CurrentChatroomIdRepository;
import edu.neu.cs5520.chatime.presentation.presenters.MatchingPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.MatchingPresenterImpl;
import edu.neu.cs5520.chatime.storage.LocalCurrentChatroomIdIdRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

public class MatchingActivity extends AppCompatActivity implements MatchingPresenter.View {

    @BindView(R.id.text_matching)
    TextView mTextMatching;
    @BindView(R.id.pb_matching)
    ProgressBar mProgressBarMatching;

    private MatchingPresenter mPresenter;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        ButterKnife.bind(this);

        mPresenter = new MatchingPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new LocalCurrentChatroomIdIdRepository(this)
        );

        mCountDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mPresenter.checkIfMatched();
                String s = String.valueOf(millisUntilFinished / 1000);
                mTextMatching.setText(s);
            }

            @Override
            public void onFinish() {
                mTextMatching.setText("Done");
                mPresenter.matchTimeout();
            }
        };
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
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtra(getString(R.string.current_chat_room_id), roomId);
//        Bundle extras = intent.getExtras();
//        startActivity(intent);
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
    public void showError(String message) {

    }
}