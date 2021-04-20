package edu.neu.cs5520.chatime.domain.interactors;

import java.util.List;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;
import edu.neu.cs5520.chatime.domain.model.Room;

public interface GetRoomListInteractor extends Interactor {
    interface Callback {
        void onRoomListRetrieveSucceed(List<Room> roomList);
        void onRoomListRetrieveFailed(String error);
    }
}
