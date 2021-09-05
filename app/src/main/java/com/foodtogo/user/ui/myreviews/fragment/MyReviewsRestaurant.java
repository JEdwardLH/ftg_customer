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
import com.foodtogo.user.model.myreviews.RestReviewList;
import com.foodtogo.user.ui.myreviews.adapter.ReviewRestaurantAdapter;
import com.foodtogo.user.ui.myreviews.interfaces.OnChildRestaurantListener;
import com.foodtogo.user.ui.myreviews.interfaces.ReviewRestaurantClickListener;
import com.foodtogo.user.ui.viewrestaurant.activity.ViewRestaurant;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.OFF_SET_LIMIT;
import static com.foodtogo.user.base.AppConstants.RESTAURANT_ID;
import static com.foodtogo.user.base.AppConstants.RESTAURANT_NAME;
import static com.foodtogo.user.base.AppConstants.REVIEWS_RESTAURANT;


public class MyReviewsRestaurant extends BaseFragment implements ReviewRestaurantClickListener {


    public MyReviewsRestaurant() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    ArrayList<RestReviewList> restReviewList;
    private ReviewRestaurantAdapter adapter;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private OnChildRestaurantListener onChildRestaurantListener;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restReviewList = getArguments().getParcelableArrayList(REVIEWS_RESTAURANT);
        if (restReviewList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);
            setRecyclerView();
        } else {
            tvNoOrder.setText(getString(R.string.no_restaurant_reviews));
            recyclerView.setVisibility(View.GONE);
            tvNoOrder.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerView() {
        adapter = new ReviewRestaurantAdapter(getActivity(), restReviewList);
        adapter.setReviewRestaurantListener(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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
        adapter.addLoadingFooter();
        if (restReviewList.size() < OFF_SET_LIMIT) {
            adapter.removeLoadingFooter();
            isLastPage = true;
        }
    }


    public void loadMore(ArrayList<RestReviewList> itemReviewLists) {
        if (!isLastPage) {
            adapter.removeLoadingFooter();
            isLoading = false;
            if (itemReviewLists == null || itemReviewLists.size() == 0) {
                adapter.removeLoadingFooter();
                isLastPage = true;
            } else {
                adapter.addAll(itemReviewLists);
                adapter.addLoadingFooter();
                if (itemReviewLists.size() < OFF_SET_LIMIT) {
                    adapter.removeLoadingFooter();
                    isLastPage = true;
                }
            }
        }
    }

    public void loadMoreError(String message) {
        adapter.removeLoadingFooter();
        isLastPage = true;
    }

    private void loadNextPage() {
        onChildRestaurantListener.refreshRestaurant();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof OnChildRestaurantListener) {
            onChildRestaurantListener = (OnChildRestaurantListener) getParentFragment();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChildItemListener");
        }
    }


    public void onDetach() {
        super.onDetach();
        onChildRestaurantListener = null;
    }


    @Override
    public void clickItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(RESTAURANT_NAME, restReviewList.get(position).getRestaurantName());
        bundle.putInt(RESTAURANT_ID, restReviewList.get(position).getResStoreId());
        changeActivityExtras(getActivity(), ViewRestaurant.class, bundle);
    }
}