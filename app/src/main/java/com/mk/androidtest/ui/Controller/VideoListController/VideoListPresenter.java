package com.mk.androidtest.ui.Controller.VideoListController;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mk.androidtest.Models.Model_VideosList;
import com.mk.androidtest.R;
import com.mk.androidtest.RemortNetwork.NetworkError;
import com.mk.androidtest.RemortNetwork.Service;
import com.mk.androidtest.Util.CheckInternetConnection;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by user on 9/30/2017.
 */

public class VideoListPresenter {
    private final Service service;
    private final VideslistView view;
    private CompositeSubscription subscriptions;

    private Service.GetVideoListCallback getVideoListCallback;
    private Subscription subscription;

    private Activity activity;

    public VideoListPresenter(Activity activity, Service service, VideslistView view) {
        this.activity = activity;
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        initCallBack();
    }

    private void initCallBack() {
        getVideoListCallback = new Service.GetVideoListCallback() {

            @Override
            public void onSuccess(Model_VideosList videosLists) {
                view.removeWait();
                view.getityListSuccess(videosLists);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        };

    }

    public void getVideoList(String searchKey, int pagenumber) {
        if (CheckInternetConnection.isNetworkConnected(activity)) {
            view.showWait();
            if (subscription != null) {
                onStop();
            }
            subscription = service.getVideos(getVideoListCallback, searchKey, 20, "dc6zaTOxFJmzC", pagenumber*20);
        } else {
            view.noInterNet();
            Toast.makeText(activity, activity.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
