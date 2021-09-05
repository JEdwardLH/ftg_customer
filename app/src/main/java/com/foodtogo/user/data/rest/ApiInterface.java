package com.foodtogo.user.data.rest;


import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.ErrorResponse;
import com.foodtogo.user.model.accountdetails.User;
import com.foodtogo.user.model.allrestaurant.AllRestaurantResponse;
import com.foodtogo.user.model.categorybased.CategoryBasedRequest;
import com.foodtogo.user.model.changepassword.ChangePasswordRequest;
import com.foodtogo.user.model.country.CountryList;
import com.foodtogo.user.model.directions.DirectionResponse;
import com.foodtogo.user.model.directions.DistanceResponse;
import com.foodtogo.user.model.forgotpassword.ForgotPasswordRequest;
import com.foodtogo.user.model.forgotpassword.ForgotPasswordResponse;
import com.foodtogo.user.model.geocodeaddress.GeoCodeAddress;
import com.foodtogo.user.model.home.HomeRequest;
import com.foodtogo.user.model.home.HomeResponse;
import com.foodtogo.user.model.home.HomeSearchRequest;
import com.foodtogo.user.model.home.HomeSearchResponse;
import com.foodtogo.user.model.invoice.InvoiceDetailsResponse;
import com.foodtogo.user.model.invoice.RequestInvoice;
import com.foodtogo.user.model.itemdetails.AddFavourites;
import com.foodtogo.user.model.itemdetails.RequestItemDetails;
import com.foodtogo.user.model.itemdetails.RequestToppingDetail;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.itemdetails.ResponseToppingQuantity;
import com.foodtogo.user.model.landing.LandingResponse;
import com.foodtogo.user.model.landing.LanguageRequest;
import com.foodtogo.user.model.login.LoginFacebookRequest;
import com.foodtogo.user.model.login.LoginGoogleRequest;
import com.foodtogo.user.model.login.LoginRequest;
import com.foodtogo.user.model.login.LoginResponse;
import com.foodtogo.user.model.login.LogoutRequest;
import com.foodtogo.user.model.mycart.CartItemRemoveRequest;
import com.foodtogo.user.model.mycart.CartRequest;
import com.foodtogo.user.model.mycart.CartResponse;
import com.foodtogo.user.model.mycart.ItemChoiceRemoveRequest;
import com.foodtogo.user.model.mycart.ItemQuantityUpdateRequest;
import com.foodtogo.user.model.mycart.PreOrderRemoveRequest;
import com.foodtogo.user.model.mycart.PreOrderRequest;
import com.foodtogo.user.model.myreviews.MyReviewRequest;
import com.foodtogo.user.model.myreviews.MyReviewResponse;
import com.foodtogo.user.model.offers.OffersResponse;
import com.foodtogo.user.model.orderdetails.CancelOrderRequest;
import com.foodtogo.user.model.orderdetails.OrderDetailRequest;
import com.foodtogo.user.model.orderdetails.OrderDetailResponse;
import com.foodtogo.user.model.orderhistory.OrderHistoryRequest;
import com.foodtogo.user.model.orderhistory.OrderHistoryResponse;
import com.foodtogo.user.model.orderhistory.RepeatOrderRequest;
import com.foodtogo.user.model.payment.CheckQuantityResponse;
import com.foodtogo.user.model.payment.PaymentMethodResponse;
import com.foodtogo.user.model.payment.PaymentResult;
import com.foodtogo.user.model.payment.RequestCashOnDelivery;
import com.foodtogo.user.model.payment.RequestPayPal;
import com.foodtogo.user.model.payment.RequestStripe;
import com.foodtogo.user.model.payment.UseWallet;
import com.foodtogo.user.model.payment.UseWalletResponse;
import com.foodtogo.user.model.paymentsettings.PaymentSettingResponse;
import com.foodtogo.user.model.paymentsettings.PaymentSettingUpdateRequest;
import com.foodtogo.user.model.profile.ProfileUpdateResponse;
import com.foodtogo.user.model.refunddetails.RefundDetailResponse;
import com.foodtogo.user.model.register.RegisterRequest;
import com.foodtogo.user.model.register.RegisterResponse;
import com.foodtogo.user.model.restaurant.CategoryBasedItems;
import com.foodtogo.user.model.restaurant.RequestAddCart;
import com.foodtogo.user.model.restaurant.RequestRestaurantBasedItem;
import com.foodtogo.user.model.restaurant.RequestRestaurantDetail;
import com.foodtogo.user.model.restaurant.ResponseAddCart;
import com.foodtogo.user.model.restaurant.RestaurantDetailResponse;
import com.foodtogo.user.model.reviews.PostItemReview;
import com.foodtogo.user.model.reviews.PostOrderReview;
import com.foodtogo.user.model.reviews.PostStoreReview;
import com.foodtogo.user.model.shippingaddress.AddressRequest;
import com.foodtogo.user.model.shippingaddress.CheckVerificationRequest;
import com.foodtogo.user.model.shippingaddress.DeleteRequest;
import com.foodtogo.user.model.shippingaddress.MultiAddressRequest;
import com.foodtogo.user.model.shippingaddress.OtpResponse;
import com.foodtogo.user.model.shippingaddress.SendEmailVerificationCodeRequest;
import com.foodtogo.user.model.shippingaddress.ShippingAddressData;
import com.foodtogo.user.model.shippingaddress.ShippingAddressRequest;
import com.foodtogo.user.model.splash.ImageResponse;
import com.foodtogo.user.model.support.Support;
import com.foodtogo.user.model.track.TrackDetailResponse;
import com.foodtogo.user.model.track.TrackDetailsRequest;
import com.foodtogo.user.model.wallet.RewardRequest;
import com.foodtogo.user.model.wallet.RewardsResponse;
import com.foodtogo.user.model.wallet.WalletBalanceResponse;
import com.foodtogo.user.model.wishlist.FavouriteRequest;
import com.foodtogo.user.model.wishlist.FavouriteResponse;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST(ApiEndPoints.CUSTOMER_HOME_PAGE)
    Single<BaseResponse<ImageResponse>> getHomeResponse(@Body LanguageRequest landingRequest);

    @POST(ApiEndPoints.COUNTRY_LIST)
    Single<BaseResponse<CountryList>> getCountryList(@Body LanguageRequest languageRequest);

    @POST(ApiEndPoints.HEMP_CONTENT)
    Single<BaseResponse<Support>> getHelpContent(@Body LanguageRequest languageRequest);

    @POST(ApiEndPoints.TERMS_CONTENT)
    Single<BaseResponse<Support>> getPrivacyContent(@Body LanguageRequest languageRequest);

    @POST(ApiEndPoints.LOGIN)
    Single<BaseResponse<LoginResponse>> signIn(@Body LoginRequest loginRequest);

    @POST(ApiEndPoints.GOOGLE_LOGIN)
    Single<BaseResponse<LoginResponse>> googleLogin(@Body LoginGoogleRequest loginGoogleRequest);

    @POST(ApiEndPoints.FACEBOOK_LOGIN)
    Single<BaseResponse<LoginResponse>> facebookLogin(@Body LoginFacebookRequest loginFacebookRequest);

    @POST(ApiEndPoints.REGISTER)
    Single<BaseResponse<RegisterResponse>> register(@Body RegisterRequest registerRequest);

    @POST(ApiEndPoints.CUSTOMER_OTP)
    Single<BaseResponse<RegisterResponse>> registerWithOtp(@Body RegisterRequest registerRequest);

    @POST(ApiEndPoints.FORGOT_PASSWORD)
    Single<BaseResponse<ForgotPasswordResponse>> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST(ApiEndPoints.LANDING)
    Single<BaseResponse<LandingResponse>> landing(@Body LanguageRequest landingRequest);

    @POST(ApiEndPoints.CUSTOMER_REFER_FRIEND)
    Single<BaseResponse<LandingResponse>> getReferFriendOffer(@Body LanguageRequest landingRequest);

    @POST(ApiEndPoints.REFER_FRIEND_SEND_MAIL)
    Single<BaseResponse<LandingResponse>> postReferFriend(@Body LanguageRequest landingRequest);

    @POST(ApiEndPoints.MY_ACCOUNT_DETAILS)
    Single<BaseResponse<User>> requestAccountDetails(@Body LanguageRequest landingRequest);

    @POST(ApiEndPoints.SAVE_SHIPPING_ADDRESS)
    Single<BaseResponse> saveShippingAddress(@Body AddressRequest addressRequest);

    @POST(ApiEndPoints.NEAR_BY_RESTAURANT)
    Single<BaseResponse<HomeResponse>> requestRestaurant(@Body HomeRequest homeRequest);

    @POST(ApiEndPoints.RESTAURANT_DETAILS)
    Single<BaseResponse<RestaurantDetailResponse>> requestRestaurantDetails(@Body RequestRestaurantDetail restaurantDetail);

    @POST(ApiEndPoints.RESTAURANT_CATEGORY_BASED)
    Single<BaseResponse<CategoryBasedItems>> requestRestaurantCategoryBased(@Body RequestRestaurantBasedItem requestRestaurantBasedItem);

    @POST(ApiEndPoints.ALL_RESTAURANT_LIST)
    Single<BaseResponse<AllRestaurantResponse>> getAllRestaurant(@Body HomeRequest homeRequest);

    @POST(ApiEndPoints.CATEGORY_BASED_RESTAURANT)
    Single<BaseResponse<AllRestaurantResponse>> getCategoryBasedRestaurant(@Body CategoryBasedRequest categoryBasedRequest);

    @POST(ApiEndPoints.FEATURED_RESTAURANT)
    Single<BaseResponse<AllRestaurantResponse>> getFeaturedRestaurant(@Body CategoryBasedRequest categoryBasedRequest);

    @POST(ApiEndPoints.NEW_RESTAURANT_LIST)
    Single<BaseResponse<AllRestaurantResponse>> getNewRestaurantList(@Body CategoryBasedRequest categoryBasedRequest);

    @POST(ApiEndPoints.TOP_RESTAURANT_LIST)
    Single<BaseResponse<AllRestaurantResponse>> getTopRestaurantList(@Body CategoryBasedRequest categoryBasedRequest);

    @POST(ApiEndPoints.NEAR_BY_RESTAURANT_LIST)
    Single<BaseResponse<AllRestaurantResponse>> getNearByRestaurantList(@Body CategoryBasedRequest categoryBasedRequest);


    @POST(ApiEndPoints.ITEM_DETAILS)
    Single<BaseResponse<ResponseItemDetails>> requestItemDetails(@Body RequestItemDetails requestItemDetails);

    @POST(ApiEndPoints.ADD_CART)
    Single<BaseResponse<ResponseAddCart>> requestAddCart(@Body RequestAddCart requestAddCart);

    @POST(ApiEndPoints.MY_CART)
    Single<BaseResponse<CartResponse>> requestCartDetails(@Body CartRequest cartRequest);

    @POST(ApiEndPoints.REMOVE_FROM_CART)
    Single<BaseResponse<CartResponse>> removeItemCart(@Body CartItemRemoveRequest cartItemRemoveRequest);

    @POST(ApiEndPoints.REMOVE_CHOICE)
    Single<BaseResponse<CartResponse>> removeItemChoice(@Body ItemChoiceRemoveRequest itemChoiceRemoveRequest);

    @POST(ApiEndPoints.UPDATE_CHOICE)
    Single<BaseResponse<CartResponse>> updateChoice(@Body ItemChoiceRemoveRequest itemChoiceRemoveRequest);

    @POST(ApiEndPoints.ADD_PRE_ORDER)
    Single<BaseResponse<CartResponse>> updatePreOrder(@Body PreOrderRequest preOrderRequest);

    @POST(ApiEndPoints.REMOVE_PRE_ORDER)
    Single<BaseResponse> removePreOrder(@Body PreOrderRemoveRequest preOrderRequest);

    @POST(ApiEndPoints.QTY_UPDATE_CART)
    Single<BaseResponse<CartResponse>> updateQuantityCart(@Body ItemQuantityUpdateRequest itemQuantityUpdateRequest);

    @POST(ApiEndPoints.CUSTOMER_SHIP_ADDRESS)
    Single<BaseResponse<ShippingAddressData>> getShippingAddress(@Body LanguageRequest LanguageRequest);

    @POST(ApiEndPoints.CUSTOMER_UPDATE_SHIP_ADDRESS)
    Single<BaseResponse<OtpResponse>> postShippingAddress(@Body ShippingAddressRequest shippingAddressRequest);

    @POST(ApiEndPoints.CUSTOMER_UPDATE_SHIP_ADDRESS_WITH_OTP)
     Single<BaseResponse> postAddressWithOtp(@Body ShippingAddressRequest addressRequest);

    @POST(ApiEndPoints.MY_WALLET)
    Single<BaseResponse<WalletBalanceResponse>> getWalletBalance(@Body LanguageRequest languageRequest);

    @POST(ApiEndPoints.USED_WALLET_DETAILS)
    Single<BaseResponse<WalletBalanceResponse>> getUsedWallet(@Body LanguageRequest languageRequest);

    @POST(ApiEndPoints.LOYALTY_HISTORY)
    Single<BaseResponse<RewardsResponse>> getLoyalityHistory(@Body RewardRequest rewardRequest);

    @POST(ApiEndPoints.PAYMENT_METHODS)
    Single<BaseResponse<PaymentMethodResponse>> getPaymentMethod(@Body LanguageRequest languageRequest);

    @POST(ApiEndPoints.CHECK_QTY_PAYMENT)
    Single<BaseResponse<CheckQuantityResponse>> checkQtyPayment(@Body LanguageRequest languageRequest);

    @POST(ApiEndPoints.COD_CHECKOUT)
    Single<BaseResponse<List<PaymentResult>>> checkOutCOD(@Body RequestCashOnDelivery requestCashOnDelivery);

    @POST(ApiEndPoints.PAYPAL_CHECKOUT)
    Single<BaseResponse<List<PaymentResult>>> checkOutPayPal(@Body RequestPayPal requestPayPal);

    @POST(ApiEndPoints.STRIPE_CHECKOUT)
    Single<BaseResponse<List<PaymentResult>>> checkOutStripe(@Body RequestStripe requestStripe);

    @POST(ApiEndPoints.WALLET_CHECKOUT)
    Single<BaseResponse<List<PaymentResult>>> checkOutWallet(@Body RequestCashOnDelivery requestCashOnDelivery);

    @POST(ApiEndPoints.USE_WALLET)
    Single<BaseResponse<UseWalletResponse>> useWallet(@Body UseWallet useWallet);

    @POST(ApiEndPoints.USE_OFFER)
    Single<BaseResponse<UseWalletResponse>> useOffer(@Body UseWallet useWallet);

    @POST(ApiEndPoints.LOG_OUT)
    Single<BaseResponse<LoginResponse>> logout(@Body LogoutRequest logoutRequest);

    @POST(ApiEndPoints.ADD_TO_WISH_LIST)
    Single<BaseResponse<ResponseAddCart>> addFavourites(@Body AddFavourites addFavourites);

    @POST(ApiEndPoints.VIEW_WISH_LIST)
    Single<BaseResponse<FavouriteResponse>> getFavourites(@Body FavouriteRequest favouriteRequest);

    @POST(ApiEndPoints.MY_ORDERS)
    Single<BaseResponse<OrderHistoryResponse>> getOrders(@Body OrderHistoryRequest orderHistoryRequest);

    @POST(ApiEndPoints.REPEAT_ORDER)
    Single<BaseResponse<ErrorResponse>> repeatOrder(@Body RepeatOrderRequest repeatOrderRequest);

    @POST(ApiEndPoints.MY_ORDER_DETAILS)
    Single<BaseResponse<OrderDetailResponse>> orderDetails(@Body OrderDetailRequest orderDetailRequest);

    @POST(ApiEndPoints.CANCEL_ORDER)
    Single<BaseResponse<OrderDetailResponse>> cancelOrderDetails(@Body CancelOrderRequest cancelOrderRequest);

    @POST(ApiEndPoints.STORE_WRITE_REVIEW)
    Single<BaseResponse<OrderDetailResponse>> writeStoreReview(@Body PostStoreReview postStoreReview);

    @POST(ApiEndPoints.ORDER_WRITE_REVIEW)
    Single<BaseResponse<OrderDetailResponse>> writeOrderReview(@Body PostOrderReview postOrderReview);

    @POST(ApiEndPoints.PRODUCT_WRITE_REVIEW)
    Single<BaseResponse<OrderDetailResponse>> writeItemReview(@Body PostItemReview postItemReview);

    @POST(ApiEndPoints.CUSTOMER_INVOICE)
    Single<BaseResponse<InvoiceDetailsResponse>> invoiceDetails(@Body RequestInvoice requestInvoice);

    @POST(ApiEndPoints.CUSTOMER_RESET_PASSWORD)
    Single<BaseResponse<ForgotPasswordResponse>> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST(ApiEndPoints.CUSTOMER_UPDATE_ACCOUNT)
    @Multipart
    Single<BaseResponse<ProfileUpdateResponse>> updateAccount(@Part("cus_name") RequestBody cus_name, @Part("cus_email") RequestBody cus_email,
                                                              @Part("cus_phone1") RequestBody cus_phone1, @Part("cus_phone2") RequestBody cus_phone2,
                                                              @Part("cus_address") RequestBody cus_address, @Part("cus_lat") RequestBody cus_lat,
                                                              @Part("cus_long") RequestBody cus_long, @Part("lang") RequestBody lang,@Part("cus_phone1_code") RequestBody phoneCode1,
                                                              @Part("cus_phone2_code") RequestBody phoneCode2,
                                                              @Part MultipartBody.Part image);

    @POST(ApiEndPoints.CUSTOMER_UPDATE_ACCOUNT_WITH_OTP)
    @Multipart
    Single<BaseResponse<ProfileUpdateResponse>> updateAccountWithOtp(@Part("cus_name") RequestBody cus_name, @Part("cus_email") RequestBody cus_email,
                                                                     @Part("cus_phone1") RequestBody cus_phone1, @Part("cus_phone2") RequestBody cus_phone2,
                                                                     @Part("cus_address") RequestBody cus_address, @Part("cus_lat") RequestBody cus_lat,
                                                                     @Part("cus_long") RequestBody cus_long, @Part("lang") RequestBody lang,
                                                                     @Part("otp") RequestBody otp, @Part("current_otp") RequestBody current_otp,@Part("cus_phone1_code") RequestBody phoneCode1,
                                                                     @Part("cus_phone2_code") RequestBody phoneCode2,
                                                                     @Part MultipartBody.Part image);

    @POST(ApiEndPoints.REFUND_DETAILS)
    Single<BaseResponse<List<RefundDetailResponse>>> refundDetails(@Body OrderDetailRequest orderDetailRequest);

    @POST(ApiEndPoints.MY_REVIEWS)
    Single<BaseResponse<MyReviewResponse>> requestMyReviews(@Body MyReviewRequest myReviewRequest);

    @POST(ApiEndPoints.CUSTOMER_PAYMENT_SETTINGS)
    Single<BaseResponse<PaymentSettingResponse>> getPaymentSettings(@Body LanguageRequest landingRequest);

    @POST(ApiEndPoints.CUSTOMER_UPDATE_PAYMENT_SETTINGS)
    Single<BaseResponse<PaymentSettingResponse>> updatePaymentSettings(@Body PaymentSettingUpdateRequest paymentSettingUpdateRequest);

    @POST(ApiEndPoints.ORDER_TRACKING)
    Single<BaseResponse<TrackDetailResponse>> getTrackDetails(@Body TrackDetailsRequest trackDetailsRequest);

    @POST(ApiEndPoints.HOME_SEARCH)
    Single<BaseResponse<HomeSearchResponse>> homeSearch(@Body HomeSearchRequest trackDetailsRequest);

    @POST(ApiEndPoints.MY_OFFERS)
    Single<BaseResponse<OffersResponse>> offersList(@Body MyReviewRequest reviewRequest);

    @POST(ApiEndPoints.ITEM_DETAIL_TOPPINGS)
    Single<BaseResponse<ResponseToppingQuantity>> toppingsQuantity(@Body RequestToppingDetail requestToppingDetail);

    @GET(ApiEndPoints.DIRECTION_API)
    Single<DirectionResponse> getPolylineData(@Query(ApiKeys.ORIGIN) String origin,
                                              @Query(ApiKeys.DESTINATION) String destination,
                                              @Query(ApiKeys.WAY_POINTS) String wayPoints,
                                              @Query(ApiKeys.KEY) String apiKey);

    @GET(ApiEndPoints.DIRECTION_API)
    Single<DirectionResponse> getPolylineData(@Query(ApiKeys.ORIGIN) String origin,
                                              @Query(ApiKeys.DESTINATION) String destination,
                                              @Query(ApiKeys.KEY) String apiKey);

    @GET(ApiEndPoints.DISTANCE_API)
    Single<DistanceResponse> getDistanceData(@Query(ApiKeys.ORIGINS) String origin,
                                             @Query(ApiKeys.DESTINATIONS) String destination,
                                             @Query(ApiKeys.WAY_POINTS) String wayPoints,
                                             @Query(ApiKeys.KEY) String apiKey);

    @GET(ApiEndPoints.ADDRESS_API)
    Single<GeoCodeAddress> getAddress(@Query(ApiKeys.LATLNG) String latlng,
                                      @Query(ApiKeys.KEY) String apiKey);

    @POST(ApiEndPoints.DELETE_ADDRESS)
    Single<BaseResponse> deleteAddress(@Body DeleteRequest deleteRequest);

    @POST(ApiEndPoints.ADD_ADDRESS)
    Single<BaseResponse> addAddress(@Body MultiAddressRequest request);

    @POST(ApiEndPoints.SEND_VERIFICATION_CODE)
    Single<BaseResponse<OtpResponse>> sendVerificationCode(@Body SendEmailVerificationCodeRequest request);

    @POST(ApiEndPoints.CHECK_VERIFICATION_CODE)
    Single<BaseResponse> checkVerificationCode(@Body CheckVerificationRequest request);







}
