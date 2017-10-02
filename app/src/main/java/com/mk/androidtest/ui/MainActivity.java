package com.mk.androidtest.ui;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bluelinelabs.conductor.ChangeHandlerFrameLayout;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.mk.androidtest.R;
import com.mk.androidtest.RemortNetwork.Service;
import com.mk.androidtest.ui.Controller.VideoListController.VideoListController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ActionBarProvider {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.controller_container)
    ChangeHandlerFrameLayout container;

    @Inject
    public Service service;

    //TODO MK 9/30/2017 ==> NOTE :- this router use to handle step and flow of application
    private Router router;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initControl() {
        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.getNetworkModule().inject(this);

    }

    @Override
    protected void findView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void titlebar() {
        setSupportActionBar(toolbar);

    }

    @Override
    protected void initListener() {
        router = Conductor.attachRouter(this, container, getSavedInstanceState());
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new VideoListController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    public Service getService() {
        return service;
    }
}
