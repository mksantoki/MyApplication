package com.mk.androidtest.ui.Controller.VideoPlayController;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.devbrackets.android.playlistcore.listener.PlaylistListener;
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager;
import com.devbrackets.android.playlistcore.service.PlaylistServiceCore;
import com.mk.androidtest.Models.Data;
import com.mk.androidtest.ObjectBoxEntity.UserVideoSelection;
import com.mk.androidtest.R;
import com.mk.androidtest.Util.CheckInternetConnection;
import com.mk.androidtest.VideoPlayer.data.MediaItem;
import com.mk.androidtest.VideoPlayer.manager.PlaylistManager;
import com.mk.androidtest.VideoPlayer.playlist.VideoApi;
import com.mk.androidtest.ui.BaseActivity;
import com.mk.androidtest.ui.MyApplication;

import java.util.LinkedList;
import java.util.List;


public class VideoPlayerActivity extends BaseActivity implements PlaylistListener<MediaItem>, View.OnClickListener {
    public final int tvUpID = 1101;
    public final int tvDownID = 1102;

    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final int PLAYLIST_ID = 6; //Arbitrary, for the example (different from audio)

    protected VideoView videoView;
    protected PlaylistManager playlistManager;

    protected int selectedIndex;
    protected boolean pausedInOnStop = false;

    protected VideoPlayPresenter videoPlayPresenter;

    public TextView tvTxtUpVote, tvTxtDownVote;
    public MediaItem mediaItem;
    private UserVideoSelection userVideoSelection;
    private LinearLayout llBottomView;

    private LinearLayout.LayoutParams tvLayoutParams;

    @Override
    protected int setLayout() {
        return R.layout.video_player_activity;
    }

    @Override
    protected void initControl() {
        Bundle extras = getIntent().getExtras();
        selectedIndex = extras.getInt(EXTRA_INDEX, 0);
        videoPlayPresenter = new VideoPlayPresenter(this);
        setupPlaylistManager();
    }

    @Override
    protected void findView() {
        videoView = (VideoView) findViewById(R.id.video_play_activity_video_view);
        llBottomView = videoView.findViewById(R.id.exomedia_controls_interactive_container);
        addCustomView();
    }

    private void addCustomView() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setPadding(dpToPx(16), 0, dpToPx(16), 0);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        tvLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.weight = 1;
        tvTxtUpVote = getCustomTextview(tvUpID, 0, R.mipmap.iv_up);
        linearLayout.addView(tvTxtUpVote);
        tvTxtDownVote = getCustomTextview(tvDownID, 0, R.mipmap.iv_down);
        linearLayout.addView(tvTxtDownVote);
        llBottomView.addView(linearLayout);
    }

    @Override
    protected void titlebar() {

    }

    @Override
    protected void initListener() {
        playlistManager.setVideoPlayer(new VideoApi(videoView));
        playlistManager.play(0, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoView.isPlaying()) {
            pausedInOnStop = true;
            videoView.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (pausedInOnStop) {
            videoView.start();
            pausedInOnStop = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        playlistManager.unRegisterPlaylistListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playlistManager = MyApplication.getPlaylistManager();
        playlistManager.registerPlaylistListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playlistManager.invokeStop();
    }

    @Override
    public boolean onPlaylistItemChanged(MediaItem currentItem, boolean hasNext, boolean hasPrevious) {
        this.mediaItem = currentItem;
        userVideoSelection = videoPlayPresenter.checkVideVotes(mediaItem.getVideoId());
        if (userVideoSelection != null) {
            updateVide(tvTxtUpVote, userVideoSelection.getUpVote());
            updateVide(tvTxtDownVote, userVideoSelection.getDownVote());
        } else {
            updateVide(tvTxtUpVote, 0);
            updateVide(tvTxtDownVote, 0);
        }

        return false;
    }

    private void updateVide(TextView tvTxtUpVote, int upVote) {
        tvTxtUpVote.setText(upVote + " " + getString(R.string.votes));
    }

    @Override
    public boolean onPlaybackStateChanged(@NonNull PlaylistServiceCore.PlaybackState playbackState) {
        if (playbackState == PlaylistServiceCore.PlaybackState.STOPPED) {
            finish();
            return true;
        } else if (playbackState == PlaylistServiceCore.PlaybackState.ERROR) {
            showErrorMessage();
        }
        return false;
    }

    private TextView getCustomTextview(int id, int vote, int draID) {
        TextView tvUpCount = new TextView(this);
        tvUpCount.setId(id);
        tvUpCount.setLayoutParams(tvLayoutParams);
        tvUpCount.setText(vote + " " + getString(R.string.votes));
        tvUpCount.setTextColor(Color.WHITE);
        tvUpCount.setGravity(Gravity.CENTER_VERTICAL);
        tvUpCount.setCompoundDrawablesWithIntrinsicBounds(draID, 0, 0, 0);
        tvUpCount.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.text_size));
        tvUpCount.setOnClickListener(this);
        return tvUpCount;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    protected void showErrorMessage() {
        videoPlayPresenter.shoeAlertDialog((CheckInternetConnection.isNetworkConnected(this) ? String.format("There was an error playing \"%s\"", playlistManager.getCurrentItem().getTitle()) : getString(R.string.no_internet)));
    }

    /**
     * Retrieves the playlist instance and performs any generation
     * of content if it hasn't already been performed.
     */
    private void setupPlaylistManager() {
        playlistManager = MyApplication.getPlaylistManager();
        List<MediaItem> mediaItems = new LinkedList<>();
        for (Data data : MyApplication.getDatas()) {
            MediaItem mediaItem = new MediaItem(data, false);
            mediaItems.add(mediaItem);
        }
        playlistManager.setAllowedMediaType(BasePlaylistManager.AUDIO | BasePlaylistManager.VIDEO);
        playlistManager.setParameters(mediaItems, selectedIndex);
        playlistManager.setId(PLAYLIST_ID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case tvUpID:
                userVideoSelection = videoPlayPresenter.updateVideoVote(mediaItem.getVideoId(), VideoPlayPresenter.UPDATE_UP_VOTE);
                updateVide(tvTxtUpVote, userVideoSelection.getUpVote());
                break;
            case tvDownID:
                userVideoSelection = videoPlayPresenter.updateVideoVote(mediaItem.getVideoId(), VideoPlayPresenter.UPDATE_DOWN_VOTE);
                updateVide(tvTxtDownVote, userVideoSelection.getDownVote());
                break;
        }
    }
}
