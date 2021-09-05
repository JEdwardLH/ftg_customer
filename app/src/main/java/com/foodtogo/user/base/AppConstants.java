package com.foodtogo.user.base;

public interface AppConstants {
    String MQTT_SERVER_ENDPOINT="tcp://13.212.61.29:1883";
    int TAB_CART = 10;
   // int TAB_SEARCH = 12;
    int TAB_HOME = 12;
    int TAB_TRACK = 13;
    int TAB_WALLET = 14;
    int TAB_DISABLE = 15;
    int TAB_LIVE_TRACK = 16;

    int OFF_SET_LIMIT = 10;

    String SELF_PICKUP = "Self-Pickup";
    String DELIVERY = "Delivery";
    String ANDROID = "android";
    String INFO = "Info";
    String IS_MANAGE = "is_manage";
    String EXCEPTION = "Exception";
    String EXCEPTION_NULL_POINTER = "NullPointerException";
    String MESSAGE_TOKEN_IS_EXPIRED = "Token is Expired";
    String MESSAGE_ORDER_PLACED = "Order placed succssfully";
    String MESSAGE_CART_ADDED = "Cart added sucessfully!";
    String MESSAGE_PROFILE_UPDATED = "Updated successfully!";
    String MESSAGE_PROFILE_ADDED = "Added successfully!";
    String MESSAGE_NEED_TO_PAY = "No need to pay";
    String MESSAGE_UN_FAVOURITE = "Wish list deleted successfully!";
    String MESSAGE_FAVOURITE = "Wish list added successfully!";
    String MESSAGE_FILLING_ADDRESS = "Please fill shipping address to continue";
    String INVALID_REFERAL_CODE = "Invalid referral code";
    String VERIFICATION_CHECK = "Sorry! You cannot change mobile number and email at a time.Update one by one!";

    String HELP = "Help";
    String PRIVACY_POLICY = "PrivacyPolicy";
    String NEW_CART = "NEW_CART";

    String UN_PUBLISH = "Unpublish";
    String PUBLISH = "Publish";
    String TITLE = "title";
    String CATEGORY_ID = "categoryId";
    String FEATURED = "featured";
    String RESTAURANT_ID = "restaurantId";
    String ORDER_AMOUNT = "order_amount";
    String RESTAURANT_NAME = "restaurantName";
    String FROM_CLASS = "fromClass";
    String LANDING = "Landing";
    String HOME = "Home";
    String YES = "Yes";
    String NO = "No";
    String SPACE = " ";
    String PAY = "Pay";
    String COD = "COD";
    String SPACE_ITEMS = " Items ";
    String AVAILABLE = "Available";
    String SOLD_OUT = "SOLD OUT";
    String ITEM_ID = "itemId";
    String ITEM_NAME = "itemName";
    String FAVOURITE = "Favourite";
    String NOT_FAVOURITE = "Not favourite";
    String NO_ITEMS_IN_CART = "No items in cart!";
    String TAB_POSITION = "tabPosition";
    String POSTAL_CODE = "postal_code";
    String NO_RECORDS_FOUND = "No records found!";
    String TOTAL_AMOUNT_USD= "totalAmountUsd";
    String IS_PEAK_HOUR= "isPeakHour";
    String TOTAL_AMOUNT = "totalAmount";
    String DELIVERY_FEE = "deliveryFee";
    String EXTRA_CHARGE = "extraCharge";
    String PEEK_INFO = "peek_info";
    String CURRENCY_SYMBOL = "currencySymbol";
    String SUB_TOTAL = "subTotal";
    String TOTAL_TAX = "Total_Tax";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String EMAIL_ID = "emailId";
    String NOT_AVAILABLE = "NOT_AVAILABLE";
    String MOBILE_NUMBER = "mobileNumber";
    String LAND_MARK = "landMark";
    String ALTERNATE_NUMBER = "alternateNumber";
    String POSTAL_ADDRESS = "postalAddress";
    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";
    String PINCODE = "pinCode";
    String ORD_SELF_PICKUP = "ordSelfPickup";
    String VEG = "Veg";
    String ORDER_ID = "orderId";
    String EMAIL = "email";
    String RESTAURANT = "Restaurant";
    String ITEM = "Item";
    String ORDER_CANCEL_MERCHANT="cancel_by_merchant";
    String CANCELLED="cancelled";

    // Pay Pal
    String PAY_PAL_CURRENCY = "THB";
    String PAY_PAL_DESC = "FoodToGo Payment";
    String PAY_PAL_RESPONSE = "PayPal Response";
    String PAY_PAL_JSON_EXCEPTION = "an extremely unlikely failure occurred:";
    String PAY_PAL_CANCELLED = "The user canceled.";
    String PAY_PAL_INVALID_PAYMENT_ERROR = "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.";
    String PENDING = "Pending";
    String SUBMIT = "SUBMIT";
    String VERIFY_EMAIL = "Verify Email";


    //Parcelable ArrayList Keys
    String PENDING_DETAILS = "PENDING_DETAILS";
    String FULFILLED_DETAILS = "FULFILLED_DETAILS";
    String CANCELLED_DETAILS = "CANCELLED_DETAILS";
    String ORDER_TRANSACTION_ID = "ORDER_TRANSACTION_ID";

    String TOTAL_WALLET = "TOTAL_WALLET";
    String USED_WALLET = "USED_WALLET";
    String TOTAL_REWARDS = "TOTAL_REWARDS";
    String CODE = "CODE";
    String MESSAGE = "MESSAGE";
    String PAYMENT_STATUS = "PAYMENT_STATUS";
    String PENDING_REFUND = "PENDING_REFUND";
    String COMPLETED_REFUND = "COMPLETED_REFUND";
    String REVIEWS_ITEMS = "REVIEWS_ITEMS";
    String REVIEWS_RESTAURANT = "REVIEWS_RESTAURANT";
    String REVIEWS_ORDER = "REVIEWS_ORDER";
    String ALL_REVIEWS = "ALL_REVIEWS";
    String STORE_LOCATIONS = "STORE_LOCATIONS";
    String WORKINGS_HOURS = "WORKINGS_HOURS";
    String TYPE = "type";


    //MQTT Objects
    String MQTT_EXCEPTION = "MQTT EXCEPTION";
    String MQTT_DELIVERY = "MQTT DELIVERED";
    String MQTT_DELETE_CALLED = "DELETECALLED";
    String MQTT_MSG = "MQTT MESSAGE";
    String MQTT_CONNECTION = "MQTT CONNECTION";
    String MQTT_TOPIC = "MQTT TOPIC";
    String MQTT_CONNECTION_SUCCESS = "Mqtt Server Connected";
    String MQTT_CONNECTION_FAILED = "Mqtt not Connected";
    String MQTT_CONNECTION_RECONNECT = "Mqtt Server reconnected";

    String PRODUCT_NOT_AVAILABLE="Product not available!";
    String INVALID_CREDENTIAL="Invalid Credentials";

    int HOME_ADDRESS=1;
    int WORK_ADDRESS=2;
    int OTHER_ADDRESS=3;
}

