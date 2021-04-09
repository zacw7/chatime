package edu.neu.cs5520.chatime.domain.interactors.impl;

import android.net.Uri;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.UploadFileInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;

public class UploadFileInteractorImpl extends AbstractInteractor implements UploadFileInteractor {

    private UploadFileInteractor.Callback mCallback;
    private StorageRepository mStorageRepository;
    private Uri mUri;
    private String mFolder;

    public UploadFileInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            UploadFileInteractor.Callback callback,
            StorageRepository storageRepository, Uri uri, String folder) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mStorageRepository = storageRepository;
        mUri = uri;
        mFolder = folder;
    }

    @Override
    public void run() {
        mStorageRepository.upload(mUri, mFolder,
                taskSnapshot -> mCallback.onUploadFileSucceed("gs://cs5520-chatime.appspot.com/" + mFolder + "/" + mUri.getLastPathSegment()),
                e -> mCallback.onUploadFileSucceed(e.getMessage()));
    }
}
