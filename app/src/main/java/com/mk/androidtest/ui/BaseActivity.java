package com.mk.androidtest.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mk.androidtest.ComponentNetworkModule.DaggerSharedPrerenceComponent;
import com.mk.androidtest.Util.SharedPreferenceModule;

/**
 * Created by user on 9/30/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Bundle savedInstanceState;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(setLayout());
        initControl();
        findView();
        titlebar();
        initListener();

        preferences = DaggerSharedPrerenceComponent.builder().sharedPreferenceModule(new SharedPreferenceModule(getApplication())).build().getSharedPreference();

    }

    protected abstract int setLayout();

    protected abstract void initControl();

    protected abstract void findView();

    protected abstract void titlebar();

    protected abstract void initListener();

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

}
