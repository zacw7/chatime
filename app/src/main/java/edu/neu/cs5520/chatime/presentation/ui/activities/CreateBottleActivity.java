package edu.neu.cs5520.chatime.presentation.ui.activities;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.network.FirebaseUserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.CreateBottlePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.CreateBottlePresenterImpl;
import edu.neu.cs5520.chatime.storage.FirebaseStorageRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class CreateBottleActivity extends AppCompatActivity implements CreateBottlePresenter.View {

    @BindView(R.id.field_bottle_content)
    EditText mFieldBottleContent;
    @BindView(R.id.layout_create_bottle_photo)
    ConstraintLayout mLayoutAddPhoto;
    @BindView(R.id.image_create_bottle_photo)
    ImageView mImageBottlePhoto;
    @BindView(R.id.radio_from_camera)
    RadioButton mRadioFromCamera;
    @BindView(R.id.radio_from_gallery)
    RadioButton mRadioFromGallery;
    @BindView(R.id.switch_multiple_receivers)
    SwitchMaterial mSwitchMultipleReceivers;
    @BindView(R.id.image_add_audio)
    ImageView mImageAddAudio;
    @BindView(R.id.image_add_photo)
    ImageView mImageAddPhoto;
    @BindView(R.id.image_send_to)
    ImageView mImageSendTo;
    @BindView(R.id.image_multiple_receivers)
    ImageView mImageMultipleReceivers;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    private CreateBottlePresenter mPresenter;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bottle);
        ButterKnife.bind(this);
        mPresenter = new CreateBottlePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new FirebaseStorageRepository(),
                new FirebaseUserRepository()
        );
    }

    @OnClick(R.id.layout_add_photo)
    public void addPhoto() {
        if (mRadioFromCamera.isEnabled() && mRadioFromCamera.isChecked()) {
            dispatchTakePictureIntent();
        } else if (mRadioFromGallery.isEnabled() && mRadioFromGallery.isChecked()) {
            dispatchPickPictureIntent();
        }
    }

    @OnClick(R.id.fab_remove_photo)
    public void removePhoto() {
        mPresenter.removePhoto();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                mPresenter.addPhoto(Uri.fromFile(new File(mCurrentPhotoPath)));
            }
            if (requestCode == REQUEST_PICK_IMAGE) {
                mPresenter.addPhoto(data.getData());
            }
        }
    }

    @Override
    public void displayAddedPhoto(Uri uri) {
        mLayoutAddPhoto.setVisibility(View.VISIBLE);
        mImageAddPhoto.setColorFilter(getResources().getColor(R.color.blue));
        Glide.with(this).load(uri).into(mImageBottlePhoto);
        mRadioFromCamera.setEnabled(false);
        mRadioFromGallery.setEnabled(false);
    }

    @Override
    public void hidePhotoPreview() {
        mLayoutAddPhoto.setVisibility(View.GONE);
        mImageAddPhoto.clearColorFilter();
        mImageBottlePhoto.setImageResource(android.R.color.transparent);
        mRadioFromCamera.setEnabled(true);
        mRadioFromGallery.setEnabled(true);
    }

    @OnClick(R.id.layout_add_audio)
    public void recordAudio() {
    }

    @OnClick(R.id.switch_multiple_receivers)
    public void onMultipleReceiversClick() {
        if (mSwitchMultipleReceivers.isChecked()) {
            mImageMultipleReceivers.setColorFilter(getResources().getColor(R.color.orange));
        } else {
            mImageMultipleReceivers.clearColorFilter();
        }
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // TODO
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "edu.neu.cs5520.chatime.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void dispatchPickPictureIntent() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPictureIntent, REQUEST_PICK_IMAGE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File cacheDir = getCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                cacheDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}