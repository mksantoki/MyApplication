package com.mk.androidtest.RemortNetwork;


import com.mk.androidtest.Models.Model_VideosList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {

    @GET("search")
    Observable<Model_VideosList> getVideosList(
            @Query("q") String q,
            @Query("limit") int limit,
            @Query("api_key") String api_key,
            @Query("offset") int offset);

}
