package edu.neu.cs5520.chatime.presentation.ui.viewmodel;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.neu.cs5520.chatime.domain.model.DriftBottle;

public class DriftBottleViewModel implements Parcelable {
    private String mId;
    private String mCreatorUid;
    private String mCreatorUsername;
    private String mContent;
    private String mCreatedAt;
    private String mPhotoUrl;
    private String mAudioUrl;
    private Double mLatitude;
    private Double mLongitude;

    private static final SimpleDateFormat DATETIME_FMT = new SimpleDateFormat("MMM dd, yyyy, hh:ss a", Locale.US);

    public DriftBottleViewModel() {
    }

    public DriftBottleViewModel(DriftBottle origin) {
        mId = origin.getId();
        mCreatorUid = origin.getCreatorUid();
        mCreatorUsername = origin.getCreatorUsername();
        mContent = origin.getContent();
        mPhotoUrl = origin.getPhotoUrl();
        mAudioUrl = origin.getAudioUrl();
        mLatitude = origin.getLatitude();
        mLongitude = origin.getLongitude();
        mCreatedAt = DATETIME_FMT.format(origin.getCreatedAt().toDate());;
    }

    protected DriftBottleViewModel(Parcel in) {
        mId = in.readString();
        mCreatorUid = in.readString();
        mCreatorUsername = in.readString();
        mContent = in.readString();
        mCreatedAt = in.readString();
        mPhotoUrl = in.readString();
        mAudioUrl = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCreatorUid);
        dest.writeString(mCreatorUsername);
        dest.writeString(mContent);
        dest.writeString(mCreatedAt);
        if (mPhotoUrl != null) dest.writeString(mPhotoUrl);
        if (mAudioUrl != null) dest.writeString(mAudioUrl);
        if (mLatitude != null && mLongitude != null) {
            dest.writeDouble(mLatitude);
            dest.writeDouble(mLongitude);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DriftBottleViewModel> CREATOR =
            new Creator<DriftBottleViewModel>() {
                @Override
                public DriftBottleViewModel createFromParcel(Parcel in) {
                    return new DriftBottleViewModel(in);
                }

                @Override
                public DriftBottleViewModel[] newArray(int size) {
                    return new DriftBottleViewModel[size];
                }
            };

    public String getId() {
        return mId;
    }

    public String getCreatorUid() {
        return mCreatorUid;
    }

    public String getCreatorUsername() {
        return mCreatorUsername;
    }

    public String getContent() {
        return mContent;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getAudioUrl() {
        return mAudioUrl;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }
}