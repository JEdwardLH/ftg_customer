package com.foodtogo.user.util;


import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.foodtogo.user.R;

import java.util.ArrayList;

public class CenteredTitleToolbar extends Toolbar {

    private TextView _titleTextView;
    private int _screenWidth;
    private boolean _centerTitle = false;

    public CenteredTitleToolbar(Context context) {
        super(context);
        init();
    }

    public CenteredTitleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CenteredTitleToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        _screenWidth = getScreenSize().x;
        _titleTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.title, this,false);
        addView(_titleTextView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        _screenWidth = getWidth();
        if (_centerTitle) {
            int[] location = new int[2];
            _titleTextView.getLocationOnScreen(location);
            _titleTextView.setTranslationX((getScreenSize().x-_screenWidth)+_titleTextView.getTranslationX() + (-location[0] + _screenWidth / 2 - _titleTextView.getWidth() / 2));
        }else{
            _titleTextView.setTranslationX(0);
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        _titleTextView.setText(title);
        requestLayout();
    }

    @Override
    public void setTitle(int titleRes) {
        _titleTextView.setText(getResources().getString(titleRes));
    }

    public void setTitleCentered(boolean centered) {
        _centerTitle = centered;
        requestLayout();
    }

    public void setTitleColor(int color) {
        _titleTextView.setTextColor(color);
        //  requestLayout();
    }

    private Point getScreenSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        return screenSize;
    }

    public static View getToolbarNavigationIcon(Toolbar toolbar){
        //check if contentDescription previously was set
        boolean hadContentDescription = !TextUtils.isEmpty(toolbar.getNavigationContentDescription());
        String contentDescription = hadContentDescription ? toolbar.getNavigationContentDescription().toString() : "navigationIcon";
        toolbar.setNavigationContentDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setNavigationContentDescription ensures its existence
        View navIcon = null;
        if(potentialViews.size() > 0){
            navIcon = potentialViews.get(0); //navigation icon is ImageButton
        }
        //Clear content description if not previously present
        if(!hadContentDescription)
            toolbar.setNavigationContentDescription(null);
        return navIcon;
    }

    public void setTitleAlpha(float alpha) {
        _titleTextView.setAlpha(alpha);
    }
}
