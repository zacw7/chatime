package edu.neu.cs5520.chatime.domain.repository;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;

import edu.neu.cs5520.chatime.domain.model.Room;

public interface ChatroomRepository {
    void getRoomInfo(OnCompleteListener<Room> onCompleteListener);
    void getRoomList(OnCompleteListener<List<Room>> onCompleteListener);
}
