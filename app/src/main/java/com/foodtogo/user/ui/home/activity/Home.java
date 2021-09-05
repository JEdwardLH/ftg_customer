package com.foodtogo.user.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.home.NavDrawerItem;
import com.foodtogo.user.ui.address.activity.AddAddress;
import com.foodtogo.user.ui.allrestaurant.activity.AllRestaurantActivity;
import com.foodtogo.user.ui.help.fragment.HelpFragment;
import com.foodtogo.user.ui.home.adapter.NavigationDrawerAdapter;
import com.foodtogo.user.ui.home.fragment.HomeFragment;
import com.foodtogo.user.ui.home.mvp.HomeActivityContractor;
import com.foodtogo.user.ui.home.mvp.HomeActivityPresenter;
import com.foodtogo.user.ui.mycart.fragment.MyCartFragment;
import com.foodtogo.user.ui.myreviews.fragment.MyReviewsFragment;
import com.foodtogo.user.ui.myrewards.fragment.MyRewardsFragment;
import com.foodtogo.user.ui.orderhistory.fragment.OrderHistoryFragment;
import com.foodtogo.user.ui.referfriend.fragment.ReferFriendFragment;
import com.foodtogo.user.ui.settings.activity.SettingActivity;
import com.foodtogo.user.ui.settings.activity.SettingMenuActivity;
import com.foodtogo.user.ui.track.activity.LiveTrackActivity;
import com.foodtogo.user.ui.wallet.fragment.WalletFragment;
import com.foodtogo.user.ui.wishlist.fragment.MyWishList;
import com.foodtogo.user.ui.youraddress.fragment.YourAddressFragment;
import com.foodtogo.user.util.BadgeUtil;
import com.foodtogo.user.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Home extends BaseActivity implements AppConstants, HomeActivityContractor.View {

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @BindView(R.id.iv_profile_blur)
    ImageView ivProfileBlur;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.tv_profile_name)
    TextView tvProfileName;

    @BindView(R.id.tv_cart_count)
    TextView tvCartCount;

    @BindView(R.id.tv_profile_email)
    TextView tvProfileEmail;

    @BindView(R.id.iv_mycart)
    ImageView ivMycart;

    @BindView(R.id.iv_fav)
    ImageView ivFav;

    @BindView(R.id.iv_track)
    ImageView ivTrack;

    @BindView(R.id.iv_wallet)
    ImageView ivWallet;

    @BindView(R.id.ll_home_un_selected)
    LinearLayout llHomeUnSelected;

    @BindView(R.id.ll_home_selected)
    LinearLayout llHomeSelected;

    @BindView(R.id.wallet_tv)
    TextView walletTabTv;

    @BindView(R.id.track_tv)
    TextView trackTabTv;

    @BindView(R.id.search_tv)
    TextView searchTabTv;

    @BindView(R.id.cat_tv)
    TextView cartTabTv;

    @BindView(R.id.tv_home)
    TextView homeTabTv;

    HomeActivityPresenter homeActivityPresenter;
    private static final String MENU_3_NAME = "Profile";

    public static int selectedPosition = 0;
    private static String[] arrayNavigationItems = null;
    private static Integer[] sliderMenuDefaultImg = new Integer[]{R.drawable.ic_fav,
            R.drawable.ic_order_history,
            R.drawable.ic_your_address, R.drawable.ic_reviews, R.drawable.ic_rewards,  R.drawable.ic_wallet,
            R.drawable.ic_refer_friend,
            R.drawable.ic_help, R.drawable.ic_about, R.drawable
            .ic_signout};
    private static Integer[] sliderMenuSelectedImg = new Integer[]{R.drawable.ic_fav, R.drawable
            .ic_order_history, R.drawable.ic_your_address, R.drawable.ic_reviews, R.drawable.ic_rewards,
            R.drawable.ic_wallet, R.drawable.ic_refer_friend, R.drawable.ic_help, R.drawable.ic_about,
            R.drawable.ic_signout};
    DrawerLayout mDrawerLayout;
    NavigationDrawerAdapter adapter;
    NavigationDrawerAdapter.NavigationCallBack navigationCallBack;
    ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    int itemPosition = 0;
    private static final int REQUEST_CODE = 100;
    boolean isActiveHome = false;
    boolean isManageAddress = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivityPresenter = new HomeActivityPresenter(this, appRepository);
        adapterClick();
        setToolBarConfig();
        getIntentValues();
        String areyou = getColoredSpanned(getResources().getString(R.string.are_you), "#FFFFFF");
        String hungry = getColoredSpanned(getResources().getString(R.string.app_name_questions), "#FEF000");
        tvTitle.setText(Html.fromHtml(areyou + " " + hungry));
        Bundle extras = getIntent().getExtras();
        int position = 0;
        if (extras != null && extras.containsKey("OrderTab"))
            position = extras.getInt("OrderTab");

        if (position == 1) {
            //add or replace fragment F2 in container
            displayView(position);
        }

        setTabText();
    }

    void setTabText(){
        cartTabTv.setText(getResources().getString(R.string.action_cart));
        searchTabTv.setText(getResources().getString(R.string.nav_search));
        trackTabTv.setText(getResources().getString(R.string.nav_track));
        walletTabTv.setText(getResources().getString(R.string.nav_wallet));
        homeTabTv.setText(getResources().getString(R.string.nav_home));
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProfile();
        updateCartCount();
    }

    public void updateCartCount() {
        BadgeUtil.setBadgeCartCount(tvCartCount, appRepository.getCartCount());
    }


    private void showProfile() {
        try {
            tvProfileName.setText(appRepository.getUserName());
            tvProfileEmail.setText(appRepository.getUserEmail());
            GlideUtils.showBlurImage(this, ivProfileBlur, R.drawable.profile_default_pic, appRepository.getUserAvatar());
            GlideUtils.showRoundImage(this, ivProfile, R.drawable.profile_default_pic, appRepository.getUserAvatar());
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
            GlideUtils.showDefaultBlurImage(this, ivProfileBlur, R.drawable.profile_default_pic);
            GlideUtils.showDefaultRoundImage(this, ivProfile, R.drawable.profile_default_pic);
        }


    }

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    private void getIntentValues() {
        int position = getIntent().getIntExtra(TAB_POSITION, TAB_HOME);
        if (position == 3) {
            isManageAddress = false;
        }
        selectedPosition = position;
        displayView(position);
    }




    private void setToolBarConfig() {
        try {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
            }
            tvTitle = toolbar.findViewById(R.id.tv_tool_title);
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            toolbar.setNavigationIcon(R.drawable.ic_toolbar_foodtogo);
            toolbar.setTitle("");
            mDrawerLayout = findViewById(R.id.drawer_layout);
            mDrawerList = findViewById(R.id.drawerList);
            arrayNavigationItems = getResources().getStringArray(R.array.nav_drawer_labels);
            adapter = new NavigationDrawerAdapter(Home.this, getData(), navigationCallBack);
            mDrawerList.setAdapter(adapter);

            mDrawerToggle = new ActionBarDrawerToggle(Home.this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                    hideKeyboard();
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    hideKeyboard();
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                    toolbar.setAlpha(1 - slideOffset / 2);
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerLayout.post(() -> mDrawerToggle.syncState());

            //Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getTheme());
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            //mDrawerToggle.setH;
            mDrawerToggle.setToolbarNavigationClickListener(v -> {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
            });
        } catch (NullPointerException nullPointerException) {
            Log.e(EXCEPTION_NULL_POINTER, nullPointerException.toString());
        } catch (Exception exception) {
            Log.e(EXCEPTION, exception.toString());
        }
    }

    @OnClick(R.id.iv_settings)
    public void setClickSettings() {
        changeActivity(SettingActivity.class);
    }

    @OnClick(R.id.ll_mycart)
    public void setMyCart() {
        changeTab(TAB_CART);
        displayView(TAB_CART);
    }


    @OnClick(R.id.iv_profile)
    public void setProfile() {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, MENU_3_NAME);
        bundle.putInt(TAB_POSITION, 2);
        changeActivityExtras(SettingMenuActivity.class, bundle);
    }

    @OnClick(R.id.tv_profile_name)
    public void setProfileName() {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, MENU_3_NAME);
        bundle.putInt(TAB_POSITION, 2);
        changeActivityExtras(SettingMenuActivity.class, bundle);
    }

    @OnClick(R.id.tv_profile_email)
    public void setProfileEmail() {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, MENU_3_NAME);
        bundle.putInt(TAB_POSITION, 2);
        changeActivityExtras(SettingMenuActivity.class, bundle);
    }

    @OnClick(R.id.ll_fav)
    public void setFav() {
        changeActivity(AllRestaurantActivity.class);
    }


    @OnClick(R.id.ll_home)
    public void setHome() {
        openHome();
    }

    public void openHome() {
        changeTab(TAB_HOME);
        displayView(TAB_HOME);
    }


    @OnClick(R.id.ll_track)
    public void setTrack() {
        changeTab(TAB_TRACK);
        displayView(TAB_TRACK);
    }

    @OnClick(R.id.ll_wallet)
    public void setWallet() {
        changeTab(TAB_WALLET);
        displayView(TAB_WALLET);
    }


    private void adapterClick() {
        navigationCallBack = position -> {
            mDrawerLayout.closeDrawers();
            displayView(position);
        };
    }

    public void showReferFriend() {
        displayView(6);
    }


    public void displayView(int position) {
        hideMenu();
        String areyou = getColoredSpanned(getString(R.string.are_you), "#FFFFFF");
        String hungry = getColoredSpanned(getString(R.string.app_name_questions), "#FEF000");
        itemPosition = position;
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MyWishList();
                hideMenu();
                tvTitle.setText(getResources().getString(R.string.nav_item_favourites));
                changeTab(TAB_DISABLE);
                break;
            case 1:
            case 13:
                fragment = new OrderHistoryFragment();
                hideMenu();
                tvTitle.setText(getResources().getString(R.string.nav_item_order_history));
                changeTab(TAB_TRACK);
                break;
            case 2:
                fragment = new YourAddressFragment();
                tvTitle.setText(getResources().getString(R.string.nav_item_your_address));
                hideMenu();
                changeTab(TAB_DISABLE);
                break;

            case 3:
                fragment = new MyReviewsFragment();
                tvTitle.setText(getResources().getString(R.string.nav_item_review));
                hideMenu();
                changeTab(TAB_DISABLE);
                break;
            case 4:
                fragment = new MyRewardsFragment();
                tvTitle.setText(getResources().getString(R.string.nav_item_rewards));
                hideMenu();
                changeTab(TAB_DISABLE);
                break;
          /*  case 6://6
                fragment = new MyOffersFragment();
                tvTitle.setText(getResources().getString(R.string.nav_item_offers));
                hideMenu();
                changeTab(TAB_DISABLE);
                break;*/
            case 5:
                fragment = new WalletFragment();
                tvTitle.setText(getResources().getString(R.string.nav_item_wallet));
                hideMenu();
                changeTab(TAB_WALLET);
                break;

            case 6:
                fragment = new ReferFriendFragment();
                tvTitle.setText(getResources().getString(R.string.nav_item_refer_friend));
                hideMenu();
                changeTab(TAB_DISABLE);
                break;
            case 7:
                fragment = new HelpFragment();
                Bundle bundle = new Bundle();
                bundle.putString(FROM_CLASS, HELP);
                fragment.setArguments(bundle);
                tvTitle.setText(getResources().getString(R.string.nav_item_help));
                hideMenu();
                changeTab(TAB_DISABLE);
                break;

            case 9:
                homeActivityPresenter.logout();
                break;
            case 10:
                changeTab(TAB_CART);
                fragment = new MyCartFragment();
                tvTitle.setText(getResources().getString(R.string.my_cart));
                hideMenu();
                break;
            case 12:
                changeTab(TAB_HOME);
                fragment = new HomeFragment();
                showMenu();
                tvTitle.setText(Html.fromHtml(areyou + " " + hungry));
                break;
            case 14:
                changeTab(TAB_WALLET);
                fragment = new WalletFragment();
                hideMenu();
                tvTitle.setText(getResources().getString(R.string.nav_wallet));
                break;
            case 17:
                showLiveTrackScreen(getIntent());
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    private void hideMenu() {
        isActiveHome = false;
    }

    private void showMenu() {
        isActiveHome = true;
    }


    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        for (int i = 0; i < arrayNavigationItems.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(arrayNavigationItems[i]);
            navItem.setImgDefault(sliderMenuDefaultImg[i]);
            navItem.setImgSelected(sliderMenuSelectedImg[i]);
            data.add(navItem);
        }
        return data;
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override

    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
        } else if (isActiveHome) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.exit_text), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        } else {
            openHome();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_menu) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.tv_tool_title)
    public void setToolTitle() {
        Bundle bundle = new Bundle();
        bundle.putString(FROM_CLASS, HOME);
        changeActivityForResultExtras(AddAddress.class, REQUEST_CODE, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            openHome();
        }
    }

    private void changeTab(int tabPosition) {
        switch (tabPosition) {
            case TAB_CART://12
                ivMycart.setImageResource(R.drawable.ic_cart_selected);
                ivFav.setImageResource(R.drawable.ic_search_unselected);
                ivTrack.setImageResource(R.drawable.ic_track_unselected);
                ivWallet.setImageResource(R.drawable.ic_wallet_unselected);
                llHomeUnSelected.setVisibility(View.VISIBLE);
                llHomeSelected.setVisibility(View.GONE);
                break;
            case 11://13
                ivMycart.setImageResource(R.drawable.ic_cart_unselected);
                ivFav.setImageResource(R.drawable.ic_search_selected);
                ivTrack.setImageResource(R.drawable.ic_track_unselected);
                ivWallet.setImageResource(R.drawable.ic_wallet_unselected);
                llHomeUnSelected.setVisibility(View.VISIBLE);
                llHomeSelected.setVisibility(View.GONE);
                break;
            case TAB_DISABLE://14
                case TAB_HOME:
                ivMycart.setImageResource(R.drawable.ic_cart_unselected);
                ivFav.setImageResource(R.drawable.ic_search_unselected);
                ivTrack.setImageResource(R.drawable.ic_track_unselected);
                ivWallet.setImageResource(R.drawable.ic_wallet_unselected);
                llHomeUnSelected.setVisibility(View.GONE);
                llHomeSelected.setVisibility(View.VISIBLE);
                break;
            case TAB_TRACK://15
                ivMycart.setImageResource(R.drawable.ic_cart_unselected);
                ivFav.setImageResource(R.drawable.ic_search_unselected);
                ivTrack.setImageResource(R.drawable.ic_track_selected);
                ivWallet.setImageResource(R.drawable.ic_wallet_unselected);
                llHomeUnSelected.setVisibility(View.VISIBLE);
                llHomeSelected.setVisibility(View.GONE);
                break;
            case TAB_WALLET://16
                ivMycart.setImageResource(R.drawable.ic_cart_unselected);
                ivFav.setImageResource(R.drawable.ic_search_unselected);
                ivTrack.setImageResource(R.drawable.ic_track_unselected);
                ivWallet.setImageResource(R.drawable.ic_wallet_selected);
                llHomeUnSelected.setVisibility(View.VISIBLE);
                llHomeSelected.setVisibility(View.GONE);
                break;

            case TAB_LIVE_TRACK://17
                ivMycart.setImageResource(R.drawable.ic_cart_unselected);
                ivFav.setImageResource(R.drawable.ic_search_unselected);
                ivTrack.setImageResource(R.drawable.ic_track_unselected);
                ivWallet.setImageResource(R.drawable.ic_wallet_unselected);
                llHomeUnSelected.setVisibility(View.VISIBLE);
                llHomeSelected.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public void onSuccess(String message) {
        appRepository.setCartCount(0);
        showSuccessDialog(1, message);
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
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }


    private void showLiveTrackScreen(Intent intent) {
        String orderId = intent.getExtras().getString(ORDER_ID);
        String restaurantId = intent.getExtras().getString(RESTAURANT_ID);
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderId);
        bundle.putInt(RESTAURANT_ID, Integer.valueOf(restaurantId));
        changeTab(TAB_TRACK);

        changeActivityExtras(LiveTrackActivity.class, bundle);
        hideMenu();
    }


}