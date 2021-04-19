package edu.neu.cs5520.chatime.domain.repository;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public interface StorageRepository {
    void upload(Uri file, String folder,
            OnCompleteListener<UploadTask.TaskSnapshot> onCompleteListener);

    void download(String url, File file,
            OnCompleteListener<FileDownloadTask.TaskSnapshot> onCompleteListener);
}
