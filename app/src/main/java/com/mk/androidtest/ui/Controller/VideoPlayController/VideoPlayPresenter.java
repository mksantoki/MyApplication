package com.mk.androidtest.ui.Controller.VideoPlayController;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.mk.androidtest.ObjectBoxEntity.UserVideoSelection;
import com.mk.androidtest.ObjectBoxEntity.UserVideoSelection_;
import com.mk.androidtest.R;
import com.mk.androidtest.Util.CheckInternetConnection;
import com.mk.androidtest.ui.MyApplication;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

import static com.mk.androidtest.ui.MyApplication.getApplication;

/**
 * Created by user on 10/2/2017.
 */

public class VideoPlayPresenter {

    public static final int UPDATE_UP_VOTE = 1;
    public static final int UPDATE_DOWN_VOTE = 2;

    private UserVideoSelection selection;

    private Box<UserVideoSelection> selectionBox;
    private Query<UserVideoSelection> selectionQuery;
    private Activity activity;
    private BoxStore boxStore;

    public VideoPlayPresenter(Activity activity) {
        this.activity = activity;
        boxStore = ((MyApplication) getApplication()).getBoxStore();
        selectionBox = boxStore.boxFor(UserVideoSelection.class);

        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        selectionQuery = selectionBox.query().order(UserVideoSelection_.videoID).build();
    }

    public UserVideoSelection checkVideVotes(String videoID) {
        List<UserVideoSelection> selection = selectionBox.find(UserVideoSelection_.videoID, videoID);
        if (selection != null && selection.size() > 0) {
            return selection.get(0);
        } else {
            return null;
        }

    }

    public UserVideoSelection updateVideoVote(String videoID, int type) {
        selection = checkVideVotes(videoID);
        if (selection != null) {
            if (type == UPDATE_UP_VOTE) {
                selection.setUpVote(selection.getUpVote() + 1);
            } else {
                selection.setDownVote(selection.getDownVote() + 1);
            }
            selectionBox.put(selection);
            return selection;
        } else {
            selection = new UserVideoSelection(videoID, 0, 0);
            if (type == UPDATE_UP_VOTE) {
                selection.setUpVote(1);
            } else {
                selection.setDownVote(1);
            }
            selectionBox.put(selection);
        }
        return selection;
    }

    public void shoeAlertDialog(String message) {
        new AlertDialog.Builder(activity)
                .setTitle("Playback Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.finish();
                    }
                })
                .show();
    }

}
