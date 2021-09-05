package com.alium.nibo.base;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alium.nibo.R;
import com.alium.nibo.utils.NiboConstants;

/**
 * Created by abdulmujibaliu on 9/7/17.
 */

public class BaseNiboActivity extends AppCompatActivity {


    public void replaceFragment(Fragment fragment, Context context) {
        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame_container, fragment, NiboConstants._FRAGMENT_TAG);
        ft.commit();
    }



}
