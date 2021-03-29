package edu.neu.cs5520.chatime.presentation.ui.viewmodel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import edu.neu.cs5520.chatime.domain.model.Message;
import edu.neu.cs5520.chatime.domain.model.User;

public class MessageViewModel {
    String mContent;
    String mFrom;
    String mTo;
    String mDate;
    String mTime;
    String mProfileUrl;
    boolean mDateShowing;
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("MMM dd", Locale.US);
    private static final SimpleDateFormat TIME_FMT = new SimpleDateFormat("HH:mm:ss", Locale.US);

    public MessageViewModel(Message message, Map<String, User> userMap) {
        User userFrom = userMap.get(message.getFrom());
        User userTo = userMap.get(message.getTo());
        Timestamp ts = new Timestamp(message.getTimestamp());
        mContent = message.getContent();
        mFrom = userFrom.getUsername();
        mTo = userTo.getUsername();
        mDate = DATE_FMT.format(ts);
        mTime = TIME_FMT.format(ts);
        mProfileUrl = userFrom.getProfileUrl().toString();
    }

    public String getContent() {
        return mContent;
    }

    public String getFrom() {
        return mFrom;
    }

    public String getTo() {
        return mTo;
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
