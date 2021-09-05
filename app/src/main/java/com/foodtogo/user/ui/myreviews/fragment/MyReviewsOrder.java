package com.foodtogo.user.ui.myreviews.fragment;

import android.content.Context;
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
import com.foodtogo.user.model.myreviews.OrderReviewList;
import com.foodtogo.user.ui.myreviews.adapter.ReviewOrderAdapter;
import com.foodtogo.user.ui.myreviews.interfaces.OnChildOrderListener;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.OFF_SET_LIMIT;
import static com.foodtogo.user.base.AppConstants.REVIEWS_ORDER;


public class MyReviewsOrder extends BaseFragment {


    public MyReviewsOrder() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    ArrayList<OrderReviewList> orderReviewLists;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private ReviewOrderAdapter reviewOrderAdapter;
    private OnChildOrderListener childOrderListener;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderReviewLists = getArguments().getParcelableArrayList(REVIEWS_ORDER);
        setRecyclerView();
    }

    private void setRecyclerView() {
        reviewOrderAdapter = new ReviewOrderAdapter(getActivity(), orderReviewLists);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reviewOrderAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                MyReviewsFragment.currentPage += 1;
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
        reviewOrderAdapter.addLoadingFooter();
        if (orderReviewLists.size() < OFF_SET_LIMIT) {
            reviewOrderAdapter.removeLoadingFooter();
            isLastPage = true;
        }

        if (orderReviewLists.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.GONE);

        } else {
            tvNoOrder.setText(getString(R.string.no_order_reviews));
            recyclerView.setVisibility(View.GONE);
            tvNoOrder.setVisibility(View.VISIBLE);
        }
    }

    private void loadNextPage() {
        childOrderListener.refreshOrder();
    }

    public void loadMore(ArrayList<OrderReviewList> itemReviewLists) {
        if (!isLastPage) {
            if (itemReviewLists == null || itemReviewLists.size() == 0) {
                reviewOrderAdapter.removeLoadingFooter();
                isLastPage = true;
            } else {
                reviewOrderAdapter.removeLoadingFooter();
                isLoading = false;
                reviewOrderAdapter.addAll(itemReviewLists);
                reviewOrderAdapter.addLoadingFooter();
                if (itemReviewLists.size() < OFF_SET_LIMIT) {
                    reviewOrderAdapter.removeLoadingFooter();
                    isLastPage = true;
                }
            }
        }

    }

    public void loadMoreError(String message) {
        reviewOrderAdapter.removeLoadingFooter();
        isLastPage = true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof OnChildOrderListener) {
            childOrderListener = (OnChildOrderListener) getParentFragment();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChildItemListener");
        }
    }


    public void onDetach() {
        super.onDetach();
        childOrderListener = null;
    }
}