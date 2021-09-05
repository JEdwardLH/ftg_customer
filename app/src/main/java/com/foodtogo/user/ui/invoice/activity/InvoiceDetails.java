package com.foodtogo.user.ui.invoice.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.invoice.Choice;
import com.foodtogo.user.model.invoice.CustomerDetailArray;
import com.foodtogo.user.model.invoice.InvoiceDetailsResponse;
import com.foodtogo.user.model.invoice.ItemList;
import com.foodtogo.user.ui.invoice.adapter.InvoiceDataModelAdapter;
import com.foodtogo.user.ui.invoice.interfaces.ClickStoreListener;
import com.foodtogo.user.ui.invoice.mvp.InvoiceContractor;
import com.foodtogo.user.ui.invoice.mvp.InvoicePresenter;
import com.foodtogo.user.util.ConversionUtils;
import com.foodtogo.user.util.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class InvoiceDetails extends BaseActivity implements InvoiceContractor.View, ClickStoreListener {

    InvoicePresenter invoicePresenter;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.tv_contact_number)
    TextView tvContactNumber;

    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;

    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;

    @BindView(R.id.tv_order_type)
    TextView tvOrderType;

    @BindView(R.id.tv_payment)
    TextView tvPayment;

    @BindView(R.id.rl_tab_customer)
    TextView rlTabCustomer;

    @BindView(R.id.ll_customer_view)
    LinearLayout llCustomerView;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.tv_sub_total)
    TextView tvSubTotal;

    @BindView(R.id.tv_total_tax)
    TextView tvTotalTax;

    @BindView(R.id.tv_use_wallet)
    TextView tvUseWallet;

    @BindView(R.id.tv_use_offer)
    TextView tvUseOffer;

    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.tv_landmark)
    TextView tvLandMark;

    @BindView(R.id.tv_cancelled_item)
    TextView tvCancelledItem;

    @BindView(R.id.tv_payment_status)
    TextView tvPaymentStatus;

    @BindView(R.id.rl_use_wallet)
    RelativeLayout rlUseWallet;

    @BindView(R.id.rl_use_offer)
    RelativeLayout rlUseOffer;

    @BindView(R.id.rl_cancelled_item)
    RelativeLayout rlCancelledItem;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.nsv_data_view)
    NestedScrollView nestedScrollView;
    private static String ZERO="0";

   // offer_used


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invoicePresenter = new InvoicePresenter(this, appRepository);
        setupToolBar();
        tvTitle.setText(getString(R.string.invoice_details));
        nestedScrollView.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String orderId = bundle.getString(ORDER_TRANSACTION_ID);
            llCustomerView.setVisibility(View.VISIBLE);
            if (isNetworkConnected())
                invoicePresenter.requestInvoiceDetails(orderId);
            else
                showToast(R.string.no_internet_connection);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_invoice;
    }

    @OnClick(R.id.rl_view_customer)
    public void setTabCustomer() {
        rlTabCustomer.setCompoundDrawablesWithIntrinsicBounds(0, 0, ViewUtils.expand(llCustomerView) ? R.drawable.ic_collapse : R.drawable.ic_expand, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        invoicePresenter.close();
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
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    @Override
    public void showInvoiceDetails(InvoiceDetailsResponse invoiceDetailsResponse) {
        nestedScrollView.setVisibility(View.VISIBLE);
        // Customer Details
        CustomerDetailArray customerDetailArray = invoiceDetailsResponse.getCustomerDetailArray();
        tvUserName.setText(customerDetailArray.getCustomeName());
        tvAddress.append(customerDetailArray.getCustomerAddress1());
        tvLandMark.setText(customerDetailArray.getCustomerAddress2());
        tvContactNumber.setText(customerDetailArray.getCustomerMobile());
        tvEmail.setText(customerDetailArray.getCustomerEmail());

        // Order Details
        tvOrderDate.setText(ConversionUtils.getFormatDateTime(invoiceDetailsResponse.getOrderDate()));
        tvOrderNumber.setText(invoiceDetailsResponse.getOrderId());
        tvPayment.setText(invoiceDetailsResponse.getPaytype());
        tvOrderType.setText(invoiceDetailsResponse.getSelfPickup().equals("0") ? DELIVERY : SELF_PICKUP);
        tvPaymentStatus.setText(invoiceDetailsResponse.getPaymentStatus());


        // Amount Details
        tvSubTotal.append(invoiceDetailsResponse.getCurrency());
        tvSubTotal.append(invoiceDetailsResponse.getGrandSubTotal());
        tvTotalTax.setText(String.format("%s%s", invoiceDetailsResponse.getCurrency(), invoiceDetailsResponse.getGrandTaxTotal()));
        tvDeliveryFee.append(invoiceDetailsResponse.getCurrency());
        tvDeliveryFee.append(invoiceDetailsResponse.getDeliveryFee());
        tvTotal.append(invoiceDetailsResponse.getCurrency());
        tvTotal.append(invoiceDetailsResponse.getGrandTotal());
        tvCancelledItem.setText(String.format("- %s%s", invoiceDetailsResponse.getCurrency(), invoiceDetailsResponse.getCancelledItemTotal()));
        rlCancelledItem.setVisibility(invoiceDetailsResponse.getCancelledItemTotal().equals(ZERO)?View.GONE:View.VISIBLE);

        if (invoiceDetailsResponse.getWalletUsed().equals(ZERO)) {
            rlUseWallet.setVisibility(View.GONE);
        } else {
            tvUseWallet.setText(String.format("- %s%s", invoiceDetailsResponse.getCurrency(), invoiceDetailsResponse.getWalletUsed()));
            rlUseWallet.setVisibility(View.VISIBLE);
        }

        /*offer text*/
        if (invoiceDetailsResponse.getOfferUsed().equals(ZERO)) {
            rlUseOffer.setVisibility(View.GONE);
        } else {
            tvUseOffer.setText(String.format("- %s%s", invoiceDetailsResponse.getCurrency(), invoiceDetailsResponse.getOfferUsed()));
            rlUseOffer.setVisibility(View.VISIBLE);
        }

        InvoiceDataModelAdapter adapter = new InvoiceDataModelAdapter(this, invoiceDetailsResponse.getOrderDetailArray());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setInfoClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void showStoreLocation(String storeTitle, String Location,String phone) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_cancelation_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvPhoneLabel = dialog.findViewById(R.id.tv_phone_label);
        final TextView tvPhoneText = dialog.findViewById(R.id.tv_phone_txt);
        final TextView tvCancellation = dialog.findViewById(R.id.tv_cancellation);
        final TextView tvCancellationInfo = dialog.findViewById(R.id.tv_cancellation_info);
        final TextView tvCancellationPolicy = dialog.findViewById(R.id.tv_cancellation_policy);
        tvCancellationInfo.setVisibility(View.GONE
        );
        if(phone!=null && !phone.equals("")){
            tvPhoneLabel.setVisibility(View.VISIBLE);
            tvPhoneText.setVisibility(View.VISIBLE);
            tvPhoneText.setText(phone);
        }
        tvTitle.setText(storeTitle);
        tvCancellation.setText(getResources().getString(R.string.address));
        tvCancellationPolicy.setText(Location);

        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void showItems(ItemList itemList) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_item_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvPriceInfo = dialog.findViewById(R.id.tv_price_info);
        final TextView tvItemTax = dialog.findViewById(R.id.tv_item_tax);
        final TextView tvTaxInfo = dialog.findViewById(R.id.tv_tax_info);
        final TextView tvPreOrder = dialog.findViewById(R.id.tv_pre_order);
        final TextView tvPreorderInfo = dialog.findViewById(R.id.tv_preorder_info);
        final TextView tvQuantityInfo = dialog.findViewById(R.id.tv_quantity_info);
        final TextView tvChoice = dialog.findViewById(R.id.tv_choice);
        final TextView tvChoiceInfo = dialog.findViewById(R.id.tv_choice_info);
        final TextView tvSpecial = dialog.findViewById(R.id.tv_special);
        final TextView tvSpecialInfo = dialog.findViewById(R.id.tv_special_info);
        tvTitle.setText(itemList.getItemName());
        String priceInfo = itemList.getOrdCurrency() + itemList.getOrdUnitPrice();
        tvPriceInfo.setText(priceInfo);
        tvQuantityInfo.setText("" + itemList.getOrdQuantity());
        tvTaxInfo.setText(itemList.getOrdCurrency() + itemList.getOrdTaxAmt());
        tvPreorderInfo.setText(ConversionUtils.getFormatDateTime1(itemList.getPreOrderDate()).toUpperCase());

        StringBuilder stringBuilder = new StringBuilder();
        if (itemList.getChoiceList() != null) {
            tvChoice.setVisibility(itemList.getChoiceList().size() == 0 ? View.GONE : View.VISIBLE);
            tvChoiceInfo.setVisibility(itemList.getChoiceList().size() == 0 ? View.GONE : View.VISIBLE);
            for (int i = 0; i < itemList.getChoiceList().size(); i++) {
                Choice choice = itemList.getChoiceList().get(i);
                stringBuilder.append(i == 0 ? "" : "\n");
                stringBuilder.append(choice.getChoiceName() + " = " + itemList.getOrdCurrency() + choice.getChoiceAmount());
            }
            tvChoiceInfo.setText(stringBuilder.toString());
        } else {
            tvChoice.setVisibility(View.GONE);
            tvChoiceInfo.setVisibility(View.GONE);
        }

        String extraItems = getResources().getString(R.string.include) + SPACE + itemList.getSpecialRequest();
        tvSpecialInfo.setText(extraItems);
        tvSpecialInfo.setVisibility(itemList.getSpecialRequest().equals("") ? View.GONE : View.VISIBLE);
        tvSpecial.setVisibility(itemList.getSpecialRequest().equals("") ? View.GONE : View.VISIBLE);


        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}
