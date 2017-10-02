package com.mk.androidtest.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Original implements Parcelable {
    private String webp;

    private String height;

    private String mp4_size;

    private String frames;

    private String width;

    private String mp4;

    private String url;

    private String webp_size;

    private String size;

    public String getWebp ()
    {
        return webp;
    }

    public void setWebp (String webp)
    {
        this.webp = webp;
    }

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    public String getMp4_size ()
    {
        return mp4_size;
    }

    public void setMp4_size (String mp4_size)
    {
        this.mp4_size = mp4_size;
    }

    public String getFrames ()
    {
        return frames;
    }

    public void setFrames (String frames)
    {
        this.frames = frames;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    public String getMp4 ()
    {
        return mp4;
    }

    public void setMp4 (String mp4)
    {
        this.mp4 = mp4;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getWebp_size ()
    {
        return webp_size;
    }

    public void setWebp_size (String webp_size)
    {
        this.webp_size = webp_size;
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
        return "ClassPojo [webp = "+webp+", height = "+height+", mp4_size = "+mp4_size+", frames = "+frames+", width = "+width+", mp4 = "+mp4+", url = "+url+", webp_size = "+webp_size+", size = "+size+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.webp);
        dest.writeString(this.height);
        dest.writeString(this.mp4_size);
        dest.writeString(this.frames);
        dest.writeString(this.width);
        dest.writeString(this.mp4);
        dest.writeString(this.url);
        dest.writeString(this.webp_size);
        dest.writeString(this.size);
    }

    public Original() {
    }

    protected Original(Parcel in) {
        this.webp = in.readString();
        this.height = in.readString();
        this.mp4_size = in.readString();
        this.frames = in.readString();
        this.width = in.readString();
        this.mp4 = in.readString();
        this.url = in.readString();
        this.webp_size = in.readString();
        this.size = in.readString();
    }

    public static final Parcelable.Creator<Original> CREATOR = new Parcelable.Creator<Original>() {
        @Override
        public Original createFromParcel(Parcel source) {
            return new Original(source);
        }

        @Override
        public Original[] newArray(int size) {
            return new Original[size];
        }
    };
}
