package com.foodtogo.user.ui.allrestaurant.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;
import com.foodtogo.user.model.allrestaurant.AllRestautrantDetail;
import com.foodtogo.user.model.allrestaurant.CategoryList;
import com.foodtogo.user.model.allrestaurant.TabbedMenu;
import com.foodtogo.user.ui.allrestaurant.adapter.TabbedAdapter;
import com.foodtogo.user.ui.allrestaurant.adapter.AllRestaurantAdapter;
import com.foodtogo.user.ui.allrestaurant.fragment.RestaurantFilter;
import com.foodtogo.user.ui.allrestaurant.interfaces.OnApplyFilter;
import com.foodtogo.user.ui.allrestaurant.interfaces.RestaurantDetailListener;
import com.foodtogo.user.ui.allrestaurant.interfaces.TabClickListener;
import com.foodtogo.user.ui.allrestaurant.mvp.AllRestaurantContractor;
import com.foodtogo.user.ui.allrestaurant.mvp.AllRestaurantPresenter;
import com.foodtogo.user.ui.viewrestaurant.activity.ViewRestaurant;
import com.foodtogo.user.util.DataUtils;
import com.foodtogo.user.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class AllRestaurantActivity extends BaseActivity implements
        AllRestaurantContractor.View, AppConstants, RestaurantDetailListener, TabClickListener, OnApplyFilter {


    AllRestaurantPresenter viewAllPresenter;
    TabbedAdapter allCategoryAdapter;


    @BindView(R.id.recycler_view_horizontal)
    RecyclerView recyclerViewHorizontal;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;



    @BindView(R.id.tv_error)
    TextView tvError;

    List<AllRestautrantDetail> restaurantDetailList = new ArrayList<>();
    List<AllRestautrantDetail> restaurantDetailListFiltered = new ArrayList<>();
    List<TabbedMenu> tabbedMenuList = new ArrayList<>();

    private SearchView searchView;
    private AllRestaurantAdapter itemListDataAdapter;

    LinearLayoutManager mLayoutManager;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;

    ArrayList<CategoryList> filterCategory;
    ArrayList<String> filterCategoryIds=new ArrayList<>();
    String filterJSON = "";
    private String searchHalal = "";
    private String orderByDelivery = "";
    private String orderByRating = "";
    private String orderByOffers = "";
    private String type = "All";
    MenuItem itemSearch;
    MenuItem itemFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewAllPresenter = new AllRestaurantPresenter(this, appRepository);
        setRecyclerView();
        setupToolBar();
        tvTitle.setText(getResources().getString(R.string.all_restaurant));
        viewAllPresenter.requestAllRestaurant(PAGE_START);



    }

    private void setRecyclerView() {
        restaurantDetailList = new ArrayList<>();
        itemListDataAdapter = new AllRestaurantAdapter(this, restaurantDetailList);
        recyclerView.setHasFixedSize(true);
        itemListDataAdapter.setItemDetailsListener(this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
                new Handler().postDelayed(() -> Refresh(), 1000);
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
        if (searchView != null) {
            searchView.setVisibility(View.VISIBLE);
        }
        hideProgress();
    }

    @Override
    public void showError(String message) {
        noItemView(message);
        showToast(message);
    }

    @Override
    public void showError(int message) {
        noItemView(getResources().getString(message));
        showToast(message);
    }


    @Override
    public void onViewAll(AllRestaurantResponse allRestaurantResponse) {
        //category
        tabbedMenuList = DataUtils.getDefaultMenu(this);
        allCategoryAdapter = new TabbedAdapter(this, tabbedMenuList);
        allCategoryAdapter.setTabClickListener(this);
        recyclerViewHorizontal.setHasFixedSize(true);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHorizontal.setAdapter(allCategoryAdapter);


        restaurantDetailList.addAll(allRestaurantResponse.getAllRestautrantDetails());
        itemListDataAdapter = new AllRestaurantAdapter(this, restaurantDetailList);
        itemListDataAdapter.setItemDetailsListener(this);
        recyclerView.setAdapter(itemListDataAdapter);
        itemListDataAdapter.addLoadingFooter();
        startRecyclerAnimation(recyclerView);
        if (allRestaurantResponse.getAllRestautrantDetails().size() < OFF_SET_LIMIT) {
            itemListDataAdapter.removeLoadingFooter();
            isLastPage = true;
        }

        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        if (recyclerViewHorizontal.getVisibility() == View.GONE) {
            recyclerViewHorizontal.setVisibility(View.VISIBLE);
            recyclerViewHorizontal.startAnimation(slideUp);
        }
        filterCategory = DataUtils.getFormatCategory(this, allRestaurantResponse.getCategoryList());
        filterJSON = DataUtils.getRestaurantDefaultFilter().toString();


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
    public void categoryBasedResponse(AllRestaurantResponse allRestaurantResponse) {

        isLastPage = false;
        restaurantDetailList.addAll(allRestaurantResponse.getAllRestautrantDetails());
        itemListDataAdapter = new AllRestaurantAdapter(this, restaurantDetailList);
        itemListDataAdapter.setItemDetailsListener(this);
        recyclerView.setAdapter(itemListDataAdapter);
        itemListDataAdapter.addLoadingFooter();
        startRecyclerAnimation(recyclerView);
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
    public void itemClick(AllRestautrantDetail allRestautrantDetail) {
        Bundle bundle = new Bundle();
        bundle.putString(RESTAURANT_NAME, allRestautrantDetail.getRestaurantName());
        bundle.putInt(RESTAURANT_ID, allRestautrantDetail.getRestaurantId());
        changeActivityExtras(ViewRestaurant.class, bundle);
    }

    @Override
    public void noItemView() {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(getString(R.string.no_restaurant_found_home));
        recyclerView.setVisibility(View.GONE);
    }

    public void noItemView(String error) {
         if(itemSearch!=null && itemFilter!=null){
             itemSearch.setVisible(false);
             itemFilter.setVisible(false);
         }
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(error);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showItem() {
        tvError.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if(itemSearch!=null && itemFilter!=null){
            itemFilter.setVisible(true);
            itemSearch.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        } else if (item.getItemId() == R.id.action_search) {
            return true;
        } else if (item.getItemId() == R.id.action_filter) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RestaurantFilter itemFilterFragment = RestaurantFilter.newInstance(filterCategory, filterJSON);
            itemFilterFragment.show(fragmentManager, "item_filter");
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        itemSearch= menu.findItem(R.id.action_search);
        itemFilter= menu.findItem(R.id.action_filter);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setVisibility(View.GONE);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        EditText searchEditText =  searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white_transperancy));

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                itemListDataAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                itemListDataAdapter.getFilter().filter(query);
                return false;
            }
        });


        return true;
    }


    @Override
    public void onComplete(ArrayList<CategoryList> categoryListArrayList, String jsonObject,ArrayList<String> categoryIds) {
        filterCategory = categoryListArrayList;
        filterCategoryIds=categoryIds;
        filterJSON = jsonObject;
        Log.d("filterJSON",""+filterJSON);
        currentPage = 1;
        restaurantDetailList.clear();
        itemListDataAdapter.notifyDataSetChanged();
        restaurantDetailListFiltered.clear();
        tabbedMenuList.get(0).setActive(false);
        allCategoryAdapter.notifyDataSetChanged();
        int tabId = tabbedMenuList.get(0).getTabId();
        setValue(false,tabId);
        type="";
        Refresh();
    }

    private void Refresh() {
        viewAllPresenter.onViewCategoryBasedItemRequest(type, currentPage, filterCategory, filterJSON,
                searchHalal, orderByDelivery, orderByRating, orderByOffers,filterCategoryIds);
    }

    @Override
    public void onClickTab(boolean isChecked, int position) {
        currentPage = 1;

        if(tabbedMenuList.size()>1)
        for(int i=0;i<tabbedMenuList.size();i++){
          tabbedMenuList.get(i).setActive(false);
        }
        type = tabbedMenuList.get(position).getTabMenu();
        tabbedMenuList.get(position).setActive(isChecked);
        int tabId = tabbedMenuList.get(position).getTabId();
        allCategoryAdapter.notifyDataSetChanged();
        restaurantDetailList.clear();
        itemListDataAdapter.notifyDataSetChanged();
        restaurantDetailListFiltered.clear();
        setOtherTabFalse();
        setValue(isChecked, tabId);
        Refresh();
    }

    private void setOtherTabFalse() {
        for(int i=2;i<6;i++){
            setValue(false,i);
        }
    }


    private void setValue(boolean isChecked, int tabId) {
        switch (tabId) {
            case 1:
                type = "All";
                break;
            case 2:
                orderByOffers = isChecked ? "1" : "";
                break;
            case 3:
                orderByDelivery = isChecked ? "1" : "";
                break;
            case 4:
                orderByRating = isChecked ? "1" : "";
                break;
            case 5:
                searchHalal = isChecked ? "1" : "";
                break;
        }
    }
}
