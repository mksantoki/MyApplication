package com.mk.androidtest.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Images implements Parcelable {
    private Original original;

    private Fixed_height_small_still fixed_height_small_still;
    private Preview_webp preview_webp;


    public Original getOriginal() {
        return original;
    }

    public void setOriginal(Original original) {
        this.original = original;
    }

    public Fixed_height_small_still getFixed_height_small_still() {
        return fixed_height_small_still;
    }

    public void setFixed_height_small_still(Fixed_height_small_still fixed_height_small_still) {
        this.fixed_height_small_still = fixed_height_small_still;
    }

    public Preview_webp getPreview_webp() {
        return preview_webp;
    }

    public void setPreview_webp(Preview_webp preview_webp) {
        this.preview_webp = preview_webp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.original, flags);
        dest.writeParcelable(this.fixed_height_small_still, flags);
        dest.writeParcelable(this.preview_webp, flags);
    }

    public Images() {
    }

    protected Images(Parcel in) {
        this.original = in.readParcelable(Original.class.getClassLoader());
        this.fixed_height_small_still = in.readParcelable(Fixed_height_small_still.class.getClassLoader());
        this.preview_webp = in.readParcelable(Preview_webp.class.getClassLoader());
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}

