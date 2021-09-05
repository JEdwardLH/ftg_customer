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
import com.foodtogo.user.model.myreviews.ItemReviewList;
import com.foodtogo.user.ui.myreviews.adapter.ReviewItemAdapter;
import com.foodtogo.user.ui.myreviews.interfaces.OnChildItemListener;
import com.foodtogo.user.ui.myreviews.interfaces.ReviewItemClickListener;
import com.foodtogo.user.ui.viewitemdetails.activity.ViewItemDetails;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.ITEM_ID;
import static com.foodtogo.user.base.AppConstants.ITEM_NAME;
import static com.foodtogo.user.base.AppConstants.OFF_SET_LIMIT;
import static com.foodtogo.user.base.AppConstants.REVIEWS_ITEMS;


public class MyReviewsItem extends BaseFragment implements ReviewItemClickListener {


    public MyReviewsItem() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    ArrayList<ItemReviewList> itemReviewLists;
    ReviewItemAdapter adapter;


    private boolean isLoading = false;
    private boolean isLastPage = false;

    private OnChildItemListener mParentListener;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemReviewLists = getArguments().getParcelableArrayList(REVIEWS_ITEMS);
        if (itemReviewLists.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.GONE);
            setRecyclerView();
        } else {
            tvNoOrder.setText(getString(R.string.no_items_reviews));
            recyclerView.setVisibility(View.GONE);
            tvNoOrder.setVisibility(View.VISIBLE);
        }
    }


    public void loadMore(ArrayList<ItemReviewList> itemReviewLists) {
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


    @Override
    public void clickItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_ID, itemReviewLists.get(position).getProductId());
        // bundle.putInt(RESTAURANT_ID, itemReviewLists.get(position).get);
        bundle.putString(ITEM_NAME, itemReviewLists.get(position).getItemTitle());
        changeActivityExtras(getActivity(), ViewItemDetails.class, bundle);
    }

    private void setRecyclerView() {
        adapter = new ReviewItemAdapter(getActivity(), itemReviewLists);
        adapter.setReviewItemListener(this);
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
        if (itemReviewLists.size() < OFF_SET_LIMIT) {
            adapter.removeLoadingFooter();
            isLastPage = true;
        }
    }

    private void loadNextPage() {
        mParentListener.refreshItem();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof OnChildItemListener) {
            mParentListener = (OnChildItemListener) getParentFragment();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChildItemListener");
        }
    }


    public void onDetach() {
        super.onDetach();
        mParentListener = null;
    }


}