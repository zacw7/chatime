package edu.neu.cs5520.chatime.domain.model;

public class Message {
    private String mFrom;
    private String mTo;
    private String mContent;
    private long mTimestamp;

    public Message() {
    }

    public Message(String from, String to, String content, long timestamp) {
        mFrom = from;
        mTo = to;
        mContent = content;
        mTimestamp = timestamp;
    }

    public String getFrom() {
        return mFrom;
    }

    public void setFrom(String from) {
        mFrom = from;
    }

    public String getTo() {
        return mTo;
    }

    public void setTo(String to) {
        mTo = to;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }
}
