package edu.neu.cs5520.chatime.presentation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.network.FirebaseUserRepository;
import edu.neu.cs5520.chatime.presentation.presenters.BottleListPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.BottleListPresenterImpl;
import edu.neu.cs5520.chatime.presentation.ui.activities.DriftBottleActivity;
import edu.neu.cs5520.chatime.presentation.ui.adapters.BottleListAdapter;
import edu.neu.cs5520.chatime.presentation.ui.viewmodel.DriftBottleViewModel;
import edu.neu.cs5520.chatime.storage.FirebaseDriftBottleRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

public class BottleListFragment extends Fragment implements BottleListPresenter.View {

    @BindView(R.id.recycler_bottle_list)
    RecyclerView mRecyclerView;

    private BottleListPresenter mPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bottle_list, container, false);
        ButterKnife.bind(this, root);

        // create a presenter for this view
        mPresenter = new BottleListPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new FirebaseDriftBottleRepository(),
                new FirebaseUserRepository()
        );
        return root;
    }

    @Override
    public void setupAdapter(BottleListAdapter mAdapter) {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void displayBottle(DriftBottleViewModel model) {
        Intent intent = new Intent(getActivity(), DriftBottleActivity.class);
        intent.putExtra(getString(R.string.current_drift_bottle), model);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {

    }
}