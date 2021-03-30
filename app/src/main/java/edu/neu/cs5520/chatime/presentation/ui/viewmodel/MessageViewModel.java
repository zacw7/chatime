package edu.neu.cs5520.chatime.presentation.ui.viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.model.Message;
import edu.neu.cs5520.chatime.domain.model.User;

public class MessageViewModel {
    String mContent;
    String mFromUid;
    String mToUid;
    String mFromUsername;
    String mToUsername;
    String mDate;
    String mTime;
    String mProfileUrl;
    boolean mDateShowing;
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("MMM dd", Locale.US);
    private static final SimpleDateFormat TIME_FMT = new SimpleDateFormat("HH:mm:ss", Locale.US);

    public MessageViewModel(Message message, Map<String, User> userMap) {
        User userFrom = userMap.get(message.getFrom());
        User userTo = userMap.get(message.getTo());
        Date date = message.getTimestamp().toDate();
        mContent = message.getContent();
        mFromUid = userFrom.getUid();
        mToUid = userTo.getUid();
        mFromUsername = userFrom.getUsername();
        mToUsername = userTo.getUsername();
        mDate = DATE_FMT.format(date);
        mTime = TIME_FMT.format(date);
        mProfileUrl = userFrom.getPictureUrl();
    }

    public String getContent() {
        return mContent;
    }

    public String getFromUid() {
        return mFromUid;
    }

    public String getToUid() {
        return mToUid;
    }

    public String getFromUsername() {
        return mFromUsername;
    }

    public String getToUsername() {
        return mToUsername;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getProfileUrl() {
        return mProfileUrl;
    }

    public void setDateShowing(boolean dateShowing) {
        mDateShowing = dateShowing;
    }

    public boolean isDateShowing() {
        return mDateShowing;
    }
}
