package edu.neu.cs5520.chatime.storage;

import android.content.Context;
import android.content.SharedPreferences;

import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.repository.CurrentChatroomIdRepository;

public class LocalCurrentChatroomIdIdRepository implements CurrentChatroomIdRepository {

    private final Context mContext;

    public LocalCurrentChatroomIdIdRepository(Context context) {
        this.mContext = context;
    }

    @Override
    public String getCurrentChatRoomId() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(mContext.getString(R.string.current_chat_room_id), null);
    }

    @Override
    public void saveCurrentChatRoomId(String chatroomId) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mContext.getString(R.string.current_chat_room_id), chatroomId);
        editor.apply();
    }

    @Override
    public void resetCurrentChatRoomId() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(mContext.getString(R.string.current_chat_room_id));
        editor.apply();
    }
}
