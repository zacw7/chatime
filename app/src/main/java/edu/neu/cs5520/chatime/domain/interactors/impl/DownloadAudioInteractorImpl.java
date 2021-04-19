package edu.neu.cs5520.chatime.domain.interactors.impl;

import java.io.File;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.DownloadAudioInteractor;
import edu.neu.cs5520.chatime.domain.interactors.base.AbstractInteractor;
import edu.neu.cs5520.chatime.domain.repository.StorageRepository;

public class DownloadAudioInteractorImpl extends AbstractInteractor implements
        DownloadAudioInteractor {

    private Callback mCallback;
    private StorageRepository mStorageRepository;
    private String mUrl;
    private File mFile;

    public DownloadAudioInteractorImpl(Executor threadExecutor,
            MainThread mainThread,
            Callback callback,
            StorageRepository storageRepository, String url, File file) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mStorageRepository = storageRepository;
        mUrl = url;
        mFile = file;
    }

    @Override
    public void run() {
        mStorageRepository.download(mUrl, mFile, task -> {
            if (task.isSuccessful()) {
                mCallback.onDownloadAudioSucceed("succeed");
            } else {
                mCallback.onDownloadAudioFailed(task.getException().getMessage());
            }
        });
    }
}
