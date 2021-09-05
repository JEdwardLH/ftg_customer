package com.foodtogo.user.data.source.sharedpreference;

import android.content.Context;

public interface PreferenceKeys {

    String PREF_NAME = "PEOPLE_PREFERENCES";
    int MODE = Context.MODE_PRIVATE;

    String IS_LOGGED_IN = "IsLoggedIn";
    String USER_ID = "userId";
    String OAUTH_KEY = "OAuthKey";
    String IS_AVAILABLE_LOCATION = "isAvailableLocation";
    String APP_LATITUDE = "appLatitude";
    String APP_LONGITUDE = "appLongitude";
    String LANG_CODE = "langCode";
    String USER_NAME = "userName";
    String USER_EMAIL = "userEmail";
    String USER_PHONE = "userPhone";
    String USER_AVATAR = "userAvatar";
    String APP_LOGO = "appLogo";
    String CART_COUNT = "cartCount";
    String FCM_TOKEN = "fcmToken";
    String SEARCH_LOCATION = "searchLocation";
    String SEARCH_PINCODE = "searchPincode";
    String LAST_SHOWN_DATE = "lastShownDate";
    String ADDRESS_TYPE = "address_type";
}
