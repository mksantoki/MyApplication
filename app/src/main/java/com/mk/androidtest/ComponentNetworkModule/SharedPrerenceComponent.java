package com.mk.androidtest.ComponentNetworkModule;

import android.content.SharedPreferences;


import com.mk.androidtest.Util.SharedPreferenceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SharedPreferenceModule.class})
public interface SharedPrerenceComponent {
    SharedPreferences getSharedPreference();
}