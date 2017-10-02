package com.mk.androidtest.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Fixed_height_small_still implements Parcelable {
    private String height;

    private String width;

    private String url;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPojo [height = " + height + ", width = " + width + ", url = " + url + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.height);
        dest.writeString(this.width);
        dest.writeString(this.url);
    }

    public Fixed_height_small_still() {
    }

    protected Fixed_height_small_still(Parcel in) {
        this.height = in.readString();
        this.width = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Fixed_height_small_still> CREATOR = new Parcelable.Creator<Fixed_height_small_still>() {
        @Override
        public Fixed_height_small_still createFromParcel(Parcel source) {
            return new Fixed_height_small_still(source);
        }

        @Override
        public Fixed_height_small_still[] newArray(int size) {
            return new Fixed_height_small_still[size];
        }
    };
}