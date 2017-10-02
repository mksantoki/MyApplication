package com.mk.androidtest.ui.Controller.VideoListController;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.mk.androidtest.Models.Data;
import com.mk.androidtest.Models.Model_VideosList;
import com.mk.androidtest.R;
import com.mk.androidtest.Util.CheckInternetConnection;
import com.mk.androidtest.Util.Deprecation;
import com.mk.androidtest.ui.Controller.VideoPlayController.FullScreenVideoPlayerActivity;
import com.mk.androidtest.ui.Controller.VideoPlayController.VideoPlayerActivity;
import com.mk.androidtest.ui.Controller.base.BaseController;
import com.mk.androidtest.ui.MainActivity;
import com.mk.androidtest.ui.MyApplication;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;

import static com.mk.androidtest.Util.AppInputMethodManager.hideSoftInput;
import static com.mk.androidtest.Util.AppInputMethodManager.showSoftInput;

/**
 * Created by user on 9/30/2017.
 */

public class VideoListController extends BaseController implements VideslistView, VideoListAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.home_root)
    FrameLayout homeRoot;

    MainActivity mainActivity;
    EditText edtSearch;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.llLoading)
    LinearLayout llLoading;

    private ActionBar action;

    private VideoListAdapter videoListAdapter;
    private VideoListPresenter videoListPresenter;

    private int pageNumber = 1;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;

    private GridLayoutManager layoutManager;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading = true;

    public VideoListController() {
        setHasOptionsMenu(true);
        setRetainViewMode(RetainViewMode.RETAIN_DETACH);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_videolist, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        mainActivity = (MainActivity) getActivity();
        llLoading.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        onScrollHideSoftInput();

        if (videoListPresenter == null) {
            videoListPresenter = new VideoListPresenter(getActivity(), mainActivity.getService(), this);
            iniToFirstPage();
            tvNoData.setText(getActivity().getString(R.string.search_video));
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setAdapter(videoListAdapter);
        }

        //TODO MK 10/2/2017 ==> NOTE :- use to manage progress
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (videoListAdapter.getItemViewType(position)) {
                    case VideoListAdapter.VIEW_PROG:
                        return 2;
                    case VideoListAdapter.VIEW_ITEM:
                        return 1; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });
    }

    private void onScrollHideSoftInput() {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftInput(v, getActivity());
                return false;
            }
        });
    }

    //TODO MK 10/2/2017 ==> NOTE :-Inflate the options menu from XML
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        mSearchAction = menu.findItem(R.id.action_search);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //TODO MK 10/2/2017 ==> NOTE :-use to set name
    @Override
    protected String getTitle() {
        return getActivity().getString(R.string.video_list);
    }


    @Override
    protected void onChangeStarted(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        setOptionsMenuHidden(!changeType.isEnter);
        if (changeType.isEnter) {
            setTitle();
        }
    }


    //TODO MK 10/2/2017 ==> NOTE :- show hide Method progressDialog
    @Override
    public void showWait() {

        if (pageNumber == 0) {
            llLoading.setVisibility(View.VISIBLE);
        } else if (videoListAdapter != null) {
            videoListAdapter.addLoading();
        }

    }

    @Override
    public void removeWait() {
        if (pageNumber == 0) {
            llLoading.setVisibility(View.GONE);
        } else if (videoListAdapter != null) {
            videoListAdapter.removeLoading();
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {
    }

    //TODO MK 10/2/2017 ==> NOTE :- API CALL Response
    @Override
    public void getityListSuccess(Model_VideosList videosLists) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyApplication.setDatas(videosLists.getData(), pageNumber);
        if (videoListAdapter == null) {
            videoListAdapter = new VideoListAdapter(getActivity(), videosLists.getData(), this);
            recyclerView.setAdapter(videoListAdapter);
        } else {
            videoListAdapter.setData(videosLists.getData(), pageNumber);
        }

        if (videosLists.getData().size() <= 0 && pageNumber == 0) {
            tvNoData.setVisibility(View.VISIBLE);
            tvNoData.setText(getActivity().getString(R.string.search_video));
        }

        if (pageNumber == 0 && videosLists.getData().size() == 20) {
            setLoadMoreCall();
        }
        isLoading = false;
    }

    @Override
    public void noInterNet() {
        if (pageNumber == 0) {
            tvNoData.setText(getActivity().getString(R.string.no_internet));
            tvNoData.setVisibility(View.VISIBLE);
        }

    }

    private RecyclerView.OnScrollListener listener;

    private void setLoadMoreCall() {
        if (listener == null) {
            listener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        isLoading = true;
                        pageNumber = pageNumber + 1;
                        videoListPresenter.getVideoList(edtSearch.getText().toString().trim(), pageNumber);
                    }
                }
            };
        }
        registerOnScrollListener();
    }

    public void registerOnScrollListener() {
        if (listener != null) {
            recyclerView.addOnScrollListener(listener);
        }
    }

    public void unRegisterOnScrollListener() {
        if (listener != null) {
            recyclerView.removeOnScrollListener(listener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //TODO MK 10/2/2017 ==> NOTE :- Show and hide SearchView Code
    protected void handleMenuSearch() {
        if (action == null) {
            action = getActionBar(); //get the actionbar
        }

        if (isSearchOpened) { //test if the search is open
            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            hideSoftInput(edtSearch, getActivity());

            //add the search icon in the action bar
            mSearchAction.setIcon(Deprecation.getDrawable(getActivity(), R.mipmap.iv_search));
            isSearchOpened = false;
        } else { //open the search entry
            action.setDisplayShowCustomEnabled(true); //enable it to display a
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSearch = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor
            edtSearch.requestFocus();

            //open the keyboard focused in the edtSearch
            showSoftInput(edtSearch, getActivity());


            //add the close icon
            mSearchAction.setIcon(Deprecation.getDrawable(getActivity(), R.mipmap.iv_clear));
            isSearchOpened = true;
            setTextWatcher();
        }
    }

    //TODO MK 10/2/2017 ==> NOTE :- GOTO next screen on select any video
    @Override
    public void onClick(Data Item, int position) {
        if (CheckInternetConnection.isNetworkConnected(getActivity())) {
            Intent intent = new Intent(getActivity(), FullScreenVideoPlayerActivity.class);
            intent.putExtra(VideoPlayerActivity.EXTRA_INDEX, position);
            startActivity(intent);
        } else {
            Toast.makeText(mainActivity, getActivity().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    //TODO MK 10/2/2017 ==> NOTE :- search edittext text watcher
    public static class RxSearch {
        public static Observable<String> fromSearchView(@NonNull final EditText editText) {
            final BehaviorSubject<String> subject = BehaviorSubject.create("");

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!editable.toString().isEmpty()) {
                        subject.onNext(editable.toString());
                    }
                }
            });
            return subject;
        }
    }

    //TODO MK 10/2/2017 ==> NOTE :- Use to Call API to get data from server
    private void setTextWatcher() {
        RxSearch.fromSearchView(edtSearch)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (s.trim().length() > 0) {
                            iniToFirstPage();
                            videoListPresenter.getVideoList(s, pageNumber);
                        } else if (s.trim().length() <= 0) {
                            if (videoListAdapter != null) {
                                videoListAdapter.removeAllData();
                                tvNoData.setVisibility(View.VISIBLE);
                                tvNoData.setText(getActivity().getString(R.string.search_video));
                            }
                        }
                    }
                });
    }

    public void iniToFirstPage() {
        pageNumber = 0;
        unRegisterOnScrollListener();
        tvNoData.setVisibility(View.GONE);
        recyclerView.smoothScrollToPosition(0);
    }


}
