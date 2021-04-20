package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.network.FirebaseUserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.ProfilePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.ProfilePresenterImpl;
import edu.neu.cs5520.chatime.presentation.ui.fragments.EditProfilePhotoFragment;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.ProfileViewModel;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class ProfileActivity extends AppCompatActivity implements ProfilePresenter.View {

    @BindView(R.id.image_profile)
    CircleImageView mImageProfile;
    @BindView(R.id.text_email)
    TextView mTextEmail;
    @BindView(R.id.text_profile_lv)
    TextView mTextLv;
    @BindView(R.id.text_profile_points)
    TextView mTextPoints;
    @BindView(R.id.field_username)
    EditText mFieldUsername;
    @BindView(R.id.field_about_me)
    EditText mFieldAboutMe;
    @BindView(R.id.button_profile_edit)
    Button mButtonEdit;
    @BindView(R.id.button_profile_submit)
    Button mButtonSubmit;
    @BindView(R.id.pb_profile_points)
    ProgressBar mPbPoints;
    @BindView(R.id.fab_check_in)
    FloatingActionButton mFabCheckIn;
    @BindView(R.id.text_profile_check_in)
    TextView mTextCheckIn;

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
    public void loadUserProfile(ProfileViewModel user) {
        mTextEmail.setText(user.getEmail());
        mFieldUsername.setText(user.getUsername());
        mFieldAboutMe.setText(user.getAbout());
        mTextLv.setText(getString(R.string.fmt_lv, user.getLevel()));
        mTextPoints.setText(getString(R.string.fmt_points, user.getCurPoints()));
        mPbPoints.setProgress(user.getCurPoints());

        if (user.isCheckedIn()) {
            mFabCheckIn.setEnabled(false);
            mFabCheckIn.setFocusable(false);
            mFabCheckIn.setBackgroundTintList(
                    ColorStateList.valueOf(getResources().getColor(R.color.colorSecond)));
            mTextCheckIn.setText(R.string.check_in_already);
        }

        if (user.getPhotoUrl() != null && !user.getPhotoUrl().isEmpty()) {
            StorageReference pictureRef = FirebaseStorage.getInstance().getReferenceFromUrl(
                    user.getPhotoUrl());
            Glide.with(this).load(pictureRef).into(mImageProfile);
        }
    }

    @Override
    public void resetElements() {
        mFieldUsername.setEnabled(false);
        mFieldAboutMe.setEnabled(false);
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
        mFieldAboutMe.setEnabled(true);

        mButtonSubmit.setEnabled(true);
        mButtonSubmit.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_profile_submit)
    public void submitEdit() {
        mButtonSubmit.setEnabled(false);
        mButtonSubmit.setVisibility(View.VISIBLE);

        mPresenter.editProfile(mFieldUsername.getText().toString(),
                mFieldAboutMe.getText().toString());
    }

    @OnClick(R.id.image_profile)
    public void showModalBottomSheetForProfilePhotoUpdate() {
        mEditProfilePhotoFragment.show(getSupportFragmentManager(),
                "edit_profile_photo_dialog_fragment");
    }

    @OnClick(R.id.button_sign_out)
    public void onSignOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> startActivity(new Intent(this, MainActivity.class)));
    }

    @OnClick(R.id.fab_check_in)
    public void dailyCheckIn() {
        mPresenter.dailyCheckIn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void disableEdit() {
        mFieldUsername.setEnabled(false);
        mFieldAboutMe.setEnabled(false);
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