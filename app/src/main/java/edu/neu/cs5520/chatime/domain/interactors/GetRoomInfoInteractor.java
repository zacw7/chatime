package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;
import edu.neu.cs5520.chatime.domain.model.Room;

public interface GetRoomInfoInteractor extends Interactor {
    interface Callback {
        void onRoomInfoRetrieveSucceed(Room room);

        void onRoomInfoRetrieveFailed(String error);
    }
}
