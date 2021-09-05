package com.foodtogo.user.ui.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.home.HomeSearchChild;
import com.foodtogo.user.model.home.HomeSearchHead;
import com.foodtogo.user.model.home.HomeSearchResponse;
import com.foodtogo.user.model.home.Offers;
import com.foodtogo.user.model.home.RecentOrders;
import com.foodtogo.user.model.home.RestaurantDataModel;
import com.foodtogo.user.model.home.RestaurantDetail;
import com.foodtogo.user.ui.address.activity.AddAddress;
import com.foodtogo.user.ui.allrestaurant.activity.AllRestaurantActivity;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.home.adapter.DemoInfiniteAdapter;
import com.foodtogo.user.ui.home.adapter.HomeDataModelAdapter;
import com.foodtogo.user.ui.home.adapter.RestaurantDataAdapter;
import com.foodtogo.user.ui.home.adapter.RestaurantPopularDataAdapter;
import com.foodtogo.user.ui.home.adapter.SearchAdapter;
import com.foodtogo.user.ui.home.interfaces.MoreClickLister;
import com.foodtogo.user.ui.home.interfaces.RestaurantClickListener;
import com.foodtogo.user.ui.home.mvp.HomeContractor;
import com.foodtogo.user.ui.home.mvp.HomePresenter;
import com.foodtogo.user.ui.home.pager.InfinitePager;
import com.foodtogo.user.ui.viewall.activity.ViewAll;
import com.foodtogo.user.ui.viewitemdetails.activity.ViewItemDetails;
import com.foodtogo.user.ui.viewrestaurant.activity.ViewRestaurant;
import com.foodtogo.user.util.ConversionUtils;
import com.foodtogo.user.util.NetworkUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements
        HomeContractor.View, MoreClickLister, AppConstants, RestaurantClickListener, DemoInfiniteAdapter.BannerClickListener {

    private HomePresenter homePresenter;

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.recycler_restraunt)
    RecyclerView recyclerRestaurant;

    @BindView(R.id.card_empty_view)
    CardView cardEmptyView;

    @BindView(R.id.nsv_data_view)
    NestedScrollView nsvDataView;

    @BindView(R.id.itemTitle)
    TextView itemTitle;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;

    private InfinitePager infinitePager;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;

    @BindView(R.id.pbSearch)
    ProgressBar pbSearch;

    @BindView(R.id.llNoRecordsFound)
    LinearLayout llNoRecordsFound;

    @BindView(R.id.banner_ll)
    LinearLayout bannerLayout;

    @BindView(R.id.ll_home_page)
    LinearLayout llHomePage;

    @BindView(R.id.tv_food_title)
    TextView tvFoodTitle;

    @BindView(R.id.tv_error)
    TextView tvError;

   private List<RestaurantDetail> featuredRestaurant;

    @BindView(R.id.recycler_restraunt_details)
    RecyclerView allRestaurantRv;

    List<RestaurantDetail> allRestaurantListDetails = null;
    private String topRestaurant = "";

    @OnClick(R.id.ivLocations)
    public void setLocation() {
        Bundle bundle = new Bundle();
        bundle.putString(FROM_CLASS, HOME);
        changeActivityExtras(getActivity(), AddAddress.class, bundle);
    }

    public static Fragment newInstance(){
        Fragment fragment=new HomeFragment();
        return fragment;
    }


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homePresenter = new HomePresenter(this, appRepository);
        if(NetworkUtils.isNetworkConnected(getActivity()))
        homePresenter.requestRestaurant();
        else {
            tvError.setText(getString(R.string.no_internet_connection));
            showError(getString(R.string.no_internet_connection));
        }

        setSearchView();
    }


    private Search search;

    private void setSearchView() {
        search = new Search(getActivity(), etSearch, rvSearch, llHomePage, llNoRecordsFound, pbSearch, homePresenter, new Search.Listener() {

            @Override
            public void onClickStore(HomeSearchHead homeSearchHead) {
                Bundle bundle = new Bundle();
                bundle.putInt(ViewRestaurant.RESTAURANT_ID, homeSearchHead.getStore_id());
                bundle.putString(ViewRestaurant.RESTAURANT_NAME, homeSearchHead.getStore_name());
                changeActivityExtras(getActivity(), ViewRestaurant.class, bundle);
            }

            @Override
            public void onClickItem(HomeSearchHead homeSearchHead, HomeSearchChild homeSearchChild) {
                Bundle bundle = new Bundle();
                bundle.putInt(ITEM_ID, Integer.parseInt(homeSearchChild.getItem_id()));
                bundle.putInt(RESTAURANT_ID, homeSearchHead.getStore_id());
                bundle.putString(ITEM_NAME, homeSearchChild.getItem_name());
                changeActivityExtras(getActivity(), ViewItemDetails.class, bundle);
            }
        });
    }


    @Override
    public void showHomeTopResponse(List<RestaurantDataModel> topRestaurantDataModelList) {
         int All_RESTAURANT=0;
        cardEmptyView.setVisibility(View.GONE);
        nsvDataView.setVisibility(View.VISIBLE);

        List<RecentOrders> allRecentOrdersList = topRestaurantDataModelList.get(0).getAllRecentOrders();
        if(allRecentOrdersList!=null && allRecentOrdersList.size()>0){
            itemTitle.setVisibility(View.VISIBLE);
            recyclerRestaurant.setVisibility(View.VISIBLE);
            RestaurantDataAdapter restaurantDataAdapter = new RestaurantDataAdapter(getActivity(), allRecentOrdersList);
            recyclerRestaurant.setHasFixedSize(true);
            restaurantDataAdapter.setRestaurantListener(this);
            recyclerRestaurant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerRestaurant.setAdapter(restaurantDataAdapter);
        }else{
            itemTitle.setVisibility(View.GONE);
            recyclerRestaurant.setVisibility(View.GONE);
        }


        allRestaurantListDetails = topRestaurantDataModelList.get(1).getAllRestaurantDetailList();
        RestaurantPopularDataAdapter itemListDataAdapter = new RestaurantPopularDataAdapter(getActivity(), allRestaurantListDetails,topRestaurantDataModelList.get(1).getDeliveryStatus(),All_RESTAURANT);
        allRestaurantRv.setHasFixedSize(true);
        itemListDataAdapter.setRestaurantListener(this);
        allRestaurantRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        allRestaurantRv.setAdapter(itemListDataAdapter);
        topRestaurant = topRestaurantDataModelList.get(1).getRestaurantName();
        if(getActivity()!=null) {
            tvFoodTitle.setText(getString(R.string.all_restaurant));
        }
    }

    @Override
    public void showHomeResponse(List<RestaurantDataModel> restaurantDataModelList, Offers offers) {
        featuredRestaurant = restaurantDataModelList.get(3).getAllFoodDetails();
        cardEmptyView.setVisibility(View.GONE);
        nsvDataView.setVisibility(View.VISIBLE);
        llHomePage.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        HomeDataModelAdapter adapter = new HomeDataModelAdapter(getActivity(), restaurantDataModelList);
        adapter.setMoreListener(this);
        adapter.setRestaurantListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        String dateString = ConversionUtils.getFormatDate(Calendar.getInstance().getTime());

        if (!appRepository.getLastShownDate().equals(dateString) && offers != null) {
            dialogOffers = new DialogOffers(getActivity(), offers);
            dialogOffers.show();
            appRepository.setLastShownDate(dateString);
        }

        List<RestaurantDetail> promotionList=restaurantDataModelList.get(3).getAllFoodDetails();
        ArrayList<String> promotionImages=new ArrayList<>();

        if(promotionList.size()>0) {
            for (int i = 0; i < promotionList.size(); i++) {
                promotionImages.add(promotionList.get(i).getRestaurantBanner());
            }

            try {
                if(getActivity()!=null && promotionImages.size()>0) {
                    infinitePager = new InfinitePager(getActivity().getWindow().getDecorView().getRootView(),this);
                    infinitePager.setData(promotionImages);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            bannerLayout.setVisibility(View.VISIBLE);
        }else{
            bannerLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public void showLoadingView() {
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        cardEmptyView.setVisibility(View.VISIBLE);
        nsvDataView.setVisibility(View.GONE);
        showToast(message);
    }

    @Override
    public void showError(int message) {
        cardEmptyView.setVisibility(View.VISIBLE);
        nsvDataView.setVisibility(View.GONE);
        showToast(message);
    }


    @OnClick(R.id.all_restaurant)
    public void setAllRestaurant() {
        changeActivity(getActivity(), AllRestaurantActivity.class);
    }




    @Override
    public void onClickViewAll(String title, int categoryId, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putInt(CATEGORY_ID, categoryId);
        bundle.putString(TYPE, type);
        changeActivityExtras(getActivity(), ViewAll.class, bundle);
    }

    @Override
    public void onClickRestaurant(String restaurantName, int restaurantId) {
        Bundle bundle = new Bundle();
        bundle.putInt(RESTAURANT_ID, restaurantId);
        bundle.putString(RESTAURANT_NAME, restaurantName);
        changeActivityExtras(getActivity(), ViewRestaurant.class, bundle);
    }

    @Override
    public void onClickOrderAgain(String id) {
        /*TODO -Need order id in api*/
        homePresenter.requestRepeatOrder(id);

    }

    DialogOffers dialogOffers;

    @Override
    public void bannerClick(int position) {
        if(featuredRestaurant!=null && featuredRestaurant.size()>0){
            Bundle bundle = new Bundle();
            bundle.putInt(ViewRestaurant.RESTAURANT_ID, featuredRestaurant.get(position).getRestaurantId());
            bundle.putString(ViewRestaurant.RESTAURANT_NAME,featuredRestaurant.get(position).getRestaurantName());
            changeActivityExtras(getActivity(), ViewRestaurant.class, bundle);
        }
    }

    static class DialogOffers {

        Dialog dialog;
        Unbinder unbinder;

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvMessage)
        TextView tvMessage;

        public DialogOffers(Context context, Offers offers) {
            if (context != null) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_offers);

                unbinder = ButterKnife.bind(this, dialog);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setGravity(Gravity.END | Gravity.BOTTOM);

                tvTitle.setText(offers.getTitle());
                tvMessage.setText(offers.getDescription());
            }
        }

        @OnClick(R.id.btnOk)
        public void onClickOk() {
            dialog.dismiss();
        }

        public void show() {
            dialog.show();
        }

        public void destroy() {
            unbinder.unbind();
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onDestroy() {
        if (dialogOffers != null) dialogOffers.destroy();
        super.onDestroy();
    }


    static class Search implements TextWatcher, SearchAdapter.Listener {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        String query;

        @Override
        public void afterTextChanged(Editable s) {
            query = s.toString().trim();
            if (query.isEmpty()) {
                homePresenter.disposeHomeSearch();
                llHomePage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                onResponse(new ArrayList<>());
            } else {
                pbSearch.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                llHomePage.setVisibility(View.GONE);
                homePresenter.homeSearch(s.toString());
            }
        }

        public interface Listener {
            void onClickStore(HomeSearchHead homeSearchHead);

            void onClickItem(HomeSearchHead homeSearchHead, HomeSearchChild homeSearchChild);
        }

        Context activity;
        EditText editText;
        RecyclerView recyclerView;
        ProgressBar pbSearch;
        HomePresenter homePresenter;

        SearchAdapter searchAdapter;

        LinearLayout llNoRecordsFound;
        LinearLayout llHomePage;

        Listener listener;

        Search(Context activity, EditText editText, RecyclerView recyclerView, LinearLayout llHomePage, LinearLayout llNoRecordsFound, ProgressBar pbSearch, HomePresenter homePresenter,
               Listener listener) {
            this.activity = activity;
            this.editText = editText;
            this.llHomePage = llHomePage;
            this.recyclerView = recyclerView;
            this.homePresenter = homePresenter;
            this.llNoRecordsFound = llNoRecordsFound;
            this.pbSearch = pbSearch;
            this.listener = listener;

            llNoRecordsFound.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            pbSearch.setVisibility(View.INVISIBLE);
            editText.addTextChangedListener(this);
        }


        public void onResponse(List<HomeSearchHead> homeSearchHeadList) {
            if (query.isEmpty()) homeSearchHeadList.clear();

            llNoRecordsFound.setVisibility(View.GONE);
            pbSearch.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            List<Object> list = new ArrayList<>();

            for (HomeSearchHead homeSearchHead : homeSearchHeadList) {
                list.add(homeSearchHead);
                list.addAll(homeSearchHead.getHomeSearchChildList());
            }

            searchAdapter = new SearchAdapter(list, this);
            recyclerView.setAdapter(searchAdapter);
        }

        public void showError(String string) {
            recyclerView.setAdapter(null);
            pbSearch.setVisibility(View.INVISIBLE);
            llNoRecordsFound.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClickStore(HomeSearchHead homeSearchHead) {
            listener.onClickStore(homeSearchHead);
        }

        @Override
        public void onClickItem(HomeSearchHead homeSearchHead, HomeSearchChild homeSearchChild) {
            listener.onClickItem(homeSearchHead, homeSearchChild);
        }
    }

    @Override
    public void onSuccessHomeSearchResponse(HomeSearchResponse homeSearchResponse) {
        search.onResponse(homeSearchResponse.getHomeSearchHeadList());
    }

    @Override
    public void showHomeSearchError(String message) {
        search.showError(message);
    }

    @Override
    public void showHomeSearchError(int message) {
        search.showError(getResources().getString(message));
    }

    @Override
    public void showRepeatOrder(String message) {
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_POSITION, TAB_CART);
        changeActivityClearBackStack(getActivity(), Home.class, bundle);
    }

}
