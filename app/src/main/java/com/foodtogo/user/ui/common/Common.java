package com.foodtogo.user.ui.common;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.foodtogo.user.R;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class Common {
    public static SimpleTooltip buildToolTip(Context context, View targetView, String text, String extraCharge, String currency){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.tool_tip_delivery_fee,null);

        TextView tvExtraCharges = view.findViewById(R.id.tvExtraCharge);
        tvExtraCharges.setText(currency+extraCharge);

        return new SimpleTooltip.Builder(context)
                .anchorView(targetView)
                .text(text)
                .contentView(view,R.id.tvMessage)
                .gravity(Gravity.TOP)
                .backgroundColor(Color.BLACK)
                .arrowColor(Color.BLACK)
                .textColor(Color.WHITE)
                .arrowHeight(20)
                .arrowWidth(20)
                .maxWidth(500f)
                .animated(false)
                .transparentOverlay(true)
                .build();
    }
}
