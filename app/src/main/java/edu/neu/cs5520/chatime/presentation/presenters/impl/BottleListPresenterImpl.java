package edu.neu.cs5520.chatime.presentation.presenters.impl;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.chatime.domain.executor.Executor;
import edu.neu.cs5520.chatime.domain.executor.MainThread;
import edu.neu.cs5520.chatime.domain.interactors.GetBottleListInteractor;
import edu.neu.cs5520.chatime.domain.interactors.impl.GetBottleListInteractorImpl;
import edu.neu.cs5520.chatime.domain.model.DriftBottle;
import edu.neu.cs5520.chatime.domain.repository.DriftBottleRepository;
import edu.neu.cs5520.chatime.domain.repository.UserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.BottleListPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.base.AbstractPresenter;
import edu.neu.cs5520.chatime.presentation.ui.adapters.BottleListAdapter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;

public class BottleListPresenterImpl extends AbstractPresenter implements BottleListPresenter,
        GetBottleListInteractor.Callback, BottleListAdapter.OnItemClickListener {

    private final String TAG = "ChatListPresenter";
    private View mView;
    private BottleListAdapter mAdapter;
    private DriftBottleRepository mDriftBottleRepository;
    private UserRepository mUserRepository;
    private List<DriftBottleViewModel> mItemList;
    private String mMeUid;

    public BottleListPresenterImpl(Executor executor, MainThread mainThread, View view,
            DriftBottleRepository driftBottleRepository, UserRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mDriftBottleRepository = driftBottleRepository;
        mUserRepository = userRepository;
        mMeUid = mUserRepository.getCurrentUser().getUid();
        mItemList = new ArrayList<>();
    }

    @Override
    public void onBottleListRetrieveSucceed(List<DriftBottle> bottleList) {
        mView.hideProgress();
        mItemList.clear();
        for (DriftBottle origin : bottleList) {
            mItemList.add(new DriftBottleViewModel(origin));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBottleListRetrievedFailed(String error) {
        mView.hideProgress();
        mView.showMessage(error);
    }

    @Override
    public void onItemClick(DriftBottleViewModel item) {
        mView.displayBottle(item);
    }

    @Override
    public void resume() {
        mView.showProgress();
        // setup adapter
        mAdapter = new BottleListAdapter(mItemList, this);
        mView.setupAdapter(mAdapter);

        GetBottleListInteractor interactor = new GetBottleListInteractorImpl(mExecutor, mMainThread,
                this, mDriftBottleRepository, mMeUid);
        interactor.execute();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        mView.showMessage(message);
    }
}
