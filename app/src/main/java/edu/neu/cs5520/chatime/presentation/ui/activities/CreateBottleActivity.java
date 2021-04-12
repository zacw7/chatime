package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.neu.cs5520.chatime.R;

@SuppressLint("NonConstantResourceId")
public class CreateBottleActivity extends AppCompatActivity{

    @BindView(R.id.field_bottle_content)
    EditText mFieldBottleContent;
    @BindView(R.id.image_create_bottle_photo)
    ImageView mImageBottlePhoto;

//    private ProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bottle);
        ButterKnife.bind(this);

//        mPresenter = new ProfilePresenterImpl(
//                ThreadExecutor.getInstance(),
//                MainThreadImpl.getInstance(),
//                this,
//                new FirebaseUserRepository()
//        );
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        mPresenter.resume();
//    }
//
//    @Override
//    public void showProgress() {
//
//    }
//
//    @Override
//    public void hideProgress() {
//
//    }
//
//    @Override
//    public void showError(String message) {
//
//    }
}