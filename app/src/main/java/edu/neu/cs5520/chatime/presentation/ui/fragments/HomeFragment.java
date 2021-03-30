package edu.neu.cs5520.chatime.presentation.ui.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.network.FirebaseUserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.HomePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.HomePresenterImpl;
import edu.neu.cs5520.chatime.storage.FirebaseTopicRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends Fragment implements HomePresenter.View {

    @BindView(R.id.field_chat_topic)
    EditText mFieldChatTopic;
    @BindView(R.id.button_start_chat)
    Button mButtonStartChat;
    @BindView(R.id.button_pick)
    Button mButtonPick;
    @BindView(R.id.button_throw)
    Button mButtonThrow;

    private static final int RC_SIGN_IN = 123;
    private HomePresenter mPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        // create a presenter for this view
        mPresenter = new HomePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new FirebaseTopicRepository(),
                new FirebaseUserRepository()
        );
        return root;
    }

    @OnClick(R.id.button_start_chat)
    public void submitTopic() {
        String topic = mFieldChatTopic.getText().toString();
        if (topic.isEmpty()) {
            topic = getString(R.string.default_topic);
        }
        mPresenter.submitTopic(topic);
    }

    @Override
    public void showCreatingSucceed() {
        Toast.makeText(getActivity(), "showCreatingSucceed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void launchActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent(getActivity(), cls);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(getActivity(),
                        getString(R.string.welcome_fmt, user.getDisplayName()),
                        Toast.LENGTH_LONG).show();
                // ...
            } else {
                Toast.makeText(getActivity(), getString(R.string.sign_in_required),
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}