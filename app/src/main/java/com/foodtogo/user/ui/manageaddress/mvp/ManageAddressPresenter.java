package com.foodtogo.user.ui.manageaddress.mvp;

import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.shippingaddress.MultiAddressRequest;
import com.foodtogo.user.model.shippingaddress.MultiLocation;

import java.util.List;

public class ManageAddressPresenter implements ManageAddressContractor.Presenter {

    private ManageAddressContractor.View view;
    private ManageAddressModel model;
    private AppRepository mApprepository;

    public ManageAddressPresenter(ManageAddressContractor.View view, AppRepository appRepository) {
        this.view = view;
        this.mApprepository=appRepository;
        this.model=new ManageAddressModel(this);
    }

    @Override
    public void apiSuccess(String msg) {
        view.hideLoadingView();
       view.onAddOrEditSuccess(msg);
    }

    @Override
    public void onSuccessMultiLocation(List<MultiLocation> multiLocationList) {
       view.hideLoadingView();
       view.onBindMultiLocationList(multiLocationList);
    }

    @Override
    public void requestDeleteAddress(String lang, String id) {
       view.showLoadingView();
       model.requestDeleteAddress(lang,id);
    }

    @Override
    public void requestToAddOrEditAddress(MultiAddressRequest addressRequest) {
        view.showLoadingView();
        model.requestToAddOrEditAddress(addressRequest);
    }

    @Override
    public void requestToGetAllAddress(String lan) {
        view.showLoadingView();
        model.requestMultiLocation(lan);
    }

    @Override
    public void apiError(String msg) {
        view.hideLoadingView();
      view.showError(msg);
    }

    @Override
    public void apiError(int msg) {
        view.hideLoadingView();
        view.showError(msg);
    }

    @Override
    public void addEditError(String msg) {
        view.hideLoadingView();
        view.addEditError(msg);
    }
}
