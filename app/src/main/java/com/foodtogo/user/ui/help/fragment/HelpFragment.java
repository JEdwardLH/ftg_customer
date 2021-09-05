package com.foodtogo.user.ui.help.fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseFragment;
import com.foodtogo.user.model.support.Support;
import com.foodtogo.user.ui.help.mvp.HelpContractor;
import com.foodtogo.user.ui.help.mvp.HelpPresenter;

import java.net.URLEncoder;

import butterknife.BindView;

import static com.foodtogo.user.base.AppConstants.EXCEPTION;
import static com.foodtogo.user.base.AppConstants.FROM_CLASS;
import static com.foodtogo.user.base.AppConstants.HELP;
import static com.foodtogo.user.base.AppConstants.PRIVACY_POLICY;


public class HelpFragment extends BaseFragment implements HelpContractor.View {


    @BindView(R.id.tv_help_title)
    TextView tvHelpTitle;

    @BindView(R.id.webview)
    WebView browser;


    public String type = "";

    public HelpFragment() {
        // Required empty public constructor
    }

    HelpPresenter myCartPresenter;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, parent, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCartPresenter = new HelpPresenter(this, appRepository);
        try {
            type = getArguments().getString(FROM_CLASS);
            if (type.equals(HELP)) {
                myCartPresenter.requestHelpContent();
            } else if (type.equals(PRIVACY_POLICY)) {
                myCartPresenter.requestPrivacyPolicyContent();
            }
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString() );
        }
    }

    @Override
    public void showLoadingView() {
        showProgress();
    }

    @Override
    public void hideLoadingView() {
        hideProgress();
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showError(int message) {
        showToast(message);
    }

    @Override
    public void showContent(Support support) {
      /*  tvHelpTitle.setText(Html.fromHtml(support.getContent().getTitle()));
       browser.getSettings().setJavaScriptEnabled(true);
       browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
       browser.loadDataWithBaseURL("", support.getContent().getDescription(), "text/html", "UTF-8", "");*/


        tvHelpTitle.setText(Html.fromHtml(support.getContent().getTitle()));
        browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        browser.loadUrl(support.getContent().getUrl());
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        browser.getSettings().setPluginState(WebSettings.PluginState.ON);
        browser.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        WebSettings ws = browser.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        ws.setLoadsImagesAutomatically(true);


    }
}