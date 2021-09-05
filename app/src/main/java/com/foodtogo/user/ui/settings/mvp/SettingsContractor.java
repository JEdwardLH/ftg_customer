package com.foodtogo.user.ui.settings.mvp;

import android.app.Activity;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.settings.MenuSettings;

import java.util.List;

public interface SettingsContractor {

    interface View extends BaseView {

        void showSettingsMenu(List<MenuSettings> menuSettingsList);

        void showPasswordChangedSuccess(String message);
    }

    interface Presenter {


        void requestMenu(Activity activity);

        void onSettingsMenu(List<MenuSettings> menuSettingsList);

        void requestChangePassword(String oldPassword, String newPassword);

        void onPasswordChangedSuccess(String message);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestMenu(Activity activity);

        void requestChangePassword(String oldPassword, String newPassword);

        void close();
    }
}
