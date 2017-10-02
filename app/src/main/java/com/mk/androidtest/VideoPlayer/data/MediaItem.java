package com.mk.androidtest.VideoPlayer.data;

import com.devbrackets.android.playlistcore.manager.IPlaylistItem;
import com.mk.androidtest.Models.Data;
import com.mk.androidtest.VideoPlayer.manager.PlaylistManager;

/**
 * A custom {@link IPlaylistItem}
 * to hold the information pertaining to the audio and video items
 */
public class MediaItem implements IPlaylistItem {

    private Data sample;
    boolean isAudio;
    private String videoId;

    public MediaItem(Data sample, boolean isAudio) {
        this.sample = sample;
        this.isAudio = isAudio;
        this.videoId = sample.getId();
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public long getPlaylistId() {
        return 0;
    }

    @Override
    public int getMediaType() {
        return isAudio ? PlaylistManager.AUDIO : PlaylistManager.VIDEO;
    }

    @Override
    public String getMediaUrl() {
        return sample.getImages().getOriginal().getMp4();
    }

    @Override
    public String getDownloadedMediaUri() {
        return null;
    }

    @Override
    public String getThumbnailUrl() {
        return sample.getImages().getFixed_height_small_still().getUrl();
    }

    @Override
    public String getArtworkUrl() {
        return sample.getContent_url();
    }

    @Override
    public String getTitle() {
        return sample.getUsername();
    }

    @Override
    public String getAlbum() {
        return "";
    }

    @Override
    public String getArtist() {
        return "";
    }


    public String getVideoId() {
        return videoId;
    }

}