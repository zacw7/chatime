package edu.neu.cs5520.chatime.domain.interactors.impl;

import android.net.Uri;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UploadProfilePhotoInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;

public class UploadProfilePhotoInteractorImpl extends AbstractInteractor implements
        UploadProfilePhotoInteractor {

    private static final String FOLDER = "profiles";
    private UploadProfilePhotoInteractor.Callback mCallback;
    private StorageRepository mStorageRepository;
    private Uri mUri;

    public UploadProfilePhotoInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            UploadProfilePhotoInteractor.Callback callback,
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
                mCallback.onUploadProfilePhotoSucceed(
                        "gs://cs5520-chatime.appspot.com/" + FOLDER + "/"
                                + mUri.getLastPathSegment());
            } else {
                mCallback.onUploadProfilePhotoFailed(task.getException().getMessage());
            }
        });
    }
}
