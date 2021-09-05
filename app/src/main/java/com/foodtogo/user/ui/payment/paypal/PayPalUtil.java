package com.foodtogo.user.ui.payment.paypal;

import android.content.Context;
import android.content.Intent;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

public class PayPalUtil {

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    public static final String PAYPAL_CLIENT_ID = "AVomx52Gh-UDWy2BSntGqIVSwi5dnc9t6vCdMRyohM_C2Llk6xep2L22sRm9nLAVt-zG5i9zwF8NC1ft";

    //Paypal Configuration Object
    public static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID);

    public static void startPayPalService(Context context) {
        Intent intent = new Intent(context, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        context.startService(intent);
    }

    public static void stopPayPalService(Context context) {
        context.stopService(new Intent(context, PayPalService.class));
    }




}
