package com.foodtogo.user.ui.myrewards.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.wallet.Reward;
import com.foodtogo.user.model.wallet.RewardsResponse;
import com.foodtogo.user.ui.myrewards.adapter.ListingNavigationAdapter;
import com.foodtogo.user.ui.myrewards.mvp.RewardsContractor;
import com.foodtogo.user.ui.myrewards.mvp.RewardsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyRewardsFragment extends BaseFragment implements RewardsContractor.View {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @BindView(R.id.earned_text)
    TextView earnedTv;

    @BindView(R.id.reward_text)
    TextView rewardTv;

    @BindView(R.id.earned_point)
    TextView earnedPoint;

    @BindView(R.id.reward_point)
    TextView rewardPoint;

    @BindView(R.id.balance_point)
    TextView balancePoint;

    @BindView(R.id.error_view)
    TextView errorView;

    @BindView(R.id.tab_transaction)
    LinearLayout transactionTab;

    @BindView(R.id.main_view)
    LinearLayout mainView;
    public static final String EARNED = "Earned";
    public static final String REWARDED = "Rewarded";
    RewardsPresenter presenter;
    ListingNavigationAdapter adapter;
    List<Reward> rewardedList;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewards, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=view.findViewById(R.id.viewpager);
        tabLayout=view.findViewById(R.id.tabs);
        transactionTab.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.GONE);
        presenter=new RewardsPresenter(this,appRepository);
        presenter.requestTotalRewards();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0){
                    earnedTextView();
                    rewardsInverseView();
                }else{
                    rewardsTextView();
                    earnedInverseView();
                }
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }
    private void earnedTextView() {
        earnedTv.setTextColor(getResources().getColor(R.color.colorWhite));
        earnedTv.setBackground(getResources().getDrawable(R.drawable.rewards_tab_left_orange));
    }

    private void earnedInverseView() {
        earnedTv.setTextColor(getResources().getColor(R.color.black));
        earnedTv.setBackground(getResources().getDrawable(R.drawable.rewards_tab_left_grey));
    }

    private void rewardsTextView() {
        rewardTv.setTextColor(getResources().getColor(R.color.colorWhite));
        rewardTv.setBackground(getResources().getDrawable(R.drawable.rewards_tab_right_orange));
    }

    private void rewardsInverseView() {
        rewardTv.setTextColor(getResources().getColor(R.color.black));
        rewardTv.setBackground(getResources().getDrawable(R.drawable.rewards_tab_right_grey));
    }


    @Override
    public void showTotalRewards(RewardsResponse rewardsResponse) {
        earnedPoint.setText(rewardsResponse.getTotalPoints());
        rewardPoint.setText(rewardsResponse.getRewardedPoints());
        balancePoint.setText(rewardsResponse.getBalancePoints());
        bindValue(rewardsResponse);
    }

    public void bindValue(RewardsResponse rewardsResponse) {
        try {
            tabLayout.setVisibility(View.GONE);
            if (viewPager != null && viewPager.getChildCount() > 0) {
                viewPager.removeAllViews();
            }
            List<Reward> earnedList = rewardsResponse.getEarnedPointsHistory();
            rewardedList = rewardsResponse.getRewardedHistory();
            ListingNavigationAdapter adapter = new ListingNavigationAdapter(getChildFragmentManager());
            adapter.addFragment(TotalRewards.newInstance(earnedList, EARNED), getString(R.string.earned_history));
            adapter.addFragment(TotalRewards.newInstance(rewardedList, REWARDED), getString(R.string.reward_history));
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);
            tabLayout.setupWithViewPager(viewPager);
        }catch (Exception e){
            e.printStackTrace();
        }
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
     showError(message);
    }

    @Override
    public void showError(int message) {
      showError(message);
    }
    @OnClick(R.id.earned_text)
    void earnedTab(){
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
    }
    @OnClick(R.id.reward_text)
    void rewardTab(){
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
    }

}
