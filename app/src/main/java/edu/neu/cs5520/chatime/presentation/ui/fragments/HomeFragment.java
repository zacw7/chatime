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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.network.FirebaseUserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.HomePresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.HomePresenterImpl;
import edu.neu.cs5520.chatime.presentation.ui.activities.CreateBottleActivity;
import edu.neu.cs5520.chatime.presentation.ui.activities.DriftBottleActivity;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;
import edu.neu.cs5520.chatime.storage.FirebaseDriftBottleRepository;
import edu.neu.cs5520.chatime.storage.FirebaseTopicRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends Fragment implements HomePresenter.View, OnMapReadyCallback {

    @BindView(R.id.field_chat_topic)
    EditText mFieldChatTopic;
    @BindView(R.id.button_start_chat)
    Button mButtonStartChat;
    @BindView(R.id.button_pick)
    Button mButtonPick;
    @BindView(R.id.button_home_create_bottle)
    Button mButtonThrow;
    @BindView(R.id.pb_home)
    ProgressBar mProgressBar;

    private static final int NUM_MARKS = 3;
    private static final int RC_SIGN_IN = 123;
    private HomePresenter mPresenter;
    private GoogleMap mMap;
    private Marker mMarker;

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
                new FirebaseUserRepository(),
                new FirebaseDriftBottleRepository()
        );

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_home);
        mapFragment.getView().setClickable(false);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_style_json));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setTrafficEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        Random rand = new Random();
        for (int i = 0; i < NUM_MARKS; i++) {
            int latitude = rand.nextInt(80) - 40;
            int longitude = rand.nextInt(360) - 180;
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(
                    BitmapDescriptorFactory.defaultMarker(213)).title(
                    "Pick it up!"));
        }
    }

    @OnClick(R.id.button_start_chat)
    public void submitTopic() {
        String topic = mFieldChatTopic.getText().toString();
        if (topic.isEmpty()) {
            topic = getString(R.string.default_topic);
        }
        mPresenter.submitTopic(topic);
    }

    @OnClick(R.id.button_home_create_bottle)
    public void launchCreateBottleActivity() {
        Intent intent = new Intent(getActivity(), CreateBottleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_pick)
    public void pickDriftBottle() {
        mPresenter.pickDriftBottle();
    }

    @Override
    public void showCreatingSucceed() {
        Toast.makeText(getActivity(), "showCreatingSucceed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayBottle(DriftBottleViewModel model) {
        Intent intent = new Intent(getActivity(), DriftBottleActivity.class);
        intent.putExtra(getString(R.string.current_drift_bottle), model);
        startActivity(intent);
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
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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