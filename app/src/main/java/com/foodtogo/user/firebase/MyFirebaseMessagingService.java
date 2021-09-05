package com.foodtogo.user.firebase;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import com.alium.nibo.utils.NiboConstants;
import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.ui.home.activity.Home;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.foodtogo.user.ui.orders.activity.OrderActivity;

import java.util.List;
import java.util.Map;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.app.NotificationCompat.DEFAULT_VIBRATE;
import static com.foodtogo.user.base.AppConstants.CANCELLED;
import static com.foodtogo.user.base.AppConstants.ORDER_CANCEL_MERCHANT;
import static com.foodtogo.user.base.AppConstants.ORDER_ID;
import static com.foodtogo.user.base.AppConstants.RESTAURANT_ID;
import static com.foodtogo.user.base.AppConstants.TAB_LIVE_TRACK;
import static com.foodtogo.user.base.AppConstants.TAB_POSITION;
import static com.foodtogo.user.base.AppConstants.TAB_WALLET;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    /*POSITIONS IN HOME PAGE*/
    private static final int WALLET=5;
    private static final int TRACK=13;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> hashMap = remoteMessage.getData();
            String restaurantId = hashMap.get("store_id");
            String orderId = hashMap.get("order_id");
            String typeId = hashMap.get("type_id");
            String mBody = hashMap.get("body");
            String mTitle = hashMap.get("title");
            handleNow(mBody,mTitle, restaurantId, orderId,typeId);
        }
    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }


    private void handleNow(String messageBody,String title, String restaurantId, String orderTransactionId,String typeId) {
        if(restaurantId!=null && orderTransactionId!=null){
            sendNotificationToHome(messageBody, restaurantId, orderTransactionId,typeId,title);
        }else if(typeId!=null && typeId.equals("3")){ //on order rejected by restaurant
            sendToOrderPage(orderTransactionId,messageBody,title);
        }else{
            sendNotificationToHome(messageBody, restaurantId, orderTransactionId,typeId,title);
        }

        showPopup(messageBody);
       /* if(title.equals("Order Accepted Notification")){
            showPopup(messageBody);
        }else if(title.equals("Order Rejected Notification")){
            showPopup(messageBody);
        }*/


    }

    void showPopup(String messageBody){
        if(isAppInForeground(BaseApplication.getContext())){
            Intent intent = new Intent("com.foodtogo.user.MyDialogAction");
            intent.putExtra("msg",messageBody);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    private boolean isAppInForeground(Context context)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
            String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();

            return foregroundTaskPackageName.toLowerCase().equals(context.getPackageName().toLowerCase());
        }
        else
        {
            ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(appProcessInfo);
            if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE)
            {
                return true;
            }

            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            // App is foreground, but screen is locked, so show notification
            return km.inKeyguardRestrictedInputMode();
        }
    }

    public static boolean isActivityRunning(Class activityClass) {
        ActivityManager activityManager = (ActivityManager) BaseApplication.getContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }
        return false;
    }
    private void sendToOrderPage(String orderId,String messageBody,String title){
        Intent intent = new Intent(this, OrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderId);
        bundle.putString(ORDER_CANCEL_MERCHANT, CANCELLED);
        intent.putExtras(bundle);
        Intent[] arrayIntent;
        if (isActivityRunning(OrderActivity.class)) {
            arrayIntent = new Intent[]{intent};
        } else {
            arrayIntent = new Intent[]{intent, intent};
        }
        composeNotification(arrayIntent,messageBody,title,"Track Order");
    }

    private void sendNotificationToHome(String messageBody, String restaurantId, String orderTransactionId,
                                        String typeId,String title) {
        String clickAction="";
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if(typeId!=null && typeId.equals("5")){
           intent.putExtra(TAB_POSITION,WALLET);
            clickAction="Your Wallet";
        }else{

            if(restaurantId!=null && orderTransactionId!=null){
                intent.putExtra(ORDER_ID, orderTransactionId);
                intent.putExtra(RESTAURANT_ID, restaurantId);
                intent.putExtra(TAB_POSITION, TRACK);
                clickAction="Track Order";
            }
        }

        Intent[] arrayIntent;
        if (isActivityRunning(Home.class)) {
            arrayIntent = new Intent[]{intent};
        } else {
            arrayIntent = new Intent[]{intent, intent};
        }
      composeNotification(arrayIntent,messageBody,title,clickAction);
    }

    void composeNotification( Intent[] arrayIntent,String messageBody,String title,String clickAction){
        PendingIntent pendingIntent = PendingIntent.getActivities(this,
                0,
                arrayIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ding_notification);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setColor(this.getResources().getColor(R.color.app_theme_clr))
                        .setSmallIcon(R.drawable.ic_noti_logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .addAction(R.drawable.ic_noti_logo, clickAction, pendingIntent) // #
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(title);
        bigTextStyle.bigText(messageBody);
        notificationBuilder.setStyle(bigTextStyle);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/ding_notification");
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();


            String mChannelId = BaseApplication.getContext().getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(mChannelId,getString(R.string.fcm_message), NotificationManager.IMPORTANCE_HIGH);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(messageBody);
            notificationManager.createNotificationChannel(channel);
            channel.setSound(alarmSound,attributes);
            channel.enableLights(true);
            notificationBuilder.setChannelId(channelId);
            notificationManager.createNotificationChannel(channel);
        }
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), defaultSoundUri);
        r.play();
        notificationManager.notify(1, notificationBuilder.build());

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }
}