package edu.neu.cs5520.chatime.presentation.ui.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
import edu.neu.cs5520.chatime.presentation.presenters.EditProfilePhotoPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.EditProfilePhotoPresenterImpl;
import edu.neu.cs5520.chatime.storage.FirebaseStorageRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class EditProfilePhotoFragment extends BottomSheetDialogFragment implements
        EditProfilePhotoPresenter.View {

    @BindView(R.id.layout_photo_preview)
    ConstraintLayout mLayoutPreview;
    @BindView(R.id.image_selected_photo)
    ImageView mImageSelected;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    private EditProfilePhotoPresenter mPresenter;
    private String mCurrentPhotoPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_bottm_sheet, container, false);
        ButterKnife.bind(this, root);

        mPresenter = new EditProfilePhotoPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new FirebaseStorageRepository(),
                new FirebaseUserRepository()
        );

        return root;
    }

    @Override
    public void displaySelectedPhoto(Uri uri) {
        mLayoutPreview.setVisibility(View.VISIBLE);
        Glide.with(this).load(uri).into(mImageSelected);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.text_edit_photo_camera)
    public void launchCamera() {
        dispatchTakePictureIntent();
    }

    @OnClick(R.id.text_edit_photo_gallery)
    public void launchGallery() {
        dispatchPickPictureIntent();
    }

    @OnClick(R.id.button_upload_profile_photo)
    public void uploadPhoto() {
        mPresenter.onUploadButtonClick();
    }

    @Override
    public void closeItself() {
        this.dismiss();
        getActivity().recreate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                mPresenter.selectFile(Uri.fromFile(new File(mCurrentPhotoPath)));
            }
            if (requestCode == REQUEST_PICK_IMAGE) {
                mPresenter.selectFile(data.getData());
            }
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
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
        File cacheDir = getActivity().getCacheDir();
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