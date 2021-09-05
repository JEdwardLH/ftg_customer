package com.foodtogo.user.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DialogActivity extends BaseActivity {
    @BindView(R.id.tv_message)
    TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent()!=null && getIntent().getStringExtra("msg")!=null){
           tvMsg.setText(getIntent().getStringExtra("msg"));
        }
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_activity;
    }

    @OnClick(R.id.btn_go)
    void closeActivity(){
        finish();
    }
}
