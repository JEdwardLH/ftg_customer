package com.foodtogo.user.ui.settings.mvp;


import android.app.Activity;

import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.changepassword.ChangePasswordRequest;
import com.foodtogo.user.model.forgotpassword.ForgotPasswordResponse;
import com.foodtogo.user.model.settings.MenuSettings;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class SettingsModel implements SettingsContractor.Model {


    private SettingsPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    private static final int MENU_1_IMAGE = R.drawable.ic_setting_password;
    private static final int MENU_2_IMAGE = R.drawable.ic_setting_about;
    private static final int MENU_3_IMAGE = R.drawable.ic_setting_profile;
    private static final int MENU_4_IMAGE = R.drawable.ic_setting_language;
    private static final int MENU_5_IMAGE = R.drawable.ic_settings_payment;
    private static final int MENU_6_IMAGE = R.drawable.ic_setting_terms_condition;
    private static final int MENU_7_IMAGE = R.drawable.ic_setting_promotional;

    SettingsModel(SettingsPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestMenu(Activity activity) {
        List<MenuSettings> menuSettingsList = new ArrayList<>();
        menuSettingsList.add(new MenuSettings(MENU_1_IMAGE, activity.getString(R.string.setting1)));
        menuSettingsList.add(new MenuSettings(MENU_2_IMAGE,activity.getString(R.string.setting2)));
        menuSettingsList.add(new MenuSettings(MENU_3_IMAGE, activity.getString(R.string.setting3)));
        menuSettingsList.add(new MenuSettings(MENU_4_IMAGE, activity.getString(R.string.setting4)));
        menuSettingsList.add(new MenuSettings(MENU_5_IMAGE, activity.getString(R.string.setting5)));
        menuSettingsList.add(new MenuSettings(MENU_6_IMAGE, activity.getString(R.string.setting6)));
        mPresenter.onSettingsMenu(menuSettingsList);
    }

    @Override
    public void requestChangePassword(String oldPassword, String newPassword) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOld_password(oldPassword);
        changePasswordRequest.setNew_password(newPassword);
        changePasswordRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .changePassword(changePasswordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ForgotPasswordResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<ForgotPasswordResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onPasswordChangedSuccess(baseResponse.getMessage());
                        } else {
                            mPresenter.apiError(baseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.apiError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.apiError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.apiError(R.string.network_error);
                        } else {
                            mPresenter.apiError(e.getMessage());
                        }

                    }
                }));
    }


    @Override
    public void close() {
        disposable.dispose();
    }
}
