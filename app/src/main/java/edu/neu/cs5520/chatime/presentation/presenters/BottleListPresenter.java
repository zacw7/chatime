package edu.neu.cs5520.chatime.presentation.presenters;

import edu.neu.cs5520.chatime.presentation.presenters.base.BasePresenter;
import edu.neu.cs5520.chatime.presentation.ui.BaseView;
import edu.neu.cs5520.chatime.presentation.ui.adapters.BottleListAdapter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;

public interface BottleListPresenter extends BasePresenter {
    interface View extends BaseView {
        void setupAdapter(BottleListAdapter mAdapter);

        void displayBottle(DriftBottleViewModel model);
    }
}
