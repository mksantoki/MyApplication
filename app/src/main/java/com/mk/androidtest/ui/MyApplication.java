package com.mk.androidtest.ui;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.devbrackets.android.exomedia.ExoMedia;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.mk.androidtest.ComponentNetworkModule.ComponentNetworkModule;
import com.mk.androidtest.ComponentNetworkModule.DaggerComponentNetworkModule;
import com.mk.androidtest.Models.Data;
import com.mk.androidtest.ObjectBoxEntity.MyObjectBox;
import com.mk.androidtest.RemortNetwork.NetworkModule;
import com.mk.androidtest.VideoPlayer.manager.PlaylistManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import io.objectbox.BoxStore;
import okhttp3.OkHttpClient;

/**
 * Created by user on 9/30/2017.
 */

public class MyApplication extends Application {

    public static final String TAG = "Test App";
    public static RefWatcher refWatcher;
    ComponentNetworkModule networkModule;
    private static PlaylistManager playlistManager;

    private static List<Data> datas;
    private BoxStore boxStore;
    private static MyApplication application;

    @Override
    public void onCreate() {
        enableStrictMode();
        super.onCreate();

        application = this;

        //TODO MK 10/2/2017 ==> NOTE :- objectbox database store
        boxStore = MyObjectBox.builder().androidContext(this).build();
        //TODO MK 10/2/2017 ==> NOTE :- A memory leak detection library for Android and Java.
        refWatcher = LeakCanary.install(this);
        //TODO MK 10/2/2017 ==> NOTE :- API call
        networkModule = DaggerComponentNetworkModule.builder().networkModule(new NetworkModule()).build();
        //TODO MK 10/2/2017 ==> NOTE :- Player
        playlistManager = new PlaylistManager();

        configureExoMedia();
    }

    public static PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    public ComponentNetworkModule getNetworkModule() {
        return networkModule;
    }

    public static MyApplication getApplication() {
        return application;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        application = null;
        playlistManager = null;
    }

    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());

    }

    private void configureExoMedia() {
        // Registers the media sources to use the OkHttp client instead of the standard Apache one
        // Note: the OkHttpDataSourceFactory can be found in the ExoPlayer extension library `extension-okhttp`
        ExoMedia.setDataSourceFactoryProvider(new ExoMedia.DataSourceFactoryProvider() {
            @NonNull
            @Override
            public DataSource.Factory provide(@NonNull String userAgent, @Nullable TransferListener<? super DataSource> listener) {
                // Updates the network data source to use the OKHttp implementation
                DataSource.Factory upstreamFactory = new OkHttpDataSourceFactory(new OkHttpClient(), userAgent, listener);

                // Adds a cache around the upstreamFactory
                Cache cache = new SimpleCache(getCacheDir(), new LeastRecentlyUsedCacheEvictor(50 * 1024 * 1024));
                return new CacheDataSourceFactory(cache, upstreamFactory, CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
            }
        });
    }

    public static List<Data> getDatas() {
        return datas;
    }

    public static void setDatas(List<Data> datas, int pageNumber) {
        if (pageNumber == 0) {
            MyApplication.datas = datas;
        } else {
            MyApplication.datas.addAll(datas);
        }

    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
