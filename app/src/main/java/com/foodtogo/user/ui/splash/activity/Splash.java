package com.foodtogo.user.ui.splash.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.ui.address.activity.AddAddress;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.login.activity.Login;
import com.foodtogo.user.ui.splash.mvp.SplashContractor;
import com.foodtogo.user.ui.splash.mvp.SplashPresenter;
import com.foodtogo.user.ui.track.activity.LiveTrackActivity;

import java.util.Objects;


public class Splash extends BaseActivity implements SplashContractor.View {

    SplashPresenter splashPresenter;


    private static final String TAG = "Splash";
    String typeId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            FirebaseApp.initializeApp(this);
            checkOreo();
            splashPresenter = new SplashPresenter(this, appRepository);
            String appToken = appRepository.getFCMToken();
            Log.i(TAG, "fireBaseRegister: " + appToken);
            if (appToken.equals("")) {
                fireBaseRegister();
            }

             manageNotificationAndRedirectFlow();

            // ATTENTION: This was auto-generated to handle app links.
             Intent appLinkIntent = getIntent();
             String appLinkAction = appLinkIntent.getAction();
             Uri appLinkData = appLinkIntent.getData();



        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void manageNotificationAndRedirectFlow(){
        if (getIntent()!= null && getIntent().getStringExtra("store_id")!=null) {
            /**
             * for fcm notification intent
             * */
            String storeId = getIntent().getStringExtra("store_id");
            String orderId = getIntent().getStringExtra("order_id");
            System.out.println("order&store:"+orderId+":"+storeId);
            if (storeId != null && !storeId.equals("") && orderId!=null && !orderId.equals("")) {
                onStatusNotificationClick(orderId,Integer.valueOf(storeId));
            }
        }else if(getIntent()!= null && getIntent().getStringExtra("type_id")!=null){
            typeId= Objects.requireNonNull(getIntent()).getStringExtra("type_id");
            basedOnTypeIdRedirectNotification();
        }else{
            splashPresenter.startAnimation(this);
        }

    }

    private void checkOreo() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create channel to show notifications.
                String channelId = getString(R.string.default_notification_channel_id);
                String channelName = getString(R.string.default_notification_channel_name);
                NotificationManager notificationManager =
                        getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_LOW));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getLayout() {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            return R.layout.activity_splash;

    }


    @Override
    public void showLoginActivity() {
        try {
            changeActivityWithFinish(Login.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showDashBoardActivity() {
        try {
            if (appRepository.isLocationAvailable()) {
                Bundle bundle = new Bundle();
                bundle.putInt(TAB_POSITION, TAB_HOME);
                changeActivityClearBackStack(Home.class, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(FROM_CLASS, LANDING);
                changeActivityExtras(AddAddress.class, bundle);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void fireBaseRegister() {
        try {
            FirebaseInstanceId.getInstance().
                    getInstanceId().
                    addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.i(TAG, "fireBaseRegister: " + token);
                        appRepository.setFCMToken(token);
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onStatusNotificationClick(String orderId,int restaurantId){
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderId);
        bundle.putInt(RESTAURANT_ID, restaurantId);
        changeActivityExtras(LiveTrackActivity.class,bundle);
        finish();
    }

    public void basedOnTypeIdRedirectNotification(){
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if(typeId!=null && typeId.equals("0")){
            intent.putExtra(TAB_POSITION, 5); //Offers
        }else if(typeId!=null && typeId.equals("5")){
            intent.putExtra(TAB_POSITION,TAB_WALLET);
        }
       startActivity(intent);
    }
}
