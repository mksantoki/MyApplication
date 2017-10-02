package com.mk.androidtest.RemortNetwork;


import com.mk.androidtest.Models.Model_VideosList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ennur on 6/25/16.
 */
public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getVideos(final GetVideoListCallback callback, String searchKey, int limit, String api_key, int page) {

        return networkService.getVideosList(searchKey, limit, api_key, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Model_VideosList>>() {
                    @Override
                    public Observable<? extends Model_VideosList> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Model_VideosList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Model_VideosList videosLists) {
                        callback.onSuccess(videosLists);

                    }
                });
    }

    public interface GetVideoListCallback {
        void onSuccess(Model_VideosList videosLists);

        void onError(NetworkError networkError);
    }
}
