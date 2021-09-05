package com.foodtogo.user.ui.manageaddress.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.shippingaddress.MultiAddressRequest;
import com.foodtogo.user.model.shippingaddress.MultiLocation;

import java.util.List;

public interface ManageAddressContractor {

    interface View extends BaseView {
        void successMsg(String msg);

        void showLoadingView();

        void hideLoadingView();

        void onBindMultiLocationList(List<MultiLocation> multiLocationList);

        void onAddOrEditSuccess(String msg);

        void addEditError(String msg);
    }

    interface Presenter {
        void apiSuccess(String msg);

        void onSuccessMultiLocation(List<MultiLocation> multiLocationList);

        void requestDeleteAddress(String lang, String id);

        void requestToAddOrEditAddress(MultiAddressRequest addressRequest);

        void requestToGetAllAddress(String lan);

        void apiError(String msg);

        void apiError(int msg);

        void addEditError(String msg);
    }

    interface Model {

        void requestDeleteAddress(String lang, String id);

        void requestToAddOrEditAddress(MultiAddressRequest addressRequest);

        void requestMultiLocation(String lat);

    }
}
