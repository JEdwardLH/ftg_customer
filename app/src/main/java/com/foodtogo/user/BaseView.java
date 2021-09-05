package com.foodtogo.user;

public interface BaseView {

    void showLoadingView();

    void hideLoadingView();

    void showError(String message);

    void showError(int message);

}
