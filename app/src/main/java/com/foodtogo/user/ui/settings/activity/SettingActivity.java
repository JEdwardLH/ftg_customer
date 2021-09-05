package com.foodtogo.user.ui.settings.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.base.LocaleHelper;
import com.foodtogo.user.model.settings.MenuSettings;
import com.foodtogo.user.ui.home.activity.Home;
import com.foodtogo.user.ui.settings.adapter.SettingsAdapter;
import com.foodtogo.user.ui.settings.interfaces.ItemListener;
import com.foodtogo.user.ui.settings.mvp.SettingsContractor;
import com.foodtogo.user.ui.settings.mvp.SettingsPresenter;

import java.util.List;

import butterknife.BindView;


public class SettingActivity extends BaseActivity implements SettingsContractor.View, ItemListener {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<MenuSettings> menuSettingsList;

    SettingsPresenter settingsPresenter;
    private final String ENGLISH = "en";
    private final String THAI = "th";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsPresenter = new SettingsPresenter(this, appRepository);
        settingsPresenter.requestMenu(this);
        setupToolBar();
        tvTitle.setText(R.string.settings);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_all;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        settingsPresenter.close();
    }


    @Override
    public void showLoadingView() {
        showProgressDialog();
    }

    @Override
    public void hideLoadingView() {
        hideProgressDialog();
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
    public void showSettingsMenu(List<MenuSettings> _menuSettingsList) {
        this.menuSettingsList = _menuSettingsList;
        SettingsAdapter itemListDataAdapter = new SettingsAdapter(this, menuSettingsList);
        itemListDataAdapter.setItemListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(itemListDataAdapter);
    }

    @Override
    public void showPasswordChangedSuccess(String message) {
        showSuccessDialog(0, message);
    }

    @Override
    public void itemClick(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                changePassword();
                break;
            case 1:
            case 4:
            case 2:
            case 5:
            case 6:
                bundle.putString(TITLE, menuSettingsList.get(position).getMenuName());
                bundle.putInt(TAB_POSITION, position);
                changeActivityExtras(SettingMenuActivity.class, bundle);
                break;
            case 3:
                chooseLanguage();
                break;

        }


    }

    private void chooseLanguage() {
        final Dialog dialog = new Dialog(SettingActivity.this, R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_choose_language);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final RadioGroup radioGroup = dialog.findViewById(R.id.radio_group);
        final RadioButton radioEnglish = dialog.findViewById(R.id.radio_eng);
        final RadioButton radioThai = dialog.findViewById(R.id.radio_spanish);
        String languageCode = appRepository.getLanguageCode();
        if (languageCode.equals(ENGLISH))
            radioEnglish.setChecked(true);
        else if(languageCode.equals(THAI))
            radioThai.setChecked(true);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_eng) {
                appRepository.setLanguageCode(ENGLISH);
                setLanguage();
            } else {
                appRepository.setLanguageCode(THAI);
                setLanguage();

            }
            dialog.dismiss();
            changeActivityClearBackStack(Home.class);
        });


        dialog.show();
    }

    private void changePassword() {
        final Dialog dialog = new Dialog(context,R.style.DefaultDialog);
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText etOldPassword = dialog.findViewById(R.id.et_old_password);
        final EditText etNewPassword = dialog.findViewById(R.id.et_new_password);
        final EditText etNewConfirmPassword = dialog.findViewById(R.id.et_new_confirm_password);
        final Button btnGo = dialog.findViewById(R.id.btn_go);
        btnGo.setOnClickListener(v -> {
            if (etOldPassword.getText().toString().trim().equals("")) {
                Toast.makeText(SettingActivity.this, getResources().getString(R.string.warning_empty_old_password), Toast.LENGTH_SHORT).show();
            } else if (etNewPassword.getText().toString().trim().equals("")) {
                Toast.makeText(SettingActivity.this, getResources().getString(R.string.warning_empty_new_password), Toast.LENGTH_SHORT).show();
            } else if (etNewConfirmPassword.getText().toString().trim().equals("")) {
                Toast.makeText(SettingActivity.this, getResources().getString(R.string.warning_empty_confirm_new_password), Toast.LENGTH_SHORT).show();
            } else if (!etNewPassword.getText().toString().trim().equals(etNewConfirmPassword.getText().toString().trim())) {
                Toast.makeText(SettingActivity.this, getResources().getString(R.string.password_confirm_does_not_match), Toast.LENGTH_SHORT).show();
            } else {
                hideKeyboard(v);
                dialog.dismiss();
                settingsPresenter.requestChangePassword(etOldPassword.getText().toString().trim(), etNewPassword.getText().toString().trim());
            }
        });
        dialog.show();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext( LocaleHelper.onAttach(base));

    }
}
