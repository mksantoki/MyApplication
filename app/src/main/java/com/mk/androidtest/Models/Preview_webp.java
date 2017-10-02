package com.mk.androidtest.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Preview_webp implements Parcelable {
    private String height;

    private String width;

    private String url;

    private String size;

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [height = "+height+", width = "+width+", url = "+url+", size = "+size+"]";
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
        dest.writeString(this.size);
    }

    public Preview_webp() {
    }

    protected Preview_webp(Parcel in) {
        this.height = in.readString();
        this.width = in.readString();
        this.url = in.readString();
        this.size = in.readString();
    }

    public static final Parcelable.Creator<Preview_webp> CREATOR = new Parcelable.Creator<Preview_webp>() {
        @Override
        public Preview_webp createFromParcel(Parcel source) {
            return new Preview_webp(source);
        }

        @Override
        public Preview_webp[] newArray(int size) {
            return new Preview_webp[size];
        }
    };
}