package com.foodtogo.user.ui.refunddetails.fragment;

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
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;
import com.foodtogo.user.ui.refunddetails.adapter.PendingRefundAdapter;

import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.ORDER_ID;
import static com.foodtogo.user.base.AppConstants.PENDING_REFUND;


public class PendingRefund extends BaseFragment{


    public PendingRefund() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;

    PendingRefundAdapter adapter;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        List<RefundDetailResponse>  pendingDetailList= getArguments().getParcelableArrayList(PENDING_REFUND);
        String orderId=getArguments().getString(ORDER_ID);
        if (pendingDetailList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);
            adapter = new PendingRefundAdapter(pendingDetailList,orderId);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            setRecyclerViewAnimation(recyclerView);
            recyclerView.setAdapter(adapter);
            startRecyclerAnimation(recyclerView);
        } else {
            tvNoOrder.setText(getString(R.string.no_pending_refunds));
            recyclerView.setVisibility(View.GONE);
            tvNoOrder.setVisibility(View.VISIBLE);
        }
    }




}