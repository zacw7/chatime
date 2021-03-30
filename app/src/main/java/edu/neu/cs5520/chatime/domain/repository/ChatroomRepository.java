package edu.neu.cs5520.chatime.domain.repository;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface ChatroomRepository {
    void getRoomInfo(OnSuccessListener<DocumentSnapshot> listener);
    void getRoomList(OnSuccessListener<QuerySnapshot> listener);
}
