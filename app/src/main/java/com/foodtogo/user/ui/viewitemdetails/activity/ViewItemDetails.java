package com.foodtogo.user.ui.viewitemdetails.activity;

import android.app.AlertDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.itemdetails.Choice;
import com.foodtogo.user.model.itemdetails.ItemReviews;
import com.foodtogo.user.model.itemdetails.ItemSpecificatioon;
import com.foodtogo.user.model.itemdetails.ItemtInfo;
import com.foodtogo.user.model.itemdetails.RelatedItem;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.restaurant.ResponseAddCart;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.viewallreviews.activity.ViewAllReviews;
import com.foodtogo.user.ui.viewitemdetails.adapter.ChoiceAdapter;
import com.foodtogo.user.ui.viewitemdetails.adapter.ItemSliderAdapter;
import com.foodtogo.user.ui.viewitemdetails.adapter.RelatedRestaurantDataAdapter;
import com.foodtogo.user.ui.viewitemdetails.interfaces.AddRelatedItemCartListener;
import com.foodtogo.user.ui.viewitemdetails.interfaces.ChoiceItemListener;
import com.foodtogo.user.ui.viewitemdetails.mvp.ViewItemDetailsContractor;
import com.foodtogo.user.ui.viewitemdetails.mvp.ViewItemDetailsPresenter;
import com.foodtogo.user.ui.viewitemdetails.service.ImageLoadingService;
import com.foodtogo.user.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import ss.com.bannerslider.Slider;


