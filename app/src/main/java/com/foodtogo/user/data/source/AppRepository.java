package com.foodtogo.user.data.source;

public class AppRepository implements AppDataSource {


    private AppDataSource mSharedPrefercenseDataSource;

    public AppRepository(AppDataSource sharedPreferences) {
        this.mSharedPrefercenseDataSource = sharedPreferences;
    }

    @Override
    public void saveIsLoggedIn(boolean isLoggedIn) {
        mSharedPrefercenseDataSource.saveIsLoggedIn(isLoggedIn);
    }

    @Override
    public boolean isLoggedIn() {
        return mSharedPrefercenseDataSource.isLoggedIn();
    }

    @Override
    public void setUserId(int userId) {
        mSharedPrefercenseDataSource.setUserId(userId);
    }

    @Override
    public int getUserId() {
        return mSharedPrefercenseDataSource.getUserId();
    }

    @Override
    public void setOAuthKey(String oAuthKey) {
        mSharedPrefercenseDataSource.setOAuthKey(oAuthKey);
    }

    @Override
    public String getOAuthKey() {
        return mSharedPrefercenseDataSource.getOAuthKey();
    }

    @Override
    public void setFCMToken(String fcmToken) {
        mSharedPrefercenseDataSource.setFCMToken(fcmToken);
    }

    @Override
    public String getFCMToken() {
        return mSharedPrefercenseDataSource.getFCMToken();
    }

    @Override
    public void saveLocation(boolean isLocationAvailable) {
        mSharedPrefercenseDataSource.saveLocation(isLocationAvailable);
    }

    @Override
    public boolean isLocationAvailable() {
        return mSharedPrefercenseDataSource.isLocationAvailable();
    }

    @Override
    public void setLatitude(String latitude) {
        mSharedPrefercenseDataSource.setLatitude(latitude);
    }

    @Override
    public String getLatitude() {
        return mSharedPrefercenseDataSource.getLatitude();
    }

    @Override
    public void setLongitude(String longitude) {
        mSharedPrefercenseDataSource.setLongitude(longitude);
    }

    @Override
    public String getLongitude() {
        return mSharedPrefercenseDataSource.getLongitude();
    }

    @Override
    public void setLanguageCode(String languageCode) {
        mSharedPrefercenseDataSource.setLanguageCode(languageCode);
    }

    @Override
    public String getLanguageCode() {
        return mSharedPrefercenseDataSource.getLanguageCode();
    }

    @Override
    public void setUserName(String userName) {
        mSharedPrefercenseDataSource.setUserName(userName);
    }

    @Override
    public String getUserPhoneNo() {
        return mSharedPrefercenseDataSource.getUserPhoneNo();
    }

    @Override
    public void setUserPhoneNo(String phoneNo) {
        mSharedPrefercenseDataSource.setUserPhoneNo(phoneNo);
    }

    @Override
    public String getUserName() {
        return mSharedPrefercenseDataSource.getUserName();
    }

    @Override
    public void setUserEmail(String userEmail) {
        mSharedPrefercenseDataSource.setUserEmail(userEmail);
    }

    @Override
    public String getUserEmail() {
        return mSharedPrefercenseDataSource.getUserEmail();
    }

    @Override
    public void setUserAvatar(String userAvatar) {
        mSharedPrefercenseDataSource.setUserAvatar(userAvatar);
    }

    @Override
    public String getUserAvatar() {
        return mSharedPrefercenseDataSource.getUserAvatar();
    }

    @Override
    public void setAppLogo(String appLogo) {
        mSharedPrefercenseDataSource.setAppLogo(appLogo);
    }

    @Override
    public String getAppLogo() {
        return mSharedPrefercenseDataSource.getAppLogo();
    }

    @Override
    public void setCartCount(int cartCount) {
        mSharedPrefercenseDataSource.setCartCount(cartCount);
    }

    @Override
    public int getCartCount() {
        return mSharedPrefercenseDataSource.getCartCount();
    }


    @Override
    public void setSearchLocation(String searchLocation) {
        mSharedPrefercenseDataSource.setSearchLocation(searchLocation);
    }

    @Override
    public String getSearchLocation() {
        return mSharedPrefercenseDataSource.getSearchLocation();
    }


    @Override
    public void setSearchPinCode(String searchPinCode) {
        mSharedPrefercenseDataSource.setSearchPinCode(searchPinCode);
    }

    @Override
    public String getSearchPinCode() {
        return mSharedPrefercenseDataSource.getSearchPinCode();
    }

    @Override
    public void setLastShownDate(String lastShownDate) {
        mSharedPrefercenseDataSource.setLastShownDate(lastShownDate);
    }

    @Override
    public String getLastShownDate() {
        return mSharedPrefercenseDataSource.getLastShownDate();
    }

    @Override
    public void setAddressType(String addressType) {
        mSharedPrefercenseDataSource.setAddressType(addressType);
    }

    @Override
    public String getAddressType() {
        return mSharedPrefercenseDataSource.getAddressType();
    }
}
