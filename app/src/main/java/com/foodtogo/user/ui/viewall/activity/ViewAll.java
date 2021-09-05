package com.foodtogo.user.ui.viewall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;
import com.foodtogo.user.model.allrestaurant.AllRestautrantDetail;
import com.foodtogo.user.model.home.RestaurantDetail;
import com.foodtogo.user.ui.viewall.adapter.ViewAllDataAdapters;
import com.foodtogo.user.ui.viewall.interfaces.ItemDetailListener;
import com.foodtogo.user.ui.viewall.mvp.ViewAllContractor;
import com.foodtogo.user.ui.viewall.mvp.ViewAllPresenter;
import com.foodtogo.user.ui.viewrestaurant.activity.ViewRestaurant;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ViewAll extends BaseActivity implements ViewAllContractor.View, AppConstants, ItemDetailListener {


    ViewAllPresenter viewAllPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    List<AllRestautrantDetail> restaurantDetailList;
    private ViewAllDataAdapters itemListDataAdapter;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    String categoryId = "";
    String type ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewAllPresenter = new ViewAllPresenter(this, appRepository);
        setRecyclerView();
        setupToolBar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTitle.setText(bundle.getString(TITLE));
            tvTitle.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            categoryId = String.valueOf(bundle.getInt(CATEGORY_ID));
            type=bundle.getString(TYPE);
            viewAllPresenter.onViewCategoryBasedItemRequest(categoryId, currentPage,type);
        }
    }

    private void setRecyclerView() {
        restaurantDetailList = new ArrayList<>();
        itemListDataAdapter = new ViewAllDataAdapters(this, restaurantDetailList);
        recyclerView.setHasFixedSize(true);
        itemListDataAdapter.setItemDetailsListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setRecyclerViewAnimation(recyclerView);
        recyclerView.setAdapter(itemListDataAdapter);

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
        viewAllPresenter.onViewCategoryBasedItemRequest(categoryId, currentPage,type);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_view_all;
    }

    @Override
    public void showLoadingView() {
        showProgress();
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }


    @Override
    public void onViewAll(List<RestaurantDetail> restaurantDetailLists) {

    }

    @Override
    public void showViewCategoryBasedItems(AllRestaurantResponse allRestaurantResponse) {
        itemListDataAdapter.addAll(allRestaurantResponse.getAllRestautrantDetails());
        recyclerView.setAdapter(itemListDataAdapter);
        itemListDataAdapter.addLoadingFooter();
        startRecyclerAnimation(recyclerView);
        if (allRestaurantResponse.getAllRestautrantDetails().size() < OFF_SET_LIMIT) {
            itemListDataAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    @Override
    public void showLoadMore(AllRestaurantResponse allRestaurantResponse) {
        itemListDataAdapter.removeLoadingFooter();
        isLoading = false;
        itemListDataAdapter.addAll(allRestaurantResponse.getAllRestautrantDetails());
        itemListDataAdapter.addLoadingFooter();
        if (allRestaurantResponse.getAllRestautrantDetails().size() < OFF_SET_LIMIT) {
            itemListDataAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    @Override
    public void showLoadMoreError(String error) {
        itemListDataAdapter.removeLoadingFooter();
        isLastPage = true;
    }

    @Override
    public void itemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(RESTAURANT_NAME, restaurantDetailList.get(position).getRestaurantName());
        bundle.putInt(RESTAURANT_ID, restaurantDetailList.get(position).getRestaurantId());
        changeActivityExtras(ViewRestaurant.class, bundle);
    }
}
