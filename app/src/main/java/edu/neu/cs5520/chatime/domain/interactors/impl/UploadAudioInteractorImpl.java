package edu.neu.cs5520.chatime.domain.interactors.impl;

import android.net.Uri;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UploadAudioInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;

public class UploadAudioInteractorImpl extends AbstractInteractor implements
        UploadAudioInteractor {

    private static final String FOLDER = "audios";
    private Callback mCallback;
    private StorageRepository mStorageRepository;
    private Uri mUri;

    public UploadAudioInteractorImpl(Executor threadExecutor,
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
                mCallback.onUploadAudioSucceed(
                        "gs://cs5520-chatime.appspot.com/" + FOLDER + "/"
                                + mUri.getLastPathSegment());
            } else {
                mCallback.onUploadAudioFailed(task.getException().getMessage());
            }
        });
    }
}
