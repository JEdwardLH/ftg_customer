package com.foodtogo.user.ui.myoffers.fragment;

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
import com.foodtogo.user.model.offers.OffersData;
import com.foodtogo.user.model.offers.OffersResponse;
import com.foodtogo.user.ui.myoffers.adapter.MyOffersAdapter;
import com.foodtogo.user.ui.myoffers.mvp.OffersContractor;
import com.foodtogo.user.ui.myoffers.mvp.OffersPresenter;
import com.foodtogo.user.util.NetworkUtils;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.OFF_SET_LIMIT;

public class MyOffersFragment extends BaseFragment implements OffersContractor.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_message)
    TextView tvError;

    @BindView(R.id.total_offer_point)
    TextView totalOfferAmount;

    @BindView(R.id.used_point)
    TextView usedOffer;

    @BindView(R.id.balance_point)
    TextView balanceOffer;

    public MyOffersFragment() {
    }

    OffersPresenter presenter;
    MyOffersAdapter adapter;

    private List<OffersData> offersDetailList=new ArrayList<>();
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    LinearLayoutManager mLayoutManager;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_offers, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
        presenter=new OffersPresenter(this,appRepository);

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
        if(NetworkUtils.isNetworkConnected(getActivity())){
            showLoadingView();
            presenter.requestTotalOffers(1);
        }else{
            showError(R.string.no_internet_connection);
        }

    }

    private void setRecyclerView() {
        try {
            offersDetailList=new ArrayList<>();
            adapter = new MyOffersAdapter(offersDetailList,getActivity());
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

    private void loadListView() {
        try {
            tvError.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.addAll(offersDetailList);
            adapter.addLoadingFooter();
            startRecyclerAnimation(recyclerView);
            if (offersDetailList.size() < OFF_SET_LIMIT) {
                adapter.removeLoadingFooter();
                isLastPage = true;
            }
            if (offersDetailList.size() == 0) {
                showError("No Records found!");
            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadNextPage() {
        presenter.requestTotalOffers(currentPage);
    }

    @Override
    public void showTotalOfferLoadMore(OffersResponse offerResponse,int pageNo) {
        if (pageNo==1){
            offersDetailList=offerResponse.getOffersData();
            if(offersDetailList!=null && offersDetailList.size()>0){
                loadListView();
            }else{
                tvError.setText(getString(R.string.no_records_found));
                tvError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            totalOfferAmount.setText(String.format("%s%s", offerResponse.getCurrency(), offerResponse.getTotalOfferAmount()));
            usedOffer.setText(String.format("%s%s", offerResponse.getCurrency(), offerResponse.getUsedOfferAmount()));
            balanceOffer.setText(String.format("%s%s", offerResponse.getCurrency(), offerResponse.getBalanceAmount()));
        }else{
                adapter.removeLoadingFooter();
                isLoading = false;
                adapter.addAll(offerResponse.getOffersData());
                adapter.addLoadingFooter();
                int size=offerResponse.getOffersData().size();
                if (size < OFF_SET_LIMIT) {
                    adapter.removeLoadingFooter();
                    isLastPage = true;
                }

        }
    }

    @Override
    public void showLoadMoreError(String error,int pageNo) {
        if(pageNo==1 && offersDetailList.size()==0){
            tvError.setText(getString(R.string.no_records_found));
            tvError.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            adapter.removeLoadingFooter();
            isLastPage = true;
        }

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
    public void showLoadingView() {
        showProgressDialog();
    }

    @Override
    public void hideLoadingView() {
        hideProgressDialog();
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
}
