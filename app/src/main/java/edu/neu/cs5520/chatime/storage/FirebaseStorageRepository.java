package edu.neu.cs5520.chatime.storage;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import edu.neu.cs5520.chatime.domain.repository.StorageRepository;

public class FirebaseStorageRepository implements StorageRepository {

    private static final String STORAGE_PATH = "gs://cs5520-chatime.appspot.com/";
    private FirebaseStorage mFirebaseStorage;

    public FirebaseStorageRepository() {
        mFirebaseStorage = FirebaseStorage.getInstance(STORAGE_PATH);
    }

    @Override
    public void upload(Uri file, String folder,
            OnCompleteListener<UploadTask.TaskSnapshot> onCompleteListener) {
        StorageReference storageRef = mFirebaseStorage.getReference().child(
                folder + "/" + file.getLastPathSegment());
        UploadTask uploadTask = storageRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void download(String url, File file,
            OnCompleteListener<FileDownloadTask.TaskSnapshot> onCompleteListener) {
        StorageReference fileRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        fileRef.getFile(file).addOnCompleteListener(onCompleteListener);
    }
}
