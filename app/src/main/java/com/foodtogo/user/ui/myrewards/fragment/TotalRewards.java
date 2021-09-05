package com.foodtogo.user.ui.myrewards.fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.wallet.Reward;
import com.foodtogo.user.model.wallet.RewardsResponse;
import com.foodtogo.user.ui.myrewards.adapter.RewardAdapter;
import com.foodtogo.user.ui.myrewards.mvp.TotalRewardsContractor;
import com.foodtogo.user.ui.myrewards.mvp.TotalRewardsPresenter;
import com.foodtogo.user.util.PaginationScrollListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.OFF_SET_LIMIT;
import static com.foodtogo.user.base.AppConstants.TOTAL_REWARDS;
import static com.foodtogo.user.ui.myrewards.fragment.MyRewardsFragment.EARNED;


public class TotalRewards extends BaseFragment implements TotalRewardsContractor.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_message)
    TextView tvError;
    public static String FROM="from";

     private String fromValue="";


    public TotalRewards() {
        // Required empty public constructor
    }

    TotalRewardsPresenter myCartPresenter;
    RewardAdapter adapter;
    private List<Reward> usedDetailList=new ArrayList<>();
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    LinearLayoutManager mLayoutManager;

    public static Fragment newInstance(List<Reward> rewardsList, String from) {
        TotalRewards fragment = new TotalRewards();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TOTAL_REWARDS, (Serializable) rewardsList);
        bundle.putSerializable(FROM, from);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_total_wallet, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCartPresenter = new TotalRewardsPresenter(this, appRepository);
        fromValue=getArguments().getString(FROM);
        setRecyclerView();
        usedDetailList = getArguments().getParcelableArrayList(TOTAL_REWARDS);
        if(usedDetailList.size()>0){
           loadListView();
        }else{
            tvError.setText(getString(R.string.no_records_found));
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        recyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                new Handler().postDelayed(() -> loadNextPage(), 1000);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadListView() {
        try {
            tvError.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.addAll(usedDetailList);
            adapter.addLoadingFooter();
            startRecyclerAnimation(recyclerView);
            if (usedDetailList.size() < OFF_SET_LIMIT) {
                adapter.removeLoadingFooter();
                isLastPage = true;
            }
            if (usedDetailList.size() == 0) {
                showError("No Records found!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setRecyclerView() {
        try {
            usedDetailList=new ArrayList<>();
            adapter = new RewardAdapter(getActivity(), usedDetailList, fromValue);
            recyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            setRecyclerViewAnimation(recyclerView);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadNextPage() {
        myCartPresenter.requestTotalRewards(currentPage,fromValue);
    }


    @Override
    public void showLoadingView() {
        //showProgressDialog();
    }

    @Override
    public void hideLoadingView() {
        //hideProgressDialog();
    }

    @Override
    public void showProgressBar() {
        showProgress();
    }

    @Override
    public void hideProgressBar() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showError(int message) {
        tvError.setText(getResources().getString(message));
        tvError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showTotalRewardsLoadMore(RewardsResponse rewardsResponse,String from) {
        adapter.removeLoadingFooter();
        isLoading = false;
        if(from.equals(EARNED)){
            adapter.addAll(rewardsResponse.getEarnedPointsHistory());
        }else{
            adapter.addAll(rewardsResponse.getRewardedHistory());
        }
        adapter.addLoadingFooter();
        int size=from.equals(EARNED)?rewardsResponse.getEarnedPointsHistory().size():rewardsResponse.getRewardedHistory().size();
        if (size < OFF_SET_LIMIT) {
            adapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    @Override
    public void showLoadMoreError(String error) {
        adapter.removeLoadingFooter();
        isLastPage = true;
    }
}