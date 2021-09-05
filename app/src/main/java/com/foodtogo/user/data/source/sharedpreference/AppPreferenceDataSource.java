package com.foodtogo.user.data.source.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.foodtogo.user.data.source.AppDataSource;


public class AppPreferenceDataSource implements AppDataSource, PreferenceKeys {

    private SharedPreferences sharedPreferences;

    public AppPreferenceDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE);
    }

    @Override
    public void saveIsLoggedIn(boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply();
    }

    @Override
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    @Override
    public void setUserId(int userId) {
        sharedPreferences.edit().putInt(USER_ID, userId).apply();
    }

    @Override
    public int getUserId() {
        return sharedPreferences.getInt(USER_ID, 0);
    }

    @Override
    public void setOAuthKey(String oAuthKey) {
        sharedPreferences.edit().putString(OAUTH_KEY, oAuthKey).apply();
    }

    @Override
    public String getOAuthKey() {
        return sharedPreferences.getString(OAUTH_KEY, "");
    }

    @Override
    public void setFCMToken(String fcmToken) {
        sharedPreferences.edit().putString(FCM_TOKEN, fcmToken).apply();
    }

    @Override
    public String getFCMToken() {
        return sharedPreferences.getString(FCM_TOKEN, "");
    }

    @Override
    public void saveLocation(boolean isLocationAvailable) {
        sharedPreferences.edit().putBoolean(IS_AVAILABLE_LOCATION, isLocationAvailable).apply();
    }

    @Override
    public boolean isLocationAvailable() {
        return sharedPreferences.getBoolean(IS_AVAILABLE_LOCATION, false);
    }

    @Override
    public void setLatitude(String latitude) {
        sharedPreferences.edit().putString(APP_LATITUDE, latitude).apply();
    }

    @Override
    public String getLatitude() {
        return sharedPreferences.getString(APP_LATITUDE, "");
    }

    @Override
    public void setLongitude(String longitude) {
        sharedPreferences.edit().putString(APP_LONGITUDE, longitude).apply();
    }

    @Override
    public String getLongitude() {
        return sharedPreferences.getString(APP_LONGITUDE, "");
    }


    @Override
    public void setLanguageCode(String languageCode) {
        sharedPreferences.edit().putString(LANG_CODE, languageCode).apply();
    }

    @Override
    public String getLanguageCode() {
        return sharedPreferences.getString(LANG_CODE, "en");
    }


    @Override
    public void setUserName(String userName) {
        sharedPreferences.edit().putString(USER_NAME, userName).apply();
    }

    @Override
    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    @Override
    public void setUserEmail(String userEmail) {
        sharedPreferences.edit().putString(USER_EMAIL, userEmail).apply();
    }

    @Override
    public String getUserEmail() {
        return sharedPreferences.getString(USER_EMAIL, "");
    }



    @Override
    public void setUserPhoneNo(String phoneNo) {
        sharedPreferences.edit().putString(USER_PHONE, phoneNo).apply();
    }

    @Override
    public String getUserPhoneNo() {
        return sharedPreferences.getString(USER_PHONE, "");
    }

    @Override
    public void setUserAvatar(String userAvatar) {
        sharedPreferences.edit().putString(USER_AVATAR, userAvatar).apply();
    }

    @Override
    public String getUserAvatar() {
        return sharedPreferences.getString(USER_AVATAR, "");
    }

    @Override
    public void setAppLogo(String appLogo) {
        sharedPreferences.edit().putString(APP_LOGO, appLogo).apply();
    }

    @Override
    public String getAppLogo() {
        return sharedPreferences.getString(APP_LOGO, "");
    }

    @Override
    public void setCartCount(int cartCount) {
        sharedPreferences.edit().putInt(CART_COUNT, cartCount).apply();
    }

    @Override
    public int getCartCount() {
        return sharedPreferences.getInt(CART_COUNT, 0);
    }


    @Override
    public void setSearchLocation(String searchLocation) {
        sharedPreferences.edit().putString(SEARCH_LOCATION, searchLocation).apply();
    }

    @Override
    public String getSearchLocation() {
        return sharedPreferences.getString(SEARCH_LOCATION, "");
    }

    @Override
    public String getSearchPinCode() {
        return sharedPreferences.getString(SEARCH_PINCODE, "");
    }

    @Override
    public void setSearchPinCode(String searchPincode) {
        sharedPreferences.edit().putString(SEARCH_PINCODE, searchPincode).apply();
    }


    @Override
    public void setLastShownDate(String lastShownDate) {
        sharedPreferences.edit().putString(LAST_SHOWN_DATE, lastShownDate).apply();
    }

    @Override
    public String getLastShownDate() {
        return sharedPreferences.getString(LAST_SHOWN_DATE, "");
    }

    @Override
    public void setAddressType(String addressType) {
        sharedPreferences.edit().putString(ADDRESS_TYPE,addressType).apply();
    }

    @Override
    public String getAddressType() {
        return sharedPreferences.getString(ADDRESS_TYPE,"1");
    }
}
