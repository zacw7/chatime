package edu.neu.cs5520.chatime.storage;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.Timestamp;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.model.Room;
import edu.neu.cs5520.chatime.domain.repository.ChatroomRepository;

public class FirebaseChatroomRepository implements ChatroomRepository {

    private final String TAG = "ChatroomRepository";
    private FirebaseFunctions mFunctions;
    private String mRoomId;

    public FirebaseChatroomRepository() {
        mFunctions = FirebaseFunctions.getInstance();
    }

    public FirebaseChatroomRepository(String roomId) {
        mRoomId = roomId;
        mFunctions = FirebaseFunctions.getInstance();
    }

    @Override
    public void getRoomInfo(OnCompleteListener<Room> onCompleteListener) {
        Map<String, Object> data = new HashMap<>();
        data.put("roomId", mRoomId);

        mFunctions.getHttpsCallable("getRoomInfo").call(data).continueWith(
                task -> {
                    Map<String, Object> map = (Map) task.getResult().getData();
                    Room room = new Room();
                    room.setId((String) map.get("id"));
                    room.setRecipientUid((String) map.get("recipientUid"));
                    room.setRecipientUsername((String) map.get("recipientUsername"));
                    if (map.containsKey("topic")) {
                        room.setTopic((String) map.get("topic"));
                    }
                    return room;
                }).addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void getRoomList(OnCompleteListener<List<Room>> onCompleteListener) {
        mFunctions.getHttpsCallable("getRoomList").call().continueWith(
                task -> {
                    if (!task.isSuccessful()) {
                        task.getException().printStackTrace();
                    }
                    ArrayList<Map<String, Object>> list = (ArrayList) task.getResult().getData();
                    List<Room> roomList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        Room room = new Room();
                        room.setId((String) map.get("id"));
                        room.setRecipientUid((String) map.get("recipientUid"));
                        Map<String, Integer> tsCreatedMap = (Map<String, Integer>) map.get(
                                "createdAt");
                        room.setCreatedAt(new Timestamp(tsCreatedMap.get("_seconds"),
                                tsCreatedMap.get("_nanoseconds")));
                        if (map.containsKey("topic")) {
                            room.setTopic((String) map.get("topic"));
                        }
                        if (map.containsKey("recipientUsername")) {
                            room.setRecipientUsername((String) map.get("recipientUsername"));
                        } else {
                            room.setRecipientUsername(room.getRecipientUid());
                        }
                        if (map.containsKey("lastMessageTime")) {
                            Map<String, Integer> tsLastMap = (Map<String, Integer>) map.get(
                                    "lastMessageTime");
                            room.setLastMessageTime(new Timestamp(tsLastMap.get("_seconds"),
                                    tsLastMap.get("_nanoseconds")));
                        }
                        roomList.add(room);
                    }
                    return roomList;
                }).addOnCompleteListener(onCompleteListener);
    }
}
