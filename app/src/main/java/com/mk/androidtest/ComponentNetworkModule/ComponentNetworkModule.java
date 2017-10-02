package com.mk.androidtest.ComponentNetworkModule;

import com.mk.androidtest.RemortNetwork.NetworkModule;
import com.mk.androidtest.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface ComponentNetworkModule {
    void inject(MainActivity mainActivity);
}