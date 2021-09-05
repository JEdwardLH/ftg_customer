package com.foodtogo.user.ui.viewrestaurant.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.restaurant.CategoryBasedItems;
import com.foodtogo.user.model.restaurant.CategoryList;
import com.foodtogo.user.model.restaurant.ItemList;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;
import com.foodtogo.user.model.restaurant.RestaurantInfo;
import com.foodtogo.user.model.restaurant.RestaurantReview;
import com.foodtogo.user.model.restaurant.SubCategoryList;
import com.foodtogo.user.model.restaurant.WorkingHour;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.viewallreviews.activity.ViewAllReviews;
import com.foodtogo.user.ui.viewitemdetails.activity.ViewItemDetails;
import com.foodtogo.user.ui.viewrestaurant.adapter.CategoryBasedItemAdapter;
import com.foodtogo.user.ui.viewrestaurant.adapter.RatingAdapter;
import com.foodtogo.user.ui.viewrestaurant.adapter.RestaurantCategoryAdapter;
import com.foodtogo.user.ui.viewrestaurant.adapter.RestaurantSubCategoryAdapter;
import com.foodtogo.user.ui.viewrestaurant.fragment.ItemFilterFragment;
import com.foodtogo.user.ui.viewrestaurant.interfaces.AddCartListener;
import com.foodtogo.user.ui.viewrestaurant.interfaces.CategoryClickListener;
import com.foodtogo.user.ui.viewrestaurant.interfaces.OnCompleteListener;
import com.foodtogo.user.ui.viewrestaurant.mvp.ViewRestaurantContractor;
import com.foodtogo.user.ui.viewrestaurant.mvp.ViewRestaurantPresenter;
import com.foodtogo.user.util.BadgeUtil;
import com.foodtogo.user.util.DataUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ViewRestaurant extends BaseActivity implements ViewRestaurantContractor.View, AppConstants,
        CategoryClickListener, AddCartListener, CompoundButton.OnCheckedChangeListener, OnCompleteListener {


    ViewRestaurantPresenter viewAllPresenter;

    @BindView(R.id.nsv_data_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.recycler_view_horizontal)
    RecyclerView recyclerViewHorizontal;

    @BindView(R.id.ll_view_restaurant)
    LinearLayout llViewRestaurant;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.et_sub_type)
    EditText etSubType;

    @BindView(R.id.switch_only_veg)
    SwitchCompat switchOnlyVeg;

    @BindView(R.id.tv_delivery_time)
    TextView tvDeliveryTime;

    @BindView(R.id.tv_available_time)
    TextView tvAvailableTime;

    @BindView(R.id.tv_rating)
    TextView tvRating;

    @BindView(R.id.ll_view_category_item)
    LinearLayout llViewCategoryItem;

    @BindView(R.id.spinner_category_type)
    Spinner spinnerCategoryType;

    @BindView(R.id.tv_no_items)
    TextView tvNoItems;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rl_SubCategoryView)
    RelativeLayout rlSubCategoryView;


    @BindView(R.id.tv_tool_desc)
    public TextView tvTitleDesc;

    @BindView(R.id.search_bar)
    public ProgressBar searchBar;

    @BindView(R.id.preferable_time)
    public TextView preferableTime;



    CategoryBasedItemAdapter itemListDataAdapter;
    RestaurantDetailResponse restaurantDetailResponse;

    ArrayList<WorkingHour> workingHourArrayList;

    int restaurantId = 0;
    int reviewPageNo = 1;
    int mainCategoryId = 0;
    int subCategoryId = 0;
    int sortType = 1;
    String searchTxt = "";
    List<ItemList> itemListList = new ArrayList<>();
    RestaurantCategoryAdapter restaurantCategoryAdapter;
    List<CategoryList> categoryLists;
    List<SubCategoryList> subCategoryLists;
    MenuItem itemFilters = null;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;

    String itemType = "";
    String orderBySplOffer = "0";
    String orderByTopOffers = "0";
    String searchCombo = "0";
    String searchHalal = "0";
    ArrayList<String> availableTime = new ArrayList<>();
    JSONObject filterJson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewAllPresenter = new ViewRestaurantPresenter(this, appRepository);
        setRecyclerView();
        llViewRestaurant.setVisibility(View.GONE);
        setupToolBar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTitle.setText(bundle.getString(RESTAURANT_NAME));
            restaurantId = bundle.getInt(RESTAURANT_ID);
            viewAllPresenter.requestRestaurantDetail(restaurantId, reviewPageNo);
        }
        switchOnlyVeg.setOnCheckedChangeListener(this);
        //availableTime = DataUtils.getDefaultAvailableTimes();
        inputFieldFilter();
    }

    private void setRecyclerView() {
        itemListDataAdapter = new CategoryBasedItemAdapter(this, itemListList);
        itemListDataAdapter.setAddCartListener(this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemListDataAdapter);
        itemListDataAdapter.notifyDataSetChanged();
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
            int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
                    .getScrollY()));
            if (diff == 0) {
                if (!isLoading && !isLastPage) {
                    progressBar.setVisibility(View.VISIBLE);
                    isLoading = true;
                    currentPage = currentPage + 1;
                    updateList();
                }
            }
        });
    }

    private void inputFieldFilter() {
        etSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchTxt = s.toString();
                if (itemListList.size() > 0) {
                    //itemListDataAdapter.getFilter().filter(s);
                    currentPage = 1;
                    updateList();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

    }

    @OnClick(R.id.et_sub_type)
    public void setEtSubType() {
        spinnerCategoryType.performClick();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_restaurant;
    }

    @Override
    public void showLoadingView() {
        showProgressDialog();
    }

    @Override
    public void hideLoadingView() {
        progressBar.setVisibility(View.GONE);
        hideProgressDialog();
    }

    @Override
    public void showProgressBar() {
        try {
            progressView = new Dialog(this, R.style.AppCompatProgressStyle);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_progress_normal, null);
            progressView.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressView.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressView.setCancelable(false);
            progressView.setContentView(view);
            progressView.show();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @Override
    public void hideProgressBar() {
        try {
            if (progressView != null)
                progressView.dismiss();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @Override
    public void showSearchProgressBar() {
        searchBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchProgressBar() {
        searchBar.setVisibility(View.INVISIBLE);
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
    public void onViewAll(RestaurantDetailResponse _restaurantDetailResponse,boolean isFilterApplied) {
        this.restaurantDetailResponse = _restaurantDetailResponse;
        llViewRestaurant.setVisibility(View.VISIBLE);
        RestaurantInfo restaurantInfo = restaurantDetailResponse.getRestaurantInfo();
        workingHourArrayList = _restaurantDetailResponse.getWorkingHours();
        tvDeliveryTime.setText(restaurantInfo.getDeliveryTime());
        int ratingCount = restaurantInfo.getRestaurantRating();
        tvRating.setText(ratingCount>0?String.valueOf(ratingCount):"");
        Drawable drawable=ratingCount>0?getResources().getDrawable(R.drawable.ic_rating_without_star):getResources().getDrawable(R.drawable.ic_rating_with_star);
        tvRating.setBackground(drawable);
        tvAvailableTime.setText(restaurantInfo.getRestaurantStatus());
        tvTitleDesc.setVisibility(View.VISIBLE);
        tvTitleDesc.setText(restaurantInfo.getRestaurantLocation());
        categoryLists = new ArrayList<>();
        categoryLists.add(RestaurantDetailResponse.getAllCategory());
        categoryLists.addAll(restaurantDetailResponse.getCategoryList());
        restaurantCategoryAdapter = new RestaurantCategoryAdapter(this, categoryLists);
        restaurantCategoryAdapter.setSelectedItem(-1);
        restaurantCategoryAdapter.setCategoryClick(this);
        recyclerViewHorizontal.setHasFixedSize(true);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHorizontal.setAdapter(restaurantCategoryAdapter);
        /**
         * set available time in filter
         * */
        if(!TextUtils.isEmpty(restaurantDetailResponse.getSelAvailbaleTime())){
            preferableTime.setVisibility(View.VISIBLE);
            preferableTime.setText(String.format("%s %s", getString(R.string.preferable_items), restaurantDetailResponse.getPreferableTime()));
        }
        filterJson = DataUtils.getDefaultFilter(restaurantDetailResponse.getPreferableTime());

        if (categoryLists.size() != 1) {
            itemFilters.setVisible(true);
            llViewCategoryItem.setVisibility(View.VISIBLE);
            subCategoryLists = categoryLists.size() > 0 ? categoryLists.get(0).getSubCategoryList() : new ArrayList<>();
            mainCategoryId = categoryLists.size() > 0 ? categoryLists.get(0).getMainCategoryId() : 0;
            RestaurantSubCategoryAdapter adapter = new RestaurantSubCategoryAdapter(this,
                    R.layout.list_item_restaruant_subcategory, subCategoryLists);
            spinnerCategoryType.setAdapter(adapter);
            spinnerCategoryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    subCategoryId = subCategoryLists.get(pos).getSubCategoryId();
                    String categoryName = subCategoryLists.get(pos).getSubCategoryName();
                    if (!categoryName.equals(etSubType.getText().toString())) {
                        etSubType.setText(categoryName);
                        currentPage = 1;
                        updateList();
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            rlSubCategoryView.setVisibility(subCategoryLists.size() > 0 ? View.VISIBLE : View.GONE);

            itemListList.clear();
            if (_restaurantDetailResponse.getItemLists().size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                tvNoItems.setVisibility(View.GONE);
                isLoading = false;
                itemListList.addAll(_restaurantDetailResponse.getItemLists());
                itemListDataAdapter.notifyDataSetChanged();
                isLastPage = _restaurantDetailResponse.getItemLists().size() < OFF_SET_LIMIT;
            } else {
                recyclerView.setVisibility(View.GONE);
                tvNoItems.setVisibility(View.VISIBLE);
            }


        } else {
            llViewCategoryItem.setVisibility(View.GONE);
            itemFilters.setVisible(false);
            showErrorDialog(1, getResources().getString(R.string.no_items));
        }
    }

    @Override
    public void showCategoryBasedItem(CategoryBasedItems categoryBasedItems) {
        itemListList.clear();
        if (categoryBasedItems.getItemLists().size() > 0) {
            if(mainCategoryId==0){
                restaurantCategoryAdapter.setSelectedItem(0);
                restaurantCategoryAdapter.notifyDataSetChanged();
            }
            recyclerView.setVisibility(View.VISIBLE);
            tvNoItems.setVisibility(View.GONE);
            isLoading = false;
            itemListList.addAll(categoryBasedItems.getItemLists());
            System.out.println("categoryBasedItemsSize:"+itemListList.size());
            itemListDataAdapter.notifyDataSetChanged();
            isLastPage = categoryBasedItems.getItemLists().size() < OFF_SET_LIMIT;
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoItems.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCategoryError(String message) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        tvNoItems.setVisibility(View.VISIBLE);
        tvNoItems.setText(message);
    }

    @Override
    public void showLoadMore(CategoryBasedItems categoryBasedItems) {
        isLoading = false;
        progressBar.setVisibility(View.GONE);
        itemListList.addAll(categoryBasedItems.getItemLists());
        itemListDataAdapter.notifyDataSetChanged();
        isLastPage = categoryBasedItems.getItemLists().size() < OFF_SET_LIMIT;
    }

    @Override
    public void showLoadMoreError(String error) {
        progressBar.setVisibility(View.GONE);
        isLastPage = true;
    }


    @Override
    public void categoryClick(int position) {
        restaurantCategoryAdapter.setSelectedItem(position);
        restaurantCategoryAdapter.notifyDataSetChanged();
        mainCategoryId = categoryLists.get(position).getMainCategoryId();
        if (mainCategoryId == 0) {
            currentPage = 1;
            updateList();
            rlSubCategoryView.setVisibility(View.GONE);
        } else {
            updateSpinner(categoryLists.get(position).getSubCategoryList());
        }
    }

    private void updateSpinner(List<SubCategoryList> subCategoryList) {
        this.subCategoryLists = subCategoryList;
        RestaurantSubCategoryAdapter adapter = new RestaurantSubCategoryAdapter(this,
                R.layout.list_item_restaruant_subcategory, subCategoryLists);
        spinnerCategoryType.setAdapter(adapter);
        rlSubCategoryView.setVisibility(subCategoryLists.size() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_restaurant, menu);
        itemFilters = menu.findItem(R.id.action_filter);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(BadgeUtil.convertLayoutToImage(this, appRepository.getCartCount(), R.drawable.ic_shopping_cart));
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart_action) {
            Bundle bundle = new Bundle();
            bundle.putInt(TAB_POSITION, TAB_CART);
            changeActivityClearBackStack(Home.class, bundle);
            return true;
        } else if (id == R.id.action_info) {
            if (restaurantDetailResponse != null) {
                showInfoPopup();
            }
            return true;
        } else if (id == R.id.action_filter) {

            showFilter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilter() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ItemFilterFragment itemFilterFragment = ItemFilterFragment.newInstance(sortType);
        itemFilterFragment.show(fragmentManager, "item_filter");
    }

    private void showInfoPopup() {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_restaurant_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvFoodDescription = dialog.findViewById(R.id.tv_food_description);
        final TextView tvPreOrder = dialog.findViewById(R.id.tv_pre_order);
        final TextView tvCancellationPolicy = dialog.findViewById(R.id.tv_cancellation_policy);
        final TextView tvRefundStatus = dialog.findViewById(R.id.tv_refund_status);
        final TextView tvReviewTitle = dialog.findViewById(R.id.tv_review_title);
        final TextView tvReviewItems = dialog.findViewById(R.id.tv_review_items);
        final TextView tvViewAll = dialog.findViewById(R.id.tv_view_all);
        final TextView tvCancelAllowed = dialog.findViewById(R.id.tv_cancel_allowed);
        final TextView tvWorkingHours = dialog.findViewById(R.id.tv_working_hours);
        final TextView tvPhoneLabel = dialog.findViewById(R.id.phone_label);
        final TextView tvPhoneNumber = dialog.findViewById(R.id.tv_phone_number);

        final RecyclerView recyclerViewReview = dialog.findViewById(R.id.recycler_view);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
        tvWorkingHours.setOnClickListener(v -> {
            dialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(WORKINGS_HOURS, workingHourArrayList);
            changeActivityExtras(WorkingsHours.class, bundle);
        });


        RestaurantInfo restaurantInfo = restaurantDetailResponse.getRestaurantInfo();
        tvTitle.setText(restaurantInfo.getRestaurantName());
        if(restaurantInfo.getRestaurantPhone()!=null && !restaurantInfo.getRestaurantPhone().equals("")){
            tvPhoneLabel.setVisibility(View.VISIBLE);
            tvPhoneNumber.setVisibility(View.VISIBLE);
            tvPhoneNumber.setText(restaurantInfo.getRestaurantPhone());
        }

        tvFoodDescription.setText(restaurantInfo.getRestaurantDesc());
        tvPreOrder.setText(restaurantInfo.getPreOrder().equals("Yes") ? getResources().getString(R.string.avaiable) : getResources().getString(R.string.not_avaiable) );
        tvCancellationPolicy.setText(restaurantInfo.getCancellationPolicy());
        tvRefundStatus.setText(restaurantInfo.getRefundStatus().equals("Yes") ? getResources().getString(R.string.refund_avaiable): getResources().getString(R.string.refund_not_avaiable));
        tvCancelAllowed.setText(restaurantInfo.getCancelStatus().equals("Yes") ? getResources().getString(R.string.allowed) : getResources().getString(R.string.not_allowed) );

        List<RestaurantReview> restaurantReviews = restaurantDetailResponse.getRestaurantReview();
        tvViewAll.setOnClickListener(v -> {
            dialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(ALL_REVIEWS, restaurantDetailResponse.getRestaurantReview());
            bundle.putInt(RESTAURANT_ID, restaurantId);
            bundle.putString(TITLE, RESTAURANT);
            changeActivityExtras(ViewAllReviews.class, bundle);
        });
        if (restaurantReviews.size() > 0) {
            int limitSize = 0;
            if (restaurantReviews.size() > 4) {
                tvReviewItems.setText(" (4 " +getResources().getString(R.string.from) + restaurantReviews.size() + " "+getResources().getString(R.string.ratings)+")");
                limitSize = 4;
                tvViewAll.setVisibility(View.VISIBLE);
            } else {
                limitSize = restaurantReviews.size();
                tvViewAll.setVisibility(View.GONE);
                tvReviewItems.setText(" (" + restaurantReviews.size() + " "+getResources().getString(R.string.from) +" " + restaurantReviews.size() + " "+getResources().getString(R.string.rating)+")");
            }
            RatingAdapter ratingAdapter = new RatingAdapter(this, restaurantReviews);
            recyclerViewReview.setHasFixedSize(true);
            ratingAdapter.setRatingLimit(limitSize);
            recyclerViewReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerViewReview.setAdapter(ratingAdapter);
        } else {
            tvReviewTitle.setVisibility(View.GONE);
            tvReviewItems.setVisibility(View.GONE);
            recyclerViewReview.setVisibility(View.GONE);
            tvViewAll.setVisibility(View.GONE);
        }


    }

    private void updateList() {
        preferableTime.setVisibility(View.GONE);

        isLoading = true;
        viewAllPresenter.requestRestaurantCategoryBased(searchTxt, restaurantId, mainCategoryId, subCategoryId, currentPage,
                sortType, orderBySplOffer, orderByTopOffers, searchCombo, searchHalal, itemType, availableTime);
    }


    @Override
    public void addCartClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_ID, itemListList.get(position).getItemId());
        bundle.putInt(RESTAURANT_ID, restaurantId);
        bundle.putString(ITEM_NAME, itemListList.get(position).getItemName());
        changeActivityExtras(ViewItemDetails.class, bundle);
    }

    @Override
    public void noItems() {
        recyclerView.setVisibility(View.GONE);
        tvNoItems.setVisibility(View.VISIBLE);
        tvNoItems.setText(getResources().getString(R.string.item_not_found));
    }

    @Override
    public void showItems() {
        recyclerView.setVisibility(View.VISIBLE);
        tvNoItems.setVisibility(View.GONE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        currentPage = 1;
        itemType = isChecked ? "1" : "";
        updateList();
    }


    @Override
    public void onComplete(JSONObject jsonObject, String orderBySplOffer, String orderByTopOffers, String searchCombo,
                           String searchHalal, String itemType, ArrayList<String> availableTime) {
         currentPage=1;
        this.filterJson = jsonObject;
        this.orderBySplOffer = orderBySplOffer;
        this.orderByTopOffers = orderByTopOffers;
        this.searchCombo = searchCombo;
        this.searchHalal = searchHalal;
        this.itemType = itemType;
        this.availableTime = availableTime;
       // if(mainCategoryId==0){
        //    callRestaurantDetailWithFilter();
        //}else{
            updateList();
      //  }
    }

    @Override
    public void onSortBy(int sortBy) {
        sortType = sortBy;
        currentPage = 1;
       // if(mainCategoryId==0){
          //  callRestaurantDetailWithFilter();
      //  }else{
            updateList();
      //  }
    }


}
