package edu.neu.cs5520.chatime.presentation.presenters;


import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;
import edu.neu.cs5520.chatime.presentation.ui.adapters.ChatMessageAdapter;

public interface ChatPresenter extends BasePresenter {
    void sendMessage(String content);

    interface View extends BaseView {
        void setupAdapter(ChatMessageAdapter mMessageAdapter);
        void enableChat();
        void setRecipient(String recipient);
        void setChatTopic(String topic);
    }
}
