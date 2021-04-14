package edu.neu.cs5520.chatime.domain.repository;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.storage.UploadTask;

public interface StorageRepository {
    void upload(Uri file, String folder,
            OnCompleteListener<UploadTask.TaskSnapshot> onCompleteListener);
}
