package com.foodtogo.user.ui.wallet.usedwallet.fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.wallet.UsedDetail;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;
import com.foodtogo.user.ui.wallet.adapter.UsedWalletAdapter;
import com.foodtogo.user.ui.wallet.usedwallet.mvp.UsedWalletContractor;
import com.foodtogo.user.ui.wallet.usedwallet.mvp.UsedWalletPresenter;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.CODE;
import static com.foodtogo.user.base.AppConstants.MESSAGE;
import static com.foodtogo.user.base.AppConstants.NO_RECORDS_FOUND;
import static com.foodtogo.user.base.AppConstants.OFF_SET_LIMIT;
import static com.foodtogo.user.base.AppConstants.USED_WALLET;


public class UsedWallet extends BaseFragment implements UsedWalletContractor.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_message)
    TextView tvError;


    public UsedWallet() {
        // Required empty public constructor
    }

    UsedWalletPresenter myCartPresenter;
    UsedWalletAdapter adapter;
    private List<UsedDetail> usedDetailList;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_total_wallet, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCartPresenter = new UsedWalletPresenter(this, appRepository);
        setRecyclerView();
        usedDetailList = getArguments().getParcelableArrayList(USED_WALLET);
        int code = getArguments().getInt(CODE);
        String message = getArguments().getString(MESSAGE);
        if (code == 200) {
            loadListView();
        } else {
            showError(message);
        }
    }

    private void loadListView() {
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
    }


    private void setRecyclerView() {
        usedDetailList = new ArrayList<>();
        adapter = new UsedWalletAdapter(getActivity(), usedDetailList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        setRecyclerViewAnimation(recyclerView);
        recyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call
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

    private void loadNextPage() {
        myCartPresenter.requestUsedWallet(currentPage);
    }


    @Override
    public void showLoadingView() {
        showProgressDialog();
    }

    @Override
    public void hideLoadingView() {
        hideProgressDialog();
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
        System.out.println("msg2:"+message);
        if(message.equalsIgnoreCase(NO_RECORDS_FOUND)){
            message=getString(R.string.no_information);
        }
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
    public void showLoadMore(WalletBalanceResponse walletBalanceResponse) {
        adapter.removeLoadingFooter();
        isLoading = false;
        adapter.addAll(walletBalanceResponse.getUsedDetails());
        adapter.addLoadingFooter();
        if (walletBalanceResponse.getUsedDetails().size() < OFF_SET_LIMIT) {
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