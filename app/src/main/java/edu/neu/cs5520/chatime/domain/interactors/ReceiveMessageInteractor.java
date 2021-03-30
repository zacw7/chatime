package edu.neu.cs5520.chatime.domain.interactors;

import java.util.List;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;
import edu.neu.cs5520.chatime.domain.model.Message;

public interface ReceiveMessageInteractor extends Interactor {
    interface Callback {
        void onMessagesReceived(List<Message> messageList);
    }
}
