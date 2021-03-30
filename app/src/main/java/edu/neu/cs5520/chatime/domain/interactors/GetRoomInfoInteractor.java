package edu.neu.cs5520.chatime.domain.interactors;

import edu.neu.cs5520.chatime.domain.interactors.base.Interactor;
import edu.neu.cs5520.chatime.domain.model.Room;

public interface GetRoomInfoInteractor extends Interactor {
    interface Callback {
        void onRoomInfoRetrieved(Room room);
    }
}
