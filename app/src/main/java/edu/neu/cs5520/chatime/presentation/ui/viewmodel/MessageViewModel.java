package edu.neu.cs5520.chatime.presentation.ui.viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.neu.cs5520.chatime.domain.model.Message;

public class MessageViewModel {
    String mContent;
    String mFromUid;
    String mToUid;
    String mFromUsername;
    String mToUsername;
    String mDate;
    String mTime;
    boolean mDateShowing;
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("MMM dd", Locale.US);
    private static final SimpleDateFormat TIME_FMT = new SimpleDateFormat("HH:mm:ss", Locale.US);

    public MessageViewModel(Message message) {
        Date date = message.getTimestamp().toDate();
        mContent = message.getContent();
        mDate = DATE_FMT.format(date);
        mTime = TIME_FMT.format(date);
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getFromUid() {
        return mFromUid;
    }

    public void setFromUid(String fromUid) {
        mFromUid = fromUid;
    }

    public String getToUid() {
        return mToUid;
    }

    public void setToUid(String toUid) {
        mToUid = toUid;
    }

    public String getFromUsername() {
        return mFromUsername;
    }

    public void setFromUsername(String fromUsername) {
        mFromUsername = fromUsername;
    }

    public String getToUsername() {
        return mToUsername;
    }

    public void setToUsername(String toUsername) {
        mToUsername = toUsername;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public boolean isDateShowing() {
        return mDateShowing;
    }

    public void setDateShowing(boolean dateShowing) {
        mDateShowing = dateShowing;
    }

    public static SimpleDateFormat getDateFmt() {
        return DATE_FMT;
    }

    public static SimpleDateFormat getTimeFmt() {
        return TIME_FMT;
    }
}
