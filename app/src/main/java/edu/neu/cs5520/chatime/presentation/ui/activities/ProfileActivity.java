package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.domain.model.User;
import edu.neu.cs5520.chatime.network.FirebaseUserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ProfilePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.ProfilePresenterImpl;
import edu.neu.cs5520.chatime.presentation.ui.fragments.EditProfilePhotoFragment;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class ProfileActivity extends AppCompatActivity implements ProfilePresenter.View {

    @BindView(R.id.image_profile)
    CircleImageView mImageProfile;
    @BindView(R.id.field_username)
    EditText mFieldUsername;
    @BindView(R.id.field_email)
    EditText mFieldEmail;
    @BindView(R.id.button_profile_edit)
    Button mButtonEdit;
    @BindView(R.id.button_profile_submit)
    Button mButtonSubmit;

    private ProfilePresenter mPresenter;
    private EditProfilePhotoFragment mEditProfilePhotoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mPresenter = new ProfilePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new FirebaseUserRepository()
        );
        mEditProfilePhotoFragment = new EditProfilePhotoFragment();
    }

    @Override
    public void loadUserProfile(User user) {
        mFieldUsername.setText(user.getUsername());
        mFieldEmail.setText(user.getEmail());
        if (user.getPictureUrl() != null && !user.getPictureUrl().isEmpty()) {
            StorageReference pictureRef = FirebaseStorage.getInstance().getReferenceFromUrl(
                    user.getPictureUrl());
            Glide.with(this).load(pictureRef).into(mImageProfile);
        }
    }

    @Override
    public void resetElements() {
        mFieldUsername.setEnabled(false);
        mFieldEmail.setEnabled(false);
        mButtonEdit.setEnabled(true);
        mButtonEdit.setVisibility(View.VISIBLE);
        mButtonSubmit.setEnabled(false);
        mButtonSubmit.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_profile_edit)
    public void editProfile() {
        mButtonEdit.setVisibility(View.INVISIBLE);
        mButtonEdit.setEnabled(false);

        mFieldUsername.setEnabled(true);

        mButtonSubmit.setEnabled(true);
        mButtonSubmit.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_profile_submit)
    public void submitEdit() {
        mButtonSubmit.setEnabled(false);
        mButtonSubmit.setVisibility(View.VISIBLE);

        mPresenter.editProfile(mFieldUsername.getText().toString());
    }

    @OnClick(R.id.image_profile)
    public void showModalBottomSheetForProfilePhotoUpdate() {
        mEditProfilePhotoFragment.show(getSupportFragmentManager(),
                "edit_profile_photo_dialog_fragment");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void disableEdit() {
        mFieldUsername.setEnabled(false);
        mFieldEmail.setEnabled(false);
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