package edu.neu.cs5520.chatime.network;

import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    public CustomFirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("TAG", "From: " + remoteMessage.getFrom());

        if (!remoteMessage.getData().isEmpty()) {
            Log.d("TAG", "Received Data: " + remoteMessage.getData().toString());
            Map<String, String> dataMap = remoteMessage.getData();
            if (dataMap.containsKey("roomId")) {
                Intent intent = new Intent("room-id-event");
                // You can also include some extra data.
                intent.putExtra("roomId", dataMap.get("roomId"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
        }
    }
}
