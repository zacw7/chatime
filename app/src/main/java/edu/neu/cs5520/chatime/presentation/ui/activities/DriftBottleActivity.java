package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.presentation.presenters.DriftBottlePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.DriftBottlePresenterImpl;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;
import edu.neu.cs5520.chatime.storage.FirebaseDriftBottleRepository;
import edu.neu.cs5520.chatime.storage.FirebaseStorageRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

public class DriftBottleActivity extends AppCompatActivity implements DriftBottlePresenter.View,
        OnMapReadyCallback {

    @BindView(R.id.text_bottle_content)
    TextView mTextContent;
    @BindView(R.id.text_bottle_info)
    TextView mTextInfo;
    @BindView(R.id.layout_bottle_audio)
    ConstraintLayout mLayoutAudio;
    @BindView(R.id.layout_bottle_photo)
    ConstraintLayout mLayoutPhoto;
    @BindView(R.id.layout_bottle_location)
    ConstraintLayout mLayoutLocation;
    @BindView(R.id.text_bottle_audio_label)
    TextView mTextAudioLabel;
    @BindView(R.id.text_bottle_audio)
    TextView mTextAudio;
    @BindView(R.id.fab_bottle_audio)
    FloatingActionButton mFabAudio;
    @BindView(R.id.image_bottle_photo)
    ImageView mImagePhoto;

    private static final String TAG = "DriftBottleActivity";
    private DriftBottlePresenter mPresenter;
    private MediaPlayer mMediaPlayer;
    private GoogleMap mMap;
    private LatLng mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drift_bottle);
        ButterKnife.bind(this);

        DriftBottleViewModel driftBottleViewModel =
                (DriftBottleViewModel) getIntent().getParcelableExtra(
                        getString(R.string.current_drift_bottle));

        mPresenter = new DriftBottlePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new FirebaseStorageRepository(),
                new FirebaseDriftBottleRepository(),
                driftBottleViewModel
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_json));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (mLocation != null) {
            mMap.addMarker(new MarkerOptions().position(mLocation));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mLocation));
        }

        mMap.setTrafficEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mLayoutLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadContentAndInfo(String content, String username, String datetime) {
        mTextContent.setText(content);
        mTextInfo.setText(getString(R.string.fmt_bottle_info, username, datetime));
    }

    @Override
    public void loadAudioPlayer(Uri uri) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(this, uri);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnCompletionListener(mp -> {
                mFabAudio.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                mTextAudio.setText(R.string.play);
            });
            int duration = mMediaPlayer.getDuration() / 1000;
            mTextAudioLabel.setText(getString(R.string.fmt_bottle_audio_label, duration));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLayoutAudio.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.fab_bottle_audio, R.id.text_bottle_audio})
    public void onAudioButtonClick() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);

            mFabAudio.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            mTextAudio.setText(R.string.play);
        } else {
            mMediaPlayer.start();

            mFabAudio.setImageResource(R.drawable.ic_baseline_stop_24);
            mTextAudio.setText(R.string.stop);
        }
    }

    @Override
    public void loadPhoto(String url) {
        mLayoutPhoto.setVisibility(View.VISIBLE);
        StorageReference pictureRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        Glide.with(this).load(pictureRef).into(mImagePhoto);
//        mImagePhoto.setImageURI(uri);
//        Bitmap myBitmap = BitmapFactory.decodeFile(file);
//        mImagePhoto.setImageBitmap(myBitmap);
    }

    @Override
    public void loadLocation(double latitude, double longitude) {
        mLocation = new LatLng(latitude, longitude);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_bottle_location);
        mapFragment.getView().setClickable(false);
        mapFragment.getMapAsync(this);
    }

    @Override
    public File getCacheFile(String filename) {
        return new File(getCacheDir().getAbsolutePath() + "/" + filename);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }
}