package com.mk.androidtest.ObjectBoxEntity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by user on 10/2/2017.
 */
@Entity
public class UserVideoSelection {
    @Id
    long id;
    private String videoID;
    private int upVote;
    private int downVote;


    public UserVideoSelection(String videoID, int upVote, int downVote) {
        this.videoID = videoID;
        this.upVote = upVote;
        this.downVote = downVote;
    }

    public UserVideoSelection() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }
}
