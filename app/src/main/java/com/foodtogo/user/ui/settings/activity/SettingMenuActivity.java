package com.foodtogo.user.ui.settings.activity;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.ui.about.fragment.AboutFragment;
import com.foodtogo.user.ui.help.fragment.HelpFragment;
import com.foodtogo.user.ui.paymentsettings.fragment.PaymentSettings;
import com.foodtogo.user.ui.profile.fragment.ProfileFragment;


public class SettingMenuActivity extends BaseActivity {

    int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTitle.setText(bundle.getString(TITLE));
            tabPosition = bundle.getInt(TAB_POSITION);
        }
        displayView();


    }

    private void displayView() {
        Fragment fragment = null;
        switch (tabPosition) {
            case 1:
                fragment = new AboutFragment();
                break;
            case 2:
                fragment = new ProfileFragment();
                break;
            case 3:

                //   fragment = new PaymentSettings().newInstance(NO);
                break;
            case 4:
                fragment = new PaymentSettings().newInstance(NO);
                break;
            case 5:
                fragment = new HelpFragment();
                Bundle bundle = new Bundle();
                bundle.putString(FROM_CLASS, PRIVACY_POLICY);
                fragment.setArguments(bundle);
                break;
            case 6:
                fragment = new PaymentSettings().newInstance(YES);
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_setting_menu;
    }


}