public class ViewItemDetails extends BaseActivity implements ViewItemDetailsContractor.View, AppConstants,
        ChoiceItemListener, AddRelatedItemCartListener, View.OnClickListener {


    ViewItemDetailsPresenter viewItemDetailsPresenter;

    @BindView(R.id.iv_fav)
    CheckBox ivFav;

    @BindView(R.id.ll_add_cart_view)
    LinearLayout llAddCartView;

    @BindView(R.id.ll_update_cart_view)
    LinearLayout llUpdateCartView;

    @BindView(R.id.tv_item_name)
    TextView tvItemName;

    @BindView(R.id.ll_related_foods)
    LinearLayout llRelatedFoods;

    @BindView(R.id.tv_item_description)
    TextView tvItemDescription;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;

    @BindView(R.id.tv_specification_name)
    TextView tvSpecificationName;

    @BindView(R.id.tv_specification_description)
    TextView tvSpecificationDescription;

    @BindView(R.id.tv_discount)
    TextView tvDiscount;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_discount_striked)
    TextView tvDiscountStriked;

    @BindView(R.id.tv_stock_details)
    TextView tvStockDetails;

    @BindView(R.id.tv_toppings)
    TextView tvToppings;

    @BindView(R.id.recycler_view_toppings)
    RecyclerView rvToppings;

    @BindView(R.id.et_note)
    EditText etNote;

    @BindView(R.id.btn_plus)
    Button btnPlus;

    @BindView(R.id.btn_minus)
    Button btnMinus;

    @BindView(R.id.tv_quantity)
    TextView tvQuantity;

    @BindView(R.id.tv_item_info)
    TextView tvItemInfo;

    @BindView(R.id.tv_total_price_info)
    TextView tvTotalPriceInfo;

    @BindView(R.id.tv_related_foods)
    TextView tvRelatedFoods;

    @BindView(R.id.recycler_view_list)
    RecyclerView rvRelatedItem;

    @BindView(R.id.rl_item_details)
    LinearLayout rlItemDetails;

    @BindView(R.id.banner_slider1)
    Slider slider;

    @BindView(R.id.content_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.add_cart_view)
    LinearLayout addCartView;

    @BindView(R.id.tv_tax)
    TextView tvTax;

    @BindView(R.id.offer_till_card)
    LinearLayout offerTillCv;

    @BindView(R.id.day_lay)
    LinearLayout dayLay;

    @BindView(R.id.day_tv)
    TextView dayTv;

    @BindView(R.id.hour_tv)
    TextView hourTv;

    @BindView(R.id.min_tv)
    TextView minTv;

    @BindView(R.id.sec_tv)
    TextView secTv;

    @BindView(R.id.glucten_img)
    ImageView gluctenImg;

    @BindView(R.id.lactos_img)
    ImageView lactosImg;

    @BindView(R.id.nuts_img)
    ImageView nutsImg;

    @BindView(R.id.chilli_img)
    ImageView chilliImg;




    int restaurantId = 0;
    int totalAvailable = 0;
    int itemId;
    int reviewPageNo = 1;
    double singleItemPrice = 0.00;
    String currencySymbol = "";
    double choiceTotalPrice = 0;
    int cartQuantity=0;

    List<Choice> itemListList;
    ChoiceAdapter choiceAdapter;

    List<RelatedItem> relatedItems = new ArrayList<>();
    boolean isRelatedItem = false;
    RelatedRestaurantDataAdapter restaurantDataAdapter;

    ArrayList<ItemReviews> reviewsArrayList;

    String cartId;
    ItemtInfo itemtInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewItemDetailsPresenter = new ViewItemDetailsPresenter(this, appRepository);
        ivFav.setOnClickListener(this);
        rlItemDetails.setVisibility(View.INVISIBLE);
        addCartView.setVisibility(View.INVISIBLE);
        setupToolBar();
        Slider.init(new ImageLoadingService(this));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTitle.setText(bundle.getString(ITEM_NAME));
            itemId = bundle.getInt(ITEM_ID);
            restaurantId = bundle.getInt(RESTAURANT_ID);
            viewItemDetailsPresenter.requestItemDetail(itemId, reviewPageNo);
        }

    }

    private void clearExistValues() {
        totalAvailable = 0;
        reviewPageNo = 1;
        singleItemPrice = 0.00;
        currencySymbol = "";
        choiceTotalPrice = 0;
        etNote.setText("");
        tvQuantity.setText("1");

    }


    @Override
    public int getLayout() {
        return R.layout.activity_view_item_details;
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
    public void showProgressBar() {
        showProgress();
    }

    @Override
    public void hideProgressBar() {
        hideProgress();
    }


    @Override
    public void showError(String message) {
        showErrorDialog(0, message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    @OnClick(R.id.add_to_cart)
    public void setAddToCart() {
       /* if(itemtInfo.getCartType().equals(NEW_CART))
            if (!tvStockDetails.getText().toString().equals(SOLD_OUT)){
            showDialog();
        }else{
        }*/
        addCart();
    }
    void addCart(){
        List<Integer> choiceList = new ArrayList<>();
        if (!tvStockDetails.getText().toString().equals(SOLD_OUT)) {
            for (int index = 0; index < itemListList.size(); index++) {
                if (itemListList.get(index).isChoiceSelected()) {
                    choiceList.add(itemListList.get(index).getChoiceId());
                }
            }
            viewItemDetailsPresenter.requestAddCart(restaurantId, itemId, tvQuantity.getText().toString(), choiceList, etNote.getText().toString());
        }
    }

    void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewItemDetails.this);
        builder.setMessage(getString(R.string.cart_clear_info));
        builder.setPositiveButton(R.string.no_view_cart, (dialog, id) -> {
            // User clicked OK button
            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.yes_start, (dialog, id) -> {
            // User cancelled the dialog
            dialog.dismiss();
            addCart();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.btn_plus)
    public void setBtnPlus() {
        int qty = Integer.valueOf(tvQuantity.getText().toString());
        if (qty < totalAvailable) {
            qty += 1;

            //updateCart(qty);
            //viewItemDetailsPresenter.updateCartQuantity(Integer.valueOf(cartId), qty);
            List<Integer> choiceList = new ArrayList<>();
            if (!tvStockDetails.getText().toString().equals(SOLD_OUT)) {
                for (int index = 0; index < itemListList.size(); index++) {
                    if (itemListList.get(index).isChoiceSelected()) {
                        choiceList.add(itemListList.get(index).getChoiceId());
                    }
                }
                viewItemDetailsPresenter.requestAddCart(restaurantId, itemId, String.valueOf(qty), choiceList, etNote.getText().toString());
            }
        } else {
            showError(getString(R.string.order_limit_reached));
        }
    }


    @Override
    public void onClickChoiceItem(int position, boolean isChecked) {
         ArrayList<Integer> choiceIds=new ArrayList<>();
        if (!tvStockDetails.getText().toString().equals(SOLD_OUT)) {
            itemListList.get(position).setChoiceSelected(isChecked);
            choiceAdapter.updateItem(position);
            choiceTotalPrice = 0;
            for (int index = 0; index < itemListList.size(); index++) {
                if (itemListList.get(index).isChoiceSelected()) {
                    choiceIds.add(itemListList.get(index).getChoiceId());
                    choiceTotalPrice = choiceTotalPrice + Double.valueOf(itemListList.get(index).getChoicePrice());
                }
            }

            viewItemDetailsPresenter.requestToppingsApi(String.valueOf(restaurantId),String.valueOf(itemtInfo.getItemId()),choiceIds);


        } else {
            itemListList.get(position).setChoiceSelected(false);
            choiceAdapter.updateItem(position);
        }
    }

    @Override
    public void updateToppings(String quantity) {
        llAddCartView.setVisibility(quantity.equals("0")?View.VISIBLE:View.GONE);
        llUpdateCartView.setVisibility(quantity.equals("0")?View.GONE:View.VISIBLE);
        tvQuantity.setText(quantity.equals("0")?"1":quantity);
    }
   /* private void updateCart(int qty) {
       // String itemInfo = getResources().getString(R.string.add) + SPACE + qty + SPACE + getResources().getString(R.string.to_cart);

        double amountTotal = singleItemPrice * qty;
        amountTotal = amountTotal + (choiceTotalPrice * qty);
        String totalPrice = currencySymbol + SPACE + amountTotal + "";
        tvTotalPriceInfo.setText(getResources().getString(R.string.view_cart));
        tvItemInfo.setText(qty + " " + (qty > 1 ? getResources().getString(R.string.to_items) : getResources().getString(R.string.to_item)) + "  |  " + totalPrice);
    }
*/


    @OnClick(R.id.btn_minus)
    public void setBtnMinus() {
        int qty = Integer.valueOf(tvQuantity.getText().toString());
        if (qty == 1) {

        } else if (qty > 1) {
            qty -= 1;
            List<Integer> choiceList = new ArrayList<>();
            if (!tvStockDetails.getText().toString().equals(SOLD_OUT)) {
                for (int index = 0; index < itemListList.size(); index++) {
                    if (itemListList.get(index).isChoiceSelected()) {
                        choiceList.add(itemListList.get(index).getChoiceId());
                    }
                }
                viewItemDetailsPresenter.requestAddCart(restaurantId, itemId, String.valueOf(qty), choiceList, etNote.getText().toString());
            }

            // updateCart(qty);
            //viewItemDetailsPresenter.updateCartQuantity(Integer.valueOf(cartId), qty);
        }
    }

    @OnClick(R.id.tv_total_price_info)
    public void setAddCart() {
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_POSITION, TAB_CART);
        changeActivityClearBackStack(Home.class, bundle);
    }


    @Override
    public void showItemDetails(ResponseItemDetails responseItemDetails) {
        reviewsArrayList = responseItemDetails.getItemReviews();
        rlItemDetails.setVisibility(View.VISIBLE);
        addCartView.setVisibility(View.VISIBLE);
        itemtInfo = responseItemDetails.getItemtInfo();
        ArrayList<String> itemInclude= checkItemInclude(itemtInfo);

        if(itemInclude.size()>0){
            gluctenImg.setVisibility(itemInclude.contains("1")?View.VISIBLE:View.GONE);
            lactosImg.setVisibility(itemInclude.contains("2")?View.VISIBLE:View.GONE);
            nutsImg.setVisibility(itemInclude.contains("3")?View.VISIBLE:View.GONE);
            chilliImg.setVisibility(itemInclude.contains("4")?View.VISIBLE:View.GONE);
        }

        String itemIfo = itemtInfo.getItemContent()!=null?itemtInfo.getItemName() + "(" + itemtInfo.getItemContent() + ")":itemtInfo.getItemName();
        tvItemName.setText(itemIfo);
        tvItemDescription.setText(itemtInfo.getItemDesc());
        currencySymbol = itemtInfo.getItemCurrency();

        List<ItemSpecificatioon> itemSpecificationList = responseItemDetails.getItemtInfo().getItemSpecificatioon();
        if (itemSpecificationList.size() > 0) {
            tvSpecificationName.setText(itemSpecificationList.get(0).getSpecificationTitle());
            tvSpecificationDescription.setText(itemSpecificationList.get(0).getSpecificationDescription());
        } else {
            tvSpecificationName.setVisibility(View.GONE);
            tvSpecificationDescription.setVisibility(View.GONE);
        }
        showImageSlide(responseItemDetails.getItemtInfo().getItemImage());
        String taxInfo = getResources().getString(R.string.exclude) + SPACE + itemtInfo.getItemTax();
        tvTax.setText(String.format("%s%%", taxInfo));
        tvTax.setVisibility(itemtInfo.getItemHasTax().equals(YES) ? View.VISIBLE : View.GONE);

        if (itemtInfo.getItemHasDiscount().equals(YES)) {
            String discountPercent = itemtInfo.getItemDiscountPercent() + "%\noff";
            String priceDetails = currencySymbol + itemtInfo.getItemDiscountPrice();
            String discountStrikeDetails = currencySymbol + itemtInfo.getItemOriginalPrice();
            String itemInfo = getResources().getString(R.string.add) + SPACE + 1 + SPACE + getResources().getString(R.string.to_cart);
            tvPrice.setText(priceDetails);
            singleItemPrice = Double.valueOf(itemtInfo.getItemDiscountPrice());
            tvDiscountStriked.setText(discountStrikeDetails);
            tvDiscountStriked.setPaintFlags(tvDiscountStriked.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvDiscount.setText(discountPercent);
            tvItemInfo.setText(itemInfo);
            tvTotalPriceInfo.setText(getResources().getString(R.string.view_cart));
        } else {
            tvDiscountStriked.setVisibility(View.GONE);
            tvDiscount.setVisibility(View.GONE);
            String priceDetails = currencySymbol + itemtInfo.getItemOriginalPrice();
            String itemInfo = getResources().getString(R.string.add) + SPACE + 1 + SPACE + getResources().getString(R.string.to_cart);
            singleItemPrice = Double.valueOf(itemtInfo.getItemOriginalPrice());
            tvPrice.setText(priceDetails);
            tvItemInfo.setText(itemInfo);
            tvTotalPriceInfo.setText(getResources().getString(R.string.view_cart));
        }

        if (itemtInfo.getItemAvailablity().equals(AVAILABLE)) {
            ivFav.setEnabled(true);
            totalAvailable = itemtInfo.getItemQuantity();
            //String stockDetails = itemtInfo.getItemQuantity() + SPACE + getResources().getString(R.string.item_available);
            //tvStockDetails.setText(stockDetails);
        } else {
            String stockDetails = getResources().getString(R.string.sold_out);
            tvStockDetails.setText(stockDetails);
            btnPlus.setEnabled(false);
            btnMinus.setEnabled(false);
            ivFav.setEnabled(false);
            addCartView.setVisibility(View.GONE);
            tvQuantity.setText("1");
        }

        if (itemtInfo.getItemIsFavourite().equals(NOT_FAVOURITE)) {
            ivFav.setChecked(false);
        } else {
            ivFav.setChecked(true);
        }
        itemListList = responseItemDetails.getChoices();
        if (itemListList.size() > 0) {
            tvToppings.setVisibility(View.VISIBLE);
            choiceAdapter = new ChoiceAdapter(this, itemListList);
            choiceAdapter.setCategoryClick(this);
            rvToppings.setHasFixedSize(true);
            rvToppings.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvToppings.setAdapter(choiceAdapter);
            tvToppings.setVisibility(View.VISIBLE);
        } else {
            rvToppings.setVisibility(View.GONE);
            tvToppings.setVisibility(View.GONE);
        }

        relatedItems = responseItemDetails.getRelatedItems();
        llRelatedFoods.setVisibility(relatedItems.size() == 0 ? View.GONE : View.VISIBLE);
        restaurantDataAdapter = new RelatedRestaurantDataAdapter(this, relatedItems);
        rvRelatedItem.setHasFixedSize(true);
        restaurantDataAdapter.setAddCartListener(this);
        rvRelatedItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRelatedItem.setAdapter(restaurantDataAdapter);
        ViewUtils.scrollToTop(nestedScrollView);

        if (responseItemDetails.getExistInCart().equals(YES)) {
            cartId = responseItemDetails.getCartId();
            tvQuantity.setText(responseItemDetails.getExistItemQuantity());
            addCartView.setVisibility(View.VISIBLE);
            llAddCartView.setVisibility(View.GONE);
            llUpdateCartView.setVisibility(View.VISIBLE);
        } else {
            addCartView.setVisibility(View.GONE);
            cartId = "";
            tvQuantity.setText("1");
            etNote.setText("");
            llAddCartView.setVisibility(View.VISIBLE);
            llUpdateCartView.setVisibility(View.GONE);
        }


        cartQuantity=responseItemDetails.getCartQuantity();
        String totalPriceInfo = currencySymbol + SPACE + responseItemDetails.getCartAmt();
        String itemText = responseItemDetails.getCartQuantity() > 1 ? getResources().getString(R.string.to_items) : getResources().getString(R.string.to_item);
        tvItemInfo.setText(responseItemDetails.getCartQuantity() + " " + itemText + "  |  " + totalPriceInfo);
        showSpecialOfferIfAvailable(responseItemDetails);

        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setAlpha(1f);
        mShimmerViewContainer.setBaseAlpha(0.6f);
    }
    private ArrayList<String> checkItemInclude(ItemtInfo itemList){
        ArrayList<String> itemInclude=new ArrayList<>();
        if(itemList.getItemApiIncludes().size()>0)
            for(int i=0;i<itemList.getItemApiIncludes().size();i++){
                itemInclude.add(itemList.getItemApiIncludes().get(i).getItem_inc_id());
            }
        return  itemInclude;
    }

    void showSpecialOfferIfAvailable(ResponseItemDetails responseItemDetails){
        if(responseItemDetails.getItemtInfo().getDiscountRemaingTime()!=0){
            offerTillCv.setVisibility(View.VISIBLE);

            new CountDownTimer(responseItemDetails.getItemtInfo().getDiscountRemaingTime(), 1000) { // adjust the milli seconds here

                public void onTick(long millisUntilFinished) {
                    final long day = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);

                    final long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                            - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));

                    final long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));

                    final long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));


                    bindOfferText(day,hours,minutes,seconds);

                }

                public void onFinish() {
                    bindOfferLayVisibility();
                }
            }.start();

        }else{
            bindOfferLayVisibility();
        }
    }

    void bindOfferText(long day,long hour,long min,long sec){
        dayLay.setVisibility(day!=0?View.VISIBLE:View.INVISIBLE);
        dayTv.setText(String.valueOf(day));
        hourTv.setText(String.valueOf(hour));
        minTv.setText(String.valueOf(min));
        secTv.setText(String.valueOf(sec));
    }

    void bindOfferLayVisibility(){
        offerTillCv.setVisibility(View.GONE);
    }

    @Override
    public void showAddCartResponse(ResponseAddCart responseAddCart, String message) {
        appRepository.setCartCount(Integer.valueOf(responseAddCart.getTotalCartCount()));
        invalidateOptionsMenu();
        tvQuantity.setText(responseAddCart.getCartQuantity());

        llAddCartView.setVisibility(View.GONE);
        llUpdateCartView.setVisibility(View.VISIBLE);
        String totalPriceInfo = responseAddCart.getCartCurrency() + SPACE + responseAddCart.getTotalCartAmount();
        try {
            int totalCartCount = Integer.valueOf(responseAddCart.getTotalCartCount());
            if (totalCartCount == 0) {
                ViewUtils.slideDown(addCartView);
            } else {
                ViewUtils.slideUp(addCartView);
            }
            String itemText = totalCartCount > 1 ? getResources().getString(R.string.to_items) : getResources().getString(R.string.to_item);
            tvItemInfo.setText(responseAddCart.getTotalCartCount() + " " + itemText + "  |  " + totalPriceInfo);
        } catch (Exception e) {

        }


    }

    @Override
    public void onFavouriteResult(int position, String message) {
        showSuccessDialog(0, message);
        if (isRelatedItem) {
            if (message.equals(MESSAGE_UN_FAVOURITE)) {
                relatedItems.get(position).setItemIsFavourite(NOT_FAVOURITE);
            } else if (message.equals(MESSAGE_FAVOURITE)) {
                relatedItems.get(position).setItemIsFavourite(FAVOURITE);
            }
            restaurantDataAdapter.updateValues(position);
        }
    }


    @Override
    public void addCartError(String error) {
        if (error.equals(MESSAGE_FILLING_ADDRESS)) {
            showErrorDialog(2, error);
        } else {
            showErrorDialog(0, error);
        }
    }

    public void showImageSlide(List<String> stringList) {
        slider.setSelectedSlideIndicator(ContextCompat.getDrawable(ViewItemDetails.this, R.drawable.selected_slide_indicator));
        slider.setUnSelectedSlideIndicator(ContextCompat.getDrawable(ViewItemDetails.this, R.drawable.unselected_slide_indicator));
        slider.setAdapter(new ItemSliderAdapter(stringList));
        slider.setSelectedSlide(0);
        slider.setLoopSlides(stringList.size() != 1);
        slider.setAnimateIndicators(true);
        slider.setInterval(2000);
        slider.setIndicatorSize(30);
    }



    @Override
    public void addCartClick(int position) {
        clearExistValues();
        tvTitle.setText(relatedItems.get(position).getItemName());
        itemId = relatedItems.get(position).getItemId();
        //restaurantId = relatedItems.get(position).();
        viewItemDetailsPresenter.requestItemDetail(itemId, reviewPageNo);
    }

    @Override
    public void itemFavourites(int position) {
        isRelatedItem = true;
        int ItemId = relatedItems.get(position).getItemId();
        viewItemDetailsPresenter.addFavourites(position, String.valueOf(ItemId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            if (reviewsArrayList.size() != 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ALL_REVIEWS, reviewsArrayList);
                bundle.putInt(ITEM_ID, itemId);
                bundle.putString(TITLE, ITEM);
                changeActivityExtras(ViewAllReviews.class, bundle);
            } else {
                showError(getString(R.string.no_reviews));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        isRelatedItem = false;
        viewItemDetailsPresenter.addFavourites(0, String.valueOf(itemId));
    }


}
