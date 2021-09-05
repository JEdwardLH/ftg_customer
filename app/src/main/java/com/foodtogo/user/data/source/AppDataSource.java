package com.foodtogo.user.data.source;

public interface AppDataSource {

    void saveIsLoggedIn(boolean isLoggedIn);

    boolean isLoggedIn();

    void setUserId(int userId);

    int getUserId();

    void setOAuthKey(String oAuthKey);

    String getOAuthKey();

    void setFCMToken(String fcmToken);

    String getFCMToken();

    void saveLocation(boolean isLocationAvailable);

    boolean isLocationAvailable();

    void setLatitude(String latitude);

    String getLatitude();

    void setLongitude(String longitude);

    String getLongitude();

    void setLanguageCode(String languageCode);

    String getLanguageCode();

    void setUserName(String userName);

    String getUserName();

    void setUserEmail(String userEmail);

    String getUserEmail();

    void setUserPhoneNo(String phoneNo);

    String getUserPhoneNo();

    void setUserAvatar(String userAvatar);

    String getUserAvatar();

    void setAppLogo(String appLogo);

    String getAppLogo();

    void setCartCount(int cartCount);

    int getCartCount();

    void setSearchLocation(String searchLocation);

    String getSearchLocation();

    void setSearchPinCode(String searchPinCode);

    String getSearchPinCode();

    void setLastShownDate(String lastShownDate);

    String getLastShownDate();

    void setAddressType(String addressType);

    String getAddressType();
}
