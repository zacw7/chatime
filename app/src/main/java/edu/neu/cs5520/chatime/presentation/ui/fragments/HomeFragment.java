package edu.neu.cs5520.chatime.presentation.ui.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.AuthUI.IdpConfig;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import edu.neu.cs5520.chatime.R;
import java.util.Arrays;
import java.util.List;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends Fragment {

  @BindView(R.id.button_start_chat)
  Button mButtonStartChat;
  @BindView(R.id.button_pick)
  Button mButtonPick;
  @BindView(R.id.button_throw)
  Button mButtonThrow;
  @BindView(R.id.field_chat_key)
  EditText mFieldChatKey;

  private static final int RC_SIGN_IN = 123;
  private FirebaseAuth mAuth;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, root);

    mAuth = FirebaseAuth.getInstance();
    checkSignInStatus();
    return root;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      IdpResponse response = IdpResponse.fromResultIntent(data);

      if (resultCode == RESULT_OK) {
        // Successfully signed in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(getActivity(), getString(R.string.welcome_fmt, user.getDisplayName()),
            Toast.LENGTH_LONG).show();
        // ...
      } else {
        Toast.makeText(getActivity(), getString(R.string.sign_in_required), Toast.LENGTH_LONG)
            .show();
      }
    }
  }

  @OnClick(R.id.button_start_chat)
  public void startChat(View view) {
    Toast.makeText(getActivity(), "Start Chat!", Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.button_pick)
  public void pickBottle(View view) {
    Toast.makeText(getActivity(), "Pick a bottle!", Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.button_throw)
  public void throwBottle(View view) {
    Toast.makeText(getActivity(), "Throw a bottle!!", Toast.LENGTH_SHORT).show();
  }

  private void checkSignInStatus() {
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser == null) {
      // Choose authentication providers
      List<IdpConfig> providers = Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build(),
          new AuthUI.IdpConfig.PhoneBuilder().build());
      // Create and launch sign-in intent
      startActivityForResult(
          AuthUI.getInstance()
              .createSignInIntentBuilder()
              .setAvailableProviders(providers)
              .build(),
          RC_SIGN_IN);
    } else {
      Toast.makeText(getActivity(), getString(R.string.welcome_fmt, currentUser.getDisplayName()),
          Toast.LENGTH_LONG).show();
    }
  }
}