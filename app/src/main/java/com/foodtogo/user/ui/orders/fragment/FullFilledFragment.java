package com.foodtogo.user.ui.orders.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.orderdetails.FulfilledDetail;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;
import com.foodtogo.user.ui.orders.activity.OrderActivity;
import com.foodtogo.user.ui.orders.adapter.FullFilledItemAdapter;
import com.foodtogo.user.ui.orders.interfaces.FullFilledListener;
import com.foodtogo.user.ui.orders.mvp.FullFilledOrderContractor;
import com.foodtogo.user.ui.orders.mvp.FullFilledOrderPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.FULFILLED_DETAILS;
import static com.foodtogo.user.base.AppConstants.NO;
import static com.foodtogo.user.base.AppConstants.ORDER_TRANSACTION_ID;
import static com.foodtogo.user.base.AppConstants.YES;


public class FullFilledFragment extends BaseFragment implements FullFilledListener, FullFilledOrderContractor.View {


    public FullFilledFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;

    @BindView(R.id.ll_invoice)
    LinearLayout invoiceLayout;

    List<FulfilledDetail> fulfilledDetails;
    FullFilledOrderPresenter filledOrderPresenter;
    String restaurantRating = "0";
    String orderRating = "0";
    String itemRating = "0";
    int TAB = 0;
    String orderTransactionId;

    /**invoice views*/

    @BindView(R.id.tv_sub_total)
    TextView tvSubTotal;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.tv_total_tax)
    TextView tvTotalTax;

    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;

    @BindView(R.id.tv_use_wallet)
    TextView tvWallet;

    @BindView(R.id.tv_use_offer)
    TextView tvOffer;

    @BindView(R.id.tv_cancelled_item)
    TextView tvCancelledItem;

    @BindView(R.id.rl_use_wallet)
    RelativeLayout rlWallet;

    @BindView(R.id.rl_use_offer)
    RelativeLayout rlOffer;

    @BindView(R.id.rl_cancelled_item)
    RelativeLayout rlCancelledItem;

    private static String ZERO="0";

    /******/

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filledOrderPresenter = new FullFilledOrderPresenter(this, appRepository);
        assert getArguments() != null;
        OrderDetailResponse orderDetailResponse=  getArguments().getParcelable(FULFILLED_DETAILS);
        assert orderDetailResponse != null;
        ArrayList<FulfilledDetail> fulfilledDetailArrayList = orderDetailResponse.getFulfilledDetails();
        orderTransactionId = getArguments().getString(ORDER_TRANSACTION_ID);
        fulfilledDetails = getList(fulfilledDetailArrayList);
        if (fulfilledDetails.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);
            FullFilledItemAdapter adapter = new FullFilledItemAdapter(getActivity(), fulfilledDetails);
            adapter.setFullFilledListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoOrder.setVisibility(View.VISIBLE);
        }
        //invoiceLayout.setVisibility(View.VISIBLE);
        //invoiceViewBinding(orderDetailResponse);
    }

    /** invoice views*/
  /*  void invoiceViewBinding(OrderDetailResponse orderDetailResponse){
        tvTotal.setText(String.format("%s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getGrandTotal()));
        tvSubTotal.setText(String.format("%s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getGrandSubTotal()));
        tvTotalTax.setText(String.format("%s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getGrandTaxTotal()));
        tvDeliveryFee.setText(String.format("%s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getGrandDeliveryFee()));
        tvWallet.setText(String.format("- %s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getWalletUsed()));
        tvOffer.setText(String.format("- %s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getOfferUsed()));
        tvCancelledItem.setText(String.format("- %s%s", orderDetailResponse.getCurrency(), orderDetailResponse.getCancelledItemAmount()));

        rlWallet.setVisibility(orderDetailResponse.getWalletUsed().equals(ZERO)?View.GONE:View.VISIBLE);
        rlOffer.setVisibility(orderDetailResponse.getOfferUsed().equals(ZERO)?View.GONE:View.VISIBLE);
        rlCancelledItem.setVisibility(orderDetailResponse.getCancelledItemAmount().equals(ZERO)?View.GONE:View.VISIBLE);
    }*/
    private List<FulfilledDetail> getList(ArrayList<FulfilledDetail> pendingDetailArrayList) {
        List<FulfilledDetail> newFulfilledList = new ArrayList<>();
        boolean isHeader = true;
        for (int index = 0; index < pendingDetailArrayList.size(); index++) {
            FulfilledDetail fulfilledDetail = pendingDetailArrayList.get(index);
            int restaurantId = fulfilledDetail.getRestaurantId();
            if (ifExist(restaurantId, newFulfilledList)) {
                isHeader = true;
                for (int jIndex = 0; jIndex < pendingDetailArrayList.size(); jIndex++) {
                    FulfilledDetail fulfilledDetailNew = pendingDetailArrayList.get(jIndex);
                    int restaurantIdNew = fulfilledDetailNew.getRestaurantId();
                    if (restaurantId == restaurantIdNew) {
                        fulfilledDetailNew.setShowHeader(isHeader);
                        newFulfilledList.add(fulfilledDetailNew);
                        isHeader = false;
                    }
                }
            }
        }
        return newFulfilledList;
    }

    private boolean ifExist(int restaurantId, List<FulfilledDetail> pendingDetailArrayList) {
        for (int jIndex = 0; jIndex < pendingDetailArrayList.size(); jIndex++) {
            FulfilledDetail pendingDetailNew = pendingDetailArrayList.get(jIndex);
            int restaurantIdNew = pendingDetailNew.getRestaurantId();
            if (restaurantId == restaurantIdNew) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void addReview(int position) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_add_order_res_review);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        final LinearLayout llOrderTab = dialog.findViewById(R.id.ll_order_view);
        final LinearLayout llRestaurantTab = dialog.findViewById(R.id.ll_restaurant_view);
        final LinearLayout llOrderView = dialog.findViewById(R.id.order_layout);
        final LinearLayout llRestaurantView = dialog.findViewById(R.id.restaurant_layout);
        final View orderView = dialog.findViewById(R.id.order_view);
        final View restaurantView = dialog.findViewById(R.id.restaurant_view);
        final View orderViewGrey = dialog.findViewById(R.id.order_view_grey);
        final View restaurantViewGrey = dialog.findViewById(R.id.restaurant_view_grey);
        final ImageView ivOrderRating1 = dialog.findViewById(R.id.iv_order_rating_1);
        final ImageView ivOrderRating2 = dialog.findViewById(R.id.iv_order_rating_2);
        final ImageView ivOrderRating3 = dialog.findViewById(R.id.iv_order_rating_3);
        final ImageView ivOrderRating4 = dialog.findViewById(R.id.iv_order_rating_4);
        final EditText etOrderComment = dialog.findViewById(R.id.et_order_comment);
        final Button btnPostReview = dialog.findViewById(R.id.btn_post_review);

        restaurantRating = "0";
        orderRating = "0";
        TAB = 1;
        llOrderTab.setOnClickListener(v -> {
            TAB = 1;
            llOrderView.setVisibility(View.VISIBLE);
            llRestaurantView.setVisibility(View.GONE);
            restaurantViewGrey.setVisibility(View.VISIBLE);
            orderView.setVisibility(View.VISIBLE);
            restaurantView.setVisibility(View.GONE);
            orderViewGrey.setVisibility(View.GONE);
        });
        llRestaurantTab.setOnClickListener(v -> {
            TAB = 2;
            llOrderView.setVisibility(View.GONE);
            llRestaurantView.setVisibility(View.VISIBLE);
            restaurantViewGrey.setVisibility(View.GONE);
            orderView.setVisibility(View.GONE);
            restaurantView.setVisibility(View.VISIBLE);
            orderViewGrey.setVisibility(View.VISIBLE);
        });
        ivOrderRating1.setOnClickListener(v -> {
            orderRating = "1";
            ivOrderRating1.setImageResource(R.drawable.selected_1);
            ivOrderRating2.setImageResource(R.drawable.unselected_2);
            ivOrderRating3.setImageResource(R.drawable.unselected_3);
            ivOrderRating4.setImageResource(R.drawable.unselected_4);
        });
        ivOrderRating2.setOnClickListener(v -> {
            orderRating = "2";
            ivOrderRating1.setImageResource(R.drawable.unselected_1);
            ivOrderRating2.setImageResource(R.drawable.selected_2);
            ivOrderRating3.setImageResource(R.drawable.unselected_3);
            ivOrderRating4.setImageResource(R.drawable.unselected_4);
        });
        ivOrderRating3.setOnClickListener(v -> {
            orderRating = "3";
            ivOrderRating1.setImageResource(R.drawable.unselected_1);
            ivOrderRating2.setImageResource(R.drawable.unselected_2);
            ivOrderRating3.setImageResource(R.drawable.selected_3);
            ivOrderRating4.setImageResource(R.drawable.unselected_4);
        });
        ivOrderRating4.setOnClickListener(v -> {
            orderRating = "4";
            ivOrderRating1.setImageResource(R.drawable.unselected_1);
            ivOrderRating2.setImageResource(R.drawable.unselected_2);
            ivOrderRating3.setImageResource(R.drawable.unselected_3);
            ivOrderRating4.setImageResource(R.drawable.selected_4);
        });

        final ImageView ivRestaurantRating1 = dialog.findViewById(R.id.iv_restaurant_rating_1);
        final ImageView ivRestaurantRating2 = dialog.findViewById(R.id.iv_restaurant_rating_2);
        final ImageView ivRestaurantRating3 = dialog.findViewById(R.id.iv_restaurant_rating_3);
        final ImageView ivRestaurantRating4 = dialog.findViewById(R.id.iv_restaurant_rating_4);
        final EditText etRestaurantComment = dialog.findViewById(R.id.et_restaurant_comment);
        ivRestaurantRating1.setOnClickListener(v -> {
            restaurantRating = "1";
            ivRestaurantRating1.setImageResource(R.drawable.selected_1);
            ivRestaurantRating2.setImageResource(R.drawable.unselected_2);
            ivRestaurantRating3.setImageResource(R.drawable.unselected_3);
            ivRestaurantRating4.setImageResource(R.drawable.unselected_4);
        });
        ivRestaurantRating2.setOnClickListener(v -> {
            restaurantRating = "2";
            ivRestaurantRating1.setImageResource(R.drawable.unselected_1);
            ivRestaurantRating2.setImageResource(R.drawable.selected_2);
            ivRestaurantRating3.setImageResource(R.drawable.unselected_3);
            ivRestaurantRating4.setImageResource(R.drawable.unselected_4);
        });
        ivRestaurantRating3.setOnClickListener(v -> {
            restaurantRating = "3";
            ivRestaurantRating1.setImageResource(R.drawable.unselected_1);
            ivRestaurantRating2.setImageResource(R.drawable.unselected_2);
            ivRestaurantRating3.setImageResource(R.drawable.selected_3);
            ivRestaurantRating4.setImageResource(R.drawable.unselected_4);
        });
        ivRestaurantRating4.setOnClickListener(v -> {
            restaurantRating = "4";
            ivRestaurantRating1.setImageResource(R.drawable.unselected_1);
            ivRestaurantRating2.setImageResource(R.drawable.unselected_2);
            ivOrderRating3.setImageResource(R.drawable.unselected_3);
            ivRestaurantRating4.setImageResource(R.drawable.selected_4);
        });

        if (fulfilledDetails.get(position).getAlreadyOrderReviewed().equals(YES) || fulfilledDetails.get(position).getSelfPickup().equals(YES)) {
                llOrderTab.setVisibility(View.GONE);
                TAB = 2;
                llOrderView.setVisibility(View.GONE);
                orderView.setVisibility(View.GONE);
                restaurantViewGrey.setVisibility(View.GONE);
                restaurantView.setVisibility(View.VISIBLE);
                orderViewGrey.setVisibility(View.VISIBLE);
                llRestaurantView.setVisibility(View.VISIBLE);
        } else if (fulfilledDetails.get(position).getAlreadyRestaurantReviewed().equals(YES)) {
            llRestaurantTab.setVisibility(View.GONE);
            TAB = 1;
            llOrderView.setVisibility(View.VISIBLE);
            orderView.setVisibility(View.VISIBLE);
            restaurantViewGrey.setVisibility(View.VISIBLE);
            restaurantView.setVisibility(View.GONE);
            orderViewGrey.setVisibility(View.GONE);
            llRestaurantView.setVisibility(View.GONE);
        }

        if (fulfilledDetails.get(position).getCanOrderReview().equals(NO)) {
            llOrderTab.setVisibility(View.GONE);
            TAB = 2;
            llOrderView.setVisibility(View.GONE);
            orderView.setVisibility(View.GONE);
            restaurantViewGrey.setVisibility(View.GONE);
        }


        btnPostReview.setOnClickListener(v -> {
            if (TAB == 1) {
                if (orderRating.equals("0")) {
                    showToast(R.string.select_your_rating);
                } else if (etOrderComment.getText().length() == 0) {
                    showToast(R.string.error_enter_you_comment);
                } else {
                    dialog.dismiss();
                    String storeId = String.valueOf(fulfilledDetails.get(position).getRestaurantId());
                    String deliveryId = String.valueOf(fulfilledDetails.get(position).getDeliveryId());
                    String comment = etOrderComment.getText().toString();
                    filledOrderPresenter.postOrderReview(orderTransactionId, storeId, deliveryId, orderRating, comment);
                }
            } else if (TAB == 2) {
                if (restaurantRating.equals("0")) {
                    showToast(R.string.select_your_rating);
                } else if (etRestaurantComment.getText().length() == 0) {
                    showToast(R.string.error_enter_you_comment);
                } else {
                    dialog.dismiss();
                    String storeId = String.valueOf(fulfilledDetails.get(position).getRestaurantId());
                    String comment = etRestaurantComment.getText().toString();
                    filledOrderPresenter.postStoreReview(storeId, restaurantRating, comment);
                }
            }
        });

        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void addItemReview(int position) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_add_review);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        final ImageView ivRating1 = dialog.findViewById(R.id.iv_rating_1);
        final ImageView ivRating2 = dialog.findViewById(R.id.iv_rating_2);
        final ImageView ivRating3 = dialog.findViewById(R.id.iv_rating_3);
        final ImageView ivRating4 = dialog.findViewById(R.id.iv_rating_4);
        final Button btnPostReview = dialog.findViewById(R.id.btn_post_review);
        final EditText etComment = dialog.findViewById(R.id.et_comment);
        itemRating = "0";
        ivRating1.setOnClickListener(v -> {
            itemRating = "1";
            ivRating1.setImageResource(R.drawable.selected_1);
            ivRating2.setImageResource(R.drawable.unselected_2);
            ivRating3.setImageResource(R.drawable.unselected_3);
            ivRating4.setImageResource(R.drawable.unselected_4);
        });
        ivRating2.setOnClickListener(v -> {
            itemRating = "2";
            ivRating1.setImageResource(R.drawable.unselected_1);
            ivRating2.setImageResource(R.drawable.selected_2);
            ivRating3.setImageResource(R.drawable.unselected_3);
            ivRating4.setImageResource(R.drawable.unselected_4);
        });
        ivRating3.setOnClickListener(v -> {
            itemRating = "3";
            ivRating1.setImageResource(R.drawable.unselected_1);
            ivRating2.setImageResource(R.drawable.unselected_2);
            ivRating3.setImageResource(R.drawable.selected_3);
            ivRating4.setImageResource(R.drawable.unselected_4);
        });
        ivRating4.setOnClickListener(v -> {
            itemRating = "4";
            ivRating1.setImageResource(R.drawable.unselected_1);
            ivRating2.setImageResource(R.drawable.unselected_2);
            ivRating3.setImageResource(R.drawable.unselected_3);
            ivRating4.setImageResource(R.drawable.selected_4);
        });
        btnPostReview.setOnClickListener(v -> {
            if (itemRating.equals("0")) {
                showToast(R.string.select_your_rating);
            } else if (etComment.getText().length() == 0) {
                showToast(R.string.error_enter_you_comment);
            } else {
                dialog.dismiss();
                String itemId = String.valueOf(fulfilledDetails.get(position).getItemId());
                String comment = etComment.getText().toString();
                filledOrderPresenter.postItemReview(itemId, itemRating, comment);
            }
        });

        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
    }


    @Override
    public void postReviewSuccess(String message) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        tvTitle.setText(getString(R.string.success));
        tvMessage.setText(message);
        btnGo.setOnClickListener(v -> {
            dialog.dismiss();
            ((OrderActivity) getActivity()).refreshOrder(1);
        });
        dialog.show();
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
}