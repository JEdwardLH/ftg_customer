package com.foodtogo.user.ui.help.mvp;

import com.foodtogo.user.BaseView;
import com.foodtogo.user.model.support.Support;

public interface HelpContractor {

    interface View extends BaseView {

        void showContent(Support support);
    }

    interface Presenter {

        void requestHelpContent();

        void requestPrivacyPolicyContent();

        void onContent(Support support);

        void apiError(String error);

        void apiError(int error);

        void close();

    }

    interface Model {

        void requestHelpContent();

        void requestPrivacyPolicyContent();

        void close();
    }
}
