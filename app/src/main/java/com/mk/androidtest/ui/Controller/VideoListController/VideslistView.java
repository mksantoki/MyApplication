package com.mk.androidtest.ui.Controller.VideoListController;

import com.mk.androidtest.Models.Model_VideosList;

/**
 * Created by ennur on 6/25/16.
 */
public interface VideslistView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getityListSuccess(Model_VideosList videosLists);

    void noInterNet();

}
