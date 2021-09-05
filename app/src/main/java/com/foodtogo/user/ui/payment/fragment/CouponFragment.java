package com.foodtogo.user.ui.payment.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseDialogFragment;
import com.foodtogo.user.model.payment.CouponList;
import com.foodtogo.user.ui.payment.adapter.CouponAdapter;
import com.foodtogo.user.ui.payment.interfaces.OnApplyItemListener;
import com.foodtogo.user.ui.payment.interfaces.OnApplyListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class CouponFragment extends BaseDialogFragment implements OnApplyItemListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_clear_all)
    TextView clearAll;
    @BindView(R.id.tv_title)
    TextView title;

    public OnApplyListener mListener;
    public final static String KEY_COUPON_LIST = "couponList";
    public final static String KEY_COUPON_ID = "coupon_id";
    private ArrayList<CouponList> couponListArrayList;

    public static CouponFragment newInstance(ArrayList<CouponList> couponLists,String couponId) {
        CouponFragment itemFilterFragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_COUPON_LIST, couponLists);
        args.putString(KEY_COUPON_ID,couponId);
        itemFilterFragment.setArguments(args);
        return itemFilterFragment;
    }


    @OnClick(R.id.iv_close)
    public void closeDialog() {
        dismiss();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnApplyListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }




    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_item_list, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(getString(R.string.offers_for_you));
        couponListArrayList = getArguments().getParcelableArrayList(KEY_COUPON_LIST);
        recyclerView.setHasFixedSize(true);
        CouponAdapter adapter = new CouponAdapter(couponListArrayList,getArguments().getString(KEY_COUPON_ID));
        adapter.setOnApplyListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.tv_clear_all)
    void clearOffers(){
        dismiss();
        mListener.clearAll();
    }


    @Override
    public void onClickItemApply(int position) {
        dismiss();
        mListener.onClickApply(position);
    }
}
