package com.foodtogo.user.ui.mycart.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.itemdetails.Choice;
import com.foodtogo.user.model.mycart.AddedItemDetail;
import com.foodtogo.user.model.mycart.CartChoice;
import com.foodtogo.user.model.mycart.CartResponse;
import com.foodtogo.user.model.mycart.StoreLocations;
import com.foodtogo.user.ui.common.Common;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.mycart.adapter.CartHeaderItemAdapter;
import com.foodtogo.user.ui.mycart.adapter.ChoiceChangeAdapter;
import com.foodtogo.user.ui.mycart.interfaces.ChoiceListener;
import com.foodtogo.user.ui.mycart.interfaces.MyCartListener;
import com.foodtogo.user.ui.mycart.mvp.MyCartContractor;
import com.foodtogo.user.ui.mycart.mvp.MyCartPresenter;
import com.foodtogo.user.ui.shippingaddress.activity.ShippingAddress;
import com.foodtogo.user.ui.viewitemdetails.activity.ViewItemDetails;
import com.foodtogo.user.util.ConversionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MyCartFragment extends BaseFragment implements MyCartContractor.View, MyCartListener, AppConstants, ChoiceListener {

    @BindView(R.id.recycler_items)
    RecyclerView recyclerView;

    @BindView(R.id.nsv_data_view)
    NestedScrollView nsvDataView;

    String totalAmountUSD = "";

    @BindView(R.id.card_empty_view)
    RelativeLayout errorCardView;

    @BindView(R.id.error_view)
    RelativeLayout errorView;

    @BindView(R.id.tv_sub_total)
    TextView tvSubTotal;

    @BindView(R.id.tv_total_tax)
    TextView tvTotalTax;

    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.ivPeakHours)
    ImageView ivPeakHours;
    public static boolean offerShow = true;

    private List<String> minimumOrderError = null;
    private List<String> preOrderError = null;
    private List<String> quantityError = null;
    List<Choice> choiceLists = new ArrayList<>();
    String totalAmount = "";
    String deliveryFee = "";
    String extraCharge = "";
    String peekHourInfo = "";
    boolean isPeakHour = false;
    String subTotal = "";
    String totalTax = "";
    String currencySymbol = "";
    ArrayList<StoreLocations> storeLocationsList;
    ChoiceChangeAdapter choiceAdapter;
    CartHeaderItemAdapter adapter;

    public MyCartFragment() {
        // Required empty public constructor
    }

    MyCartPresenter myCartPresenter;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mycart, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        offerShow = true;
        myCartPresenter = new MyCartPresenter(this, appRepository);
        errorView.setVisibility(View.GONE);
        nsvDataView.setVisibility(View.GONE);
        myCartPresenter.requestCart();

        setLocaleLang();
    }
    void setLocaleLang(){
        Locale locale = new Locale(appRepository.getLanguageCode());
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        context.createConfigurationContext(configuration);
    }

    @OnClick(R.id.ivPeakHours)
    public void onClickPeakHours() {
        Common.buildToolTip(getActivity(), ivPeakHours, peekHourInfo, extraCharge, currencySymbol).show();
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
        System.out.println("message:"+message);
        if (message.equals(getString(R.string.no_items_cart))) {
            errorCardView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.VISIBLE);
            nsvDataView.setVisibility(View.GONE);
            appRepository.setCartCount(0);
            try {
                ((Home) getActivity()).updateCartCount();
            } catch (Exception e) {
                Log.e(EXCEPTION, e.toString());
            }
        } else {
            showToast(message);
        }
    }

    @OnClick(R.id.btn_check_out)
    public void setCheckOut() {
        if (minimumOrderError.size() > 0) {
            showToast(minimumOrderError.get(0));
        } else if (preOrderError.size() > 0) {
            showToast(preOrderError.get(0));
        } else if (quantityError.size() > 0) {
            showToast(quantityError.get(0));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(TOTAL_AMOUNT, totalAmount);
            bundle.putString(TOTAL_AMOUNT_USD, totalAmountUSD);
            bundle.putString(DELIVERY_FEE, deliveryFee);
            bundle.putString(EXTRA_CHARGE, extraCharge);
            bundle.putString(PEEK_INFO, peekHourInfo);
            bundle.putBoolean(IS_PEAK_HOUR, isPeakHour);
            bundle.putString(CURRENCY_SYMBOL, currencySymbol);
            bundle.putString(SUB_TOTAL, subTotal);
            bundle.putString(TOTAL_TAX,totalTax);
            bundle.putParcelableArrayList(STORE_LOCATIONS, storeLocationsList);
            changeActivityExtras(getActivity(), ShippingAddress.class, bundle);
        }
    }

    @OnClick(R.id.btn_add_some_food)
    public void setAddSomeFood() {
        try {
            ((Home) getActivity()).openHome();
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    @Override
    public void showCartResponse(CartResponse cartResponse) {
        storeLocationsList = cartResponse.getStoreLocations();
        minimumOrderError = cartResponse.getMinimumOrderError();
        preOrderError = cartResponse.getPreOrderError();
        quantityError = cartResponse.getQuantityError();
        if (cartResponse.getCartDetails().size() > 0) {
            errorCardView.setVisibility(View.GONE);
            nsvDataView.setVisibility(View.VISIBLE);
            subTotal = cartResponse.getCartSubTotal();
            totalTax = cartResponse.getCarTaxTotal();
            deliveryFee = cartResponse.getDeliveryFee();
            extraCharge = cartResponse.getExtraFee();
            peekHourInfo = cartResponse.getPeakHourInfo();
            isPeakHour = cartResponse.isPeakHour();
            currencySymbol = cartResponse.getCurrencyCode();
            totalAmount = cartResponse.getTotalCartAmount();
            totalAmountUSD = cartResponse.getTotalCartAmountUsd();
            tvSubTotal.setText(String.format("%s%s%s", currencySymbol, SPACE, cartResponse.getCartSubTotal()));
            tvTotalTax.setText(String.format("%s%s%s", currencySymbol, SPACE, cartResponse.getCarTaxTotal()));
            tvDeliveryFee.setText(String.format("%s%s%s", currencySymbol, SPACE, cartResponse.getDeliveryFee()));
            tvTotal.setText(String.format("%s%s%s", currencySymbol, SPACE, cartResponse.getTotalCartAmount()));
            try {
                ((Home) getActivity()).updateCartCount();
            } catch (Exception e) {
                Log.e(EXCEPTION, e.toString());
            }
            recyclerView.setHasFixedSize(true);
            Log.i("showCartResponse: ", "" + cartResponse.getCartDetails().size());
            adapter = new CartHeaderItemAdapter(getActivity(), cartResponse.getCartDetails());
            adapter.setAddCartListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);

            ivPeakHours.setVisibility(isPeakHour ? View.VISIBLE : View.GONE);

            appRepository.setCartCount(cartResponse.getTotalCartCount());
            try {
                ((Home) getActivity()).updateCartCount();
            } catch (Exception e) {
                Log.e(EXCEPTION, e.toString());
            }


        } else {
            appRepository.setCartCount(0);
            try {
                ((Home) getActivity()).updateCartCount();
            } catch (Exception e) {
                Log.e(EXCEPTION, e.toString());
            }
            errorCardView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.VISIBLE);
            nsvDataView.setVisibility(View.GONE);
        }

        if (cartResponse.getLoyaltyInfo() != null && !cartResponse.getLoyaltyInfo().equals("")) {
            dialogOffers = new DialogOffers(getActivity(), cartResponse.getLoyaltyInfo());
            dialogOffers.show();
        }

    }

    @Override
    public void showProgressBar() {
        showProgress();
    }

    @Override
    public void hideProgressBar() {
        hideProgress();
    }


    @Override
    public void clickItem(AddedItemDetail addedItemDetail) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_cart_item_info);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        tvTitle.setText(addedItemDetail.getProductName());
        final TextView tvItemAmount = dialog.findViewById(R.id.tv_item_amount);
        String itemPrice=String.valueOf(Float.valueOf(addedItemDetail.getCartUnitPrice())* addedItemDetail.getCartQuantity());
        tvItemAmount.setText(String.format("%s%s", addedItemDetail.getCartCurrency(), itemPrice));
        final EditText etNote = dialog.findViewById(R.id.et_note);
        final TextView tvChoiceTitle = dialog.findViewById(R.id.tv_choice_title);
        final TextView tvTax = dialog.findViewById(R.id.tv_item_tax);
        tvTax.setText(String.format("%s%s", addedItemDetail.getCartCurrency(), addedItemDetail.getCartTax()));
        etNote.setText(addedItemDetail.getCartSplRequest() == null ? "" : addedItemDetail.getCartSplRequest());
        final RecyclerView rvToppings = dialog.findViewById(R.id.recycler_view);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            hideKeyboard(v);
            dialog.dismiss();
        });
        dialog.show();


        //etNote.setText(addedItemDetail.get);
        choiceLists.clear();
        List<CartChoice> cartChoiceList = addedItemDetail.getCartChoices();
        List<Choice> allChoiceList = addedItemDetail.getItemChoices();
        for (int i = 0; i < allChoiceList.size(); i++) {
            Choice existChoice = allChoiceList.get(i);
            Choice choice = new Choice();
            choice.setChoiceSelected(false);
            choice.setChoiceId(existChoice.getChoiceId());
            choice.setChoiceName(existChoice.getChoiceName());
            choice.setChoicePrice(existChoice.getChoicePrice());
            choice.setChoiceCurrency(currencySymbol);
            for (int j = 0; j < cartChoiceList.size(); j++) {
                if (existChoice.getChoiceId() == cartChoiceList.get(j).getChoiceId()) {
                    choice.setChoiceSelected(true);
                }
            }
            choiceLists.add(choice);
        }
        tvChoiceTitle.setVisibility(allChoiceList.size() == 0 ? View.GONE : View.VISIBLE);
        rvToppings.setVisibility(allChoiceList.size() == 0 ? View.GONE : View.VISIBLE);
        choiceAdapter = new ChoiceChangeAdapter(getActivity(), choiceLists);
        choiceAdapter.setChoiceListener(this);
        rvToppings.setHasFixedSize(true);
        rvToppings.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvToppings.setAdapter(choiceAdapter);

        final Button btnUpdateCart = dialog.findViewById(R.id.btn_update_cart);
        btnUpdateCart.setOnClickListener(v -> {
            hideKeyboard(v);
            dialog.dismiss();
            int productId = addedItemDetail.getProductId();
            int cartId = addedItemDetail.getCartId();
            List<Integer> choiceList = getChoiceList();
            myCartPresenter.updateCartChoice(productId, cartId, choiceList, etNote.getText().toString());
        });

        final Button btnViewItem = dialog.findViewById(R.id.btn_view_item);
        btnViewItem.setOnClickListener(v -> {
            hideKeyboard(v);
            dialog.dismiss();
            int storeId = addedItemDetail.getRestaurantId();
            int productId = addedItemDetail.getProductId();
            Bundle bundle = new Bundle();
            bundle.putInt(ITEM_ID, productId);
            bundle.putInt(RESTAURANT_ID, storeId);
            bundle.putString(ITEM_NAME, addedItemDetail.getProductName());
            changeActivityExtras(getActivity(), ViewItemDetails.class, bundle);

        });
    }

    private ArrayList<Integer> getChoiceList() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int index = 0; index < choiceLists.size(); index++) {
            if (choiceLists.get(index).isChoiceSelected()) {
                arrayList.add(choiceLists.get(index).getChoiceId());
            }
        }
        return arrayList;
    }

    @Override
    public void clickPlusBtn(int cartId, int quantity) {
        myCartPresenter.updateCartQuantity(cartId, quantity);
    }

    @Override
    public void clickMinusBtn(int cartId, int quantity) {
        myCartPresenter.updateCartQuantity(cartId, quantity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void clickPreOrder(int storeId, String storeName) {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_date_time_picker);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tv_title);
        final TextView tvMessage = dialog.findViewById(R.id.tv_message);
        final ImageView ivClose = dialog.findViewById(R.id.iv_close);
        final LinearLayout llDateView = dialog.findViewById(R.id.ll_date_view);
        final LinearLayout llTimerView = dialog.findViewById(R.id.ll_timer_view);
        final TextView tvDate = dialog.findViewById(R.id.tv_date);
        final TextView tvTime = dialog.findViewById(R.id.tv_time);
        final View timeView = dialog.findViewById(R.id.time_view);
        final View dateView = dialog.findViewById(R.id.date_view);
        final View timeViewGrey = dialog.findViewById(R.id.time_view_grey);
        final View dateViewGrey = dialog.findViewById(R.id.date_view_grey);
        final DatePicker datePicker = dialog.findViewById(R.id.date_picker);
        final TimePicker timePicker = dialog.findViewById(R.id.time_picker);
        final Button btnPreOrder = dialog.findViewById(R.id.btn_pre_order);
        Calendar calendar = Calendar.getInstance();
        tvDate.setText(ConversionUtils.getFormatDate(calendar.getTime()));
        tvTime.setText(ConversionUtils.getFormatTime(calendar.getTime()));
        try {
            datePicker.setMinDate(calendar.getTimeInMillis());
        }catch (Exception e){

        }
        dialog.setCancelable(false);
        tvTitle.setText(getString(R.string.pre_booking));
        tvMessage.setText(storeName);
        llDateView.setOnClickListener(v -> {
            dateViewGrey.setVisibility(View.GONE);
            timeView.setVisibility(View.GONE);
            dateView.setVisibility(View.VISIBLE);
            timeViewGrey.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
            timePicker.setVisibility(View.GONE);
        });
        llTimerView.setOnClickListener(v -> {
            dateViewGrey.setVisibility(View.VISIBLE);
            timeView.setVisibility(View.VISIBLE);
            dateView.setVisibility(View.GONE);
            timeViewGrey.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            timePicker.setVisibility(View.VISIBLE);
        });
        btnPreOrder.setOnClickListener(v -> {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
            } else {
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());

            }
            String preOrderDate = ConversionUtils.getFormatDateTime(calendar.getTime());
            myCartPresenter.updatePreOrder(storeId, preOrderDate);
            dialog.dismiss();
        });
        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });


        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (datePicker1, year, month, dayOfMonth) -> {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(year, month, dayOfMonth, timePicker.getHour(), timePicker.getMinute());
            } else {
                calendar.set(year, month, dayOfMonth, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            }

            tvDate.setText(ConversionUtils.getFormatDate(calendar.getTime()));
        });


        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
            } else {
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());

            }
            tvTime.setText(ConversionUtils.getFormatTime(calendar.getTime()));
        });
        dialog.show();
    }

    @Override
    public void clickRemovePreOrder(int storeId) {
        myCartPresenter.removePreOrder(String.valueOf(storeId));
    }


    @Override
    public void clickRemoveChoice(int productId, int cartId, int choiceId) {
        List<Integer> choiceList = new ArrayList<>();
        choiceList.add(choiceId);
        myCartPresenter.removeItemChoice(productId, cartId, choiceList);
    }

    @Override
    public void clickRemoveCart(int cartId) {
        myCartPresenter.removeCartItem(cartId);
    }

    @Override
    public void showErrorMessage(String message) {
        showToast(message);
    }

    @Override
    public void onClickChoiceItem(int position, boolean isChecked) {
        choiceLists.get(position).setChoiceSelected(isChecked);
        choiceAdapter.updateItem(position);
    }

    private DialogOffers dialogOffers;


    static class DialogOffers {

        Dialog dialog;
        Unbinder unbinder;

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvMessage)
        TextView tvMessage;

        public DialogOffers(Context context, String offers) {
            if(context!=null) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_offers);

                unbinder = ButterKnife.bind(this, dialog);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                tvTitle.setText("");
                tvTitle.setVisibility(View.GONE);
                tvMessage.setText(offers);
            }
        }

        @OnClick(R.id.btnOk)
        public void onClickOk() {
            dialog.dismiss();
        }

        public void show() {
            if(offerShow){
                dialog.show();
                offerShow=false;
            }
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

}