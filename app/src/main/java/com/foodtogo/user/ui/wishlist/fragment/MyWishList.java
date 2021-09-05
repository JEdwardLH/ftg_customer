package com.foodtogo.user.ui.wishlist.fragment;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.wishlist.FavouriteResponse;
import com.foodtogo.user.model.wishlist.ProductWishList;
import com.foodtogo.user.ui.viewitemdetails.activity.ViewItemDetails;
import com.foodtogo.user.ui.wishlist.adapter.WishListItemAdapter;
import com.foodtogo.user.ui.wishlist.interfaces.MyWishListener;
import com.foodtogo.user.ui.wishlist.mvp.WishListContractor;
import com.foodtogo.user.ui.wishlist.mvp.WishListPresenter;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.ITEM_ID;
import static com.foodtogo.user.base.AppConstants.ITEM_NAME;
import static com.foodtogo.user.base.AppConstants.OFF_SET_LIMIT;
import static com.foodtogo.user.base.AppConstants.RESTAURANT_ID;


public class MyWishList extends BaseFragment implements WishListContractor.View, MyWishListener {

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    private WishListItemAdapter wishListItemAdapter;
    private List<ProductWishList> productWishList;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.rl_empty_wishlist)
    RelativeLayout rlEmptyWishlist;


    public MyWishList() {
        // Required empty public constructor
    }

    WishListPresenter myCartPresenter;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wishlist, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCartPresenter = new WishListPresenter(this, appRepository);
        setRecyclerView();
        myCartPresenter.requestFavourites(currentPage);
    }

    private void setRecyclerView() {
        productWishList = new ArrayList<>();
        wishListItemAdapter = new WishListItemAdapter(getActivity(), productWishList);
        recyclerView.setHasFixedSize(true);
        wishListItemAdapter.setWishListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        startRecyclerAnimation(recyclerView);
        recyclerView.setAdapter(wishListItemAdapter);
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
        myCartPresenter.requestFavourites(currentPage);
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
        tvError.setText(message);
        rlEmptyWishlist.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showError(int message) {
        tvError.setText(getResources().getString(message));
        rlEmptyWishlist.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showWishList(FavouriteResponse favouriteResponse) {
            recyclerView.setVisibility(View.VISIBLE);
            rlEmptyWishlist.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);
            wishListItemAdapter.addAll(favouriteResponse.getProductWishList());
            wishListItemAdapter.addLoadingFooter();
            startRecyclerAnimation(recyclerView);
            if (favouriteResponse.getProductWishList().size() < OFF_SET_LIMIT) {
                wishListItemAdapter.removeLoadingFooter();
                isLastPage = true;
            }

    }

    @Override
    public void showLoadMore(FavouriteResponse favouriteResponse) {
        wishListItemAdapter.removeLoadingFooter();
        isLoading = false;
        wishListItemAdapter.addAll(favouriteResponse.getProductWishList());
        wishListItemAdapter.addLoadingFooter();
        if (favouriteResponse.getProductWishList().size() < OFF_SET_LIMIT) {
            wishListItemAdapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    @Override
    public void showLoadMoreError(String error) {
        wishListItemAdapter.removeLoadingFooter();
        isLastPage = true;
    }

    @Override
    public void showFavouriteResult(int position, String message) {
        wishListItemAdapter.removedItem(position);
        if (wishListItemAdapter.getItemCount() == 0) {
            tvError.setText(getResources().getString(R.string.no_wish_list));
            rlEmptyWishlist.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void removeFavourites(int position, int productId) {
        myCartPresenter.removeFavourites(position, String.valueOf(productId));
    }

    @Override
    public void viewFavourites(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_ID, productWishList.get(position).getProductId());
        bundle.putInt(RESTAURANT_ID, productWishList.get(position).getRestaurantId());
        bundle.putString(ITEM_NAME, productWishList.get(position).getProductTitle());
        changeActivityExtras(getActivity(), ViewItemDetails.class, bundle);
    }
}