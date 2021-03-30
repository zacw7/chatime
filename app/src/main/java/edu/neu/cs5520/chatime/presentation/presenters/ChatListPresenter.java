package edu.neu.cs5520.chatime.presentation.presenters;


import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;
import edu.neu.cs5520.chatime.presentation.ui.adapters.ChatListAdapter;

public interface ChatListPresenter extends BasePresenter {
    interface View extends BaseView {
        void setupAdapter(ChatListAdapter mAdapter);
        void enterChatRoom(String roomId);
    }
}
