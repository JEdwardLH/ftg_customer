package com.foodtogo.user.ui.viewallreviews.activity;

import android.os.Bundle;
import android.os.Handler;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.itemdetails.ItemReviews;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;
import com.foodtogo.user.model.restaurant.RestaurantReview;
import com.foodtogo.user.ui.viewallreviews.adapter.AllItemRatingAdapter;
import com.foodtogo.user.ui.viewallreviews.adapter.AllRestaurantRatingAdapter;
import com.foodtogo.user.ui.viewallreviews.mvp.AllReviewContractor;
import com.foodtogo.user.ui.viewallreviews.mvp.AllReviewPresenter;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ViewAllReviews extends BaseActivity implements AllReviewContractor.View {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<RestaurantReview> restaurantReviews = null;

    ArrayList<ItemReviews> itemReviews;

    AllRestaurantRatingAdapter allRestaurantRatingAdapter;
    AllItemRatingAdapter allItemRatingAdapter;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private int restaurantId;
    private int itemId;
    private AllReviewPresenter allReviewPresenter;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allReviewPresenter = new AllReviewPresenter(this, appRepository);
        setupToolBar();
        tvTitle.setText(getResources().getString(R.string.view_all_reviews));
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString(TITLE);
        if (type.equals(RESTAURANT)) {
            restaurantReviews = bundle.getParcelableArrayList(ALL_REVIEWS);
            restaurantId = bundle.getInt(RESTAURANT_ID);
            setRestaurantRecyclerView();
        } else {
            itemReviews = bundle.getParcelableArrayList(ALL_REVIEWS);
            itemId = bundle.getInt(ITEM_ID);
            setItemRecyclerView();
        }
    }

    private void setRestaurantRecyclerView() {
        allRestaurantRatingAdapter = new AllRestaurantRatingAdapter(this, restaurantReviews);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setRecyclerViewAnimation(recyclerView);
        recyclerView.setAdapter(allRestaurantRatingAdapter);
        startRecyclerAnimation(recyclerView);
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

        allRestaurantRatingAdapter.addLoadingFooter();
        if (restaurantReviews.size() < OFF_SET_LIMIT) {
            allRestaurantRatingAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    private void setItemRecyclerView() {
        allItemRatingAdapter = new AllItemRatingAdapter(this, itemReviews);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setRecyclerViewAnimation(recyclerView);
        recyclerView.setAdapter(allItemRatingAdapter);
        startRecyclerAnimation(recyclerView);
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

        allItemRatingAdapter.addLoadingFooter();
        if (itemReviews.size() < OFF_SET_LIMIT) {
            allItemRatingAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }


    private void loadNextPage() {
        if (type.equals(RESTAURANT)) {
            allReviewPresenter.requestRestaurantDetail(restaurantId, currentPage);
        } else {
            allReviewPresenter.requestItemDetail(itemId, currentPage);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_all;
    }


    @Override
    public void showLoadMore(RestaurantDetailResponse restaurantDetailResponse) {
        allRestaurantRatingAdapter.removeLoadingFooter();
        isLoading = false;
        allRestaurantRatingAdapter.addAll(restaurantDetailResponse.getRestaurantReview());
        allRestaurantRatingAdapter.addLoadingFooter();
        if (restaurantDetailResponse.getRestaurantReview().size() < OFF_SET_LIMIT) {
            allRestaurantRatingAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    @Override
    public void showLoadMore(ResponseItemDetails responseItemDetails) {
        allItemRatingAdapter.removeLoadingFooter();
        isLoading = false;
        allItemRatingAdapter.addAll(responseItemDetails.getItemReviews());
        allItemRatingAdapter.addLoadingFooter();
        if (responseItemDetails.getItemReviews().size() < OFF_SET_LIMIT) {
            allItemRatingAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    @Override
    public void showLoadMoreError(String error) {
        if (type.equals(RESTAURANT)) {
            allRestaurantRatingAdapter.removeLoadingFooter();
            isLastPage = true;
        } else {
            allItemRatingAdapter.removeLoadingFooter();
            isLastPage = true;
        }

    }


    @Override
    public void showLoadingView() {
    }

    @Override
    public void hideLoadingView() {
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }
}
