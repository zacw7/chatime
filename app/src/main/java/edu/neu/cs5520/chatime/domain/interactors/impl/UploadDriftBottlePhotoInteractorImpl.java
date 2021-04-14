package edu.neu.cs5520.chatime.domain.interactors.impl;

import android.net.Uri;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UploadDriftBottlePhotoInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;

public class UploadDriftBottlePhotoInteractorImpl extends AbstractInteractor implements
        UploadDriftBottlePhotoInteractor {

    private static final String FOLDER = "photos";
    private Callback mCallback;
    private StorageRepository mStorageRepository;
    private Uri mUri;

    public UploadDriftBottlePhotoInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            StorageRepository storageRepository, Uri uri) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mStorageRepository = storageRepository;
        mUri = uri;
    }

    @Override
    public void run() {
        mStorageRepository.upload(mUri, FOLDER, task -> {
            if (task.isSuccessful()) {
                mCallback.onUploadDriftBottlePhotoSucceed(
                        "gs://cs5520-chatime.appspot.com/" + FOLDER + "/"
                                + mUri.getLastPathSegment());
            } else {
                mCallback.onUploadDriftBottlePhotoFailed(task.getException().getMessage());
            }
        });
    }
}
