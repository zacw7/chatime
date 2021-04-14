package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import edu.neu.cs5520.chatime.presentation.presenters.CreateBottlePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.CreateBottlePresenterImpl;
import edu.neu.cs5520.chatime.storage.FirebaseDriftBottleRepository;
import edu.neu.cs5520.chatime.storage.FirebaseStorageRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class CreateBottleActivity extends AppCompatActivity implements
        CreateBottlePresenter.View, OnMapReadyCallback {

    @BindView(R.id.field_bottle_content)
    EditText mFieldBottleContent;
    @BindView(R.id.layout_create_bottle_photo)
    ConstraintLayout mLayoutAddPhoto;
    @BindView(R.id.layout_create_bottle_audio)
    ConstraintLayout mLayoutAddAudio;
    @BindView(R.id.layout_location_picker)
    ConstraintLayout mLayoutLocationPicker;
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
    @BindView(R.id.image_add_location)
    ImageView mImageSendTo;
    @BindView(R.id.image_multiple_receivers)
    ImageView mImageMultipleReceivers;
    @BindView(R.id.fab_add_audio_record)
    FloatingActionButton mFabAudioRecord;
    @BindView(R.id.fab_add_audio_cancel)
    FloatingActionButton mFabAudioCancel;
    @BindView(R.id.fab_add_audio_play)
    FloatingActionButton mFabAudioPlay;
    @BindView(R.id.fab_add_audio_delete)
    FloatingActionButton mFabAudioDelete;
    @BindView(R.id.fab_add_location_cancel)
    FloatingActionButton mFabLocationCancel;
    @BindView(R.id.text_add_audio_record)
    TextView mTextAudioRecord;
    @BindView(R.id.text_add_audio_cancel)
    TextView mTextAudioCancel;
    @BindView(R.id.text_add_audio_play)
    TextView mTextAudioPlay;
    @BindView(R.id.text_add_audio_delete)
    TextView mTextAudioDelete;
    @BindView(R.id.text_add_location)
    TextView mTextLocation;
    @BindView(R.id.button_location_confirm)
    Button mButtonLocationConfirm;
    @BindView(R.id.button_location_clear)
    Button mButtonLocationClear;
    @BindView(R.id.pb_create_drift_bottle)
    ProgressBar mProgressBar;

    private static final String TAG = "CreateBottleActivity";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    private CreateBottlePresenter mPresenter;
    private MediaRecorder mMediaRecorder;
    private MediaPlayer mMediaPlayer;
    private GoogleMap mMap;
    private String mCurrentPhotoPath;
    private String mCurrentAudioPath;
    private boolean mAudioRecording;
    private boolean mAudioPlaying;
    private Marker mMarker;

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
                new FirebaseDriftBottleRepository()
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);

        // Add a marker in Sydney and move the camera
        if (mMarker == null) {
            mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(38, -122)).title("Drop it here!"));
        }

        mMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mMarker.getPosition()));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(true);

        mMap.setOnCameraMoveStartedListener(i -> mMarker.hideInfoWindow());
        mMap.setOnCameraIdleListener(() -> mMarker.showInfoWindow());
        mMap.setOnCameraMoveListener(() -> mMarker.setPosition(mMap.getCameraPosition().target));

        mLayoutLocationPicker.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_add_location)
    public void addLocation() {
        mPresenter.startLocationAdding();
    }

    @OnClick(R.id.button_location_confirm)
    public void onLocationConfirm() {
        mPresenter.addLocation(mMap.getCameraPosition().target);
    }

    @OnClick(R.id.button_location_clear)
    public void onLocationClear() {
        mPresenter.removeLocation();
    }

    @OnClick(R.id.fab_add_location_cancel)
    public void onLocationCancel() {
        mPresenter.cancelLocationAdding();
    }

    @Override
    public void startLocationPicker() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_add_location);
        mapFragment.getMapAsync(this);
        mButtonLocationConfirm.setVisibility(View.VISIBLE);
        mButtonLocationClear.setVisibility(View.GONE);
    }

    @Override
    public void saveLocation(String location) {
        mTextLocation.setText(getString(R.string.fmt_add_location, location));
        mImageSendTo.setColorFilter(getResources().getColor(R.color.blue));

        mButtonLocationConfirm.setVisibility(View.GONE);
        mButtonLocationClear.setVisibility(View.VISIBLE);

        mMap.getUiSettings().setScrollGesturesEnabled(false);
    }

    @Override
    public void clearLocation() {
        mTextLocation.setText(R.string.add_location);
        mImageSendTo.clearColorFilter();

        mButtonLocationConfirm.setVisibility(View.VISIBLE);
        mButtonLocationClear.setVisibility(View.GONE);

        mMap.getUiSettings().setScrollGesturesEnabled(true);
    }

    @Override
    public void closeLocationPicker() {
        mLayoutLocationPicker.setVisibility(View.GONE);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionToRecordAccepted) {
            mCurrentAudioPath = generateAudioFile();
            mPresenter.startAudioAdding();
        } else {
            showError("Permission denied");
        }
    }

    @Override
    public void displayAddedPhoto(Uri uri) {
        mLayoutAddPhoto.setVisibility(View.VISIBLE);
        mImageAddPhoto.setColorFilter(getResources().getColor(R.color.orange));
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

    @Override
    public void startAudioRecorder() {
        mLayoutAddAudio.setVisibility(View.VISIBLE);
        mMediaRecorder = null;
        mFabAudioRecord.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24);
        mFabAudioRecord.setVisibility(View.VISIBLE);
        mTextAudioRecord.setText(R.string.record);
        mTextAudioRecord.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeAudioRecorder() {
        mFabAudioRecord.setVisibility(View.GONE);
        mTextAudioRecord.setVisibility(View.GONE);
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    @Override
    public void startAudioPlayer() {
        mImageAddAudio.setColorFilter(getResources().getColor(R.color.green));
        mLayoutAddAudio.setVisibility(View.VISIBLE);
        mMediaPlayer = null;
        mFabAudioPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        mFabAudioPlay.setVisibility(View.VISIBLE);
        mTextAudioPlay.setText(R.string.play);
        mTextAudioPlay.setVisibility(View.VISIBLE);
        mFabAudioDelete.setVisibility(View.VISIBLE);
        mTextAudioDelete.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeAudioPlayer() {
        mFabAudioPlay.setVisibility(View.GONE);
        mTextAudioPlay.setVisibility(View.GONE);
        mFabAudioDelete.setVisibility(View.GONE);
        mTextAudioDelete.setVisibility(View.GONE);
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void closeAudioSection() {
        mLayoutAddAudio.setVisibility(View.GONE);
    }

    @OnClick(R.id.layout_add_audio)
    public void addAudio() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    @OnClick(R.id.fab_add_audio_cancel)
    public void cancelAudioAdding() {
        mImageAddAudio.clearColorFilter();
        mPresenter.cancelAudioAdding();
    }

    @OnClick(R.id.fab_add_audio_delete)
    public void onAudioDelete() {
        mImageAddAudio.clearColorFilter();
        mPresenter.removeAudio();
    }

    @OnClick(R.id.fab_add_audio_play)
    public void onAudioPlay() {
        if (mAudioPlaying) {
            // stop
            mMediaPlayer.release();
            mMediaPlayer = null;

            mFabAudioPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            mTextAudioPlay.setText(R.string.play);
        } else {
            // start
            mMediaPlayer = new MediaPlayer();

            try {
                mMediaPlayer.setDataSource(mCurrentAudioPath);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                Log.e(TAG, "prepare() failed");
            }

            mFabAudioPlay.setImageResource(R.drawable.ic_baseline_stop_24);
            mTextAudioPlay.setText(R.string.stop);
        }
        mAudioPlaying = !mAudioPlaying;
    }

    @OnClick(R.id.fab_add_audio_record)
    public void onAudioRecord() {
        if (mAudioRecording) {
            // stop
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mPresenter.addAudio(Uri.fromFile(new File(mCurrentAudioPath)));
        } else {
            // start
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setOutputFile(mCurrentAudioPath);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                mMediaRecorder.prepare();
            } catch (IOException e) {
                Log.e(TAG, "prepare() failed");
                e.printStackTrace();
            }
            mMediaRecorder.start();
            mFabAudioRecord.setImageResource(R.drawable.ic_baseline_stop_24);
            mTextAudioRecord.setText(R.string.stop);
        }
        mAudioRecording = !mAudioRecording;
    }

    @OnClick(R.id.switch_multiple_receivers)
    public void onMultipleReceiversClick() {
        if (mSwitchMultipleReceivers.isChecked()) {
            mPresenter.setAllowingMultipleReceivers(true);
            mImageMultipleReceivers.setColorFilter(getResources().getColor(R.color.red));
        } else {
            mPresenter.setAllowingMultipleReceivers(false);
            mImageMultipleReceivers.clearColorFilter();
        }
    }

    @OnClick(R.id.button_create_bottle)
    public void onCreateButton() {
        mPresenter.createBottle(mFieldBottleContent.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

    private String generateAudioFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String audioFileName = "/3GP_" + timeStamp + ".3gp";

        return getCacheDir().getAbsolutePath() + audioFileName;
    }
}