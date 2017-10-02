package com.mk.androidtest.Models;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;

public class Data implements Parcelable {
    private String source_post_url;

    private String trending_datetime;

    private String type;

    private String url;

    private String content_url;

    private String id;

    private String import_datetime;

    private String username;

    private String is_indexable;

    private String source;

    private String embed_url;

    private String source_tld;

    private Images images;

    private String bitly_gif_url;

    private String slug;

    private String bitly_url;

    private String rating;

    public String getSource_post_url ()
    {
        return source_post_url;
    }

    public void setSource_post_url (String source_post_url)
    {
        this.source_post_url = source_post_url;
    }

    public String getTrending_datetime ()
    {
        return trending_datetime;
    }

    public void setTrending_datetime (String trending_datetime)
    {
        this.trending_datetime = trending_datetime;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getContent_url ()
    {
        return content_url;
    }

    public void setContent_url (String content_url)
    {
        this.content_url = content_url;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getImport_datetime ()
    {
        return import_datetime;
    }

    public void setImport_datetime (String import_datetime)
    {
        this.import_datetime = import_datetime;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getIs_indexable ()
    {
        return is_indexable;
    }

    public void setIs_indexable (String is_indexable)
    {
        this.is_indexable = is_indexable;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public String getEmbed_url ()
    {
        return embed_url;
    }

    public void setEmbed_url (String embed_url)
    {
        this.embed_url = embed_url;
    }

    public String getSource_tld ()
    {
        return source_tld;
    }

    public void setSource_tld (String source_tld)
    {
        this.source_tld = source_tld;
    }

    public Images getImages ()
    {
        return images;
    }

    public void setImages (Images images)
    {
        this.images = images;
    }

    public String getBitly_gif_url ()
    {
        return bitly_gif_url;
    }

    public void setBitly_gif_url (String bitly_gif_url)
    {
        this.bitly_gif_url = bitly_gif_url;
    }

    public String getSlug ()
    {
        return slug;
    }

    public void setSlug (String slug)
    {
        this.slug = slug;
    }

    public String getBitly_url ()
    {
        return bitly_url;
    }

    public void setBitly_url (String bitly_url)
    {
        this.bitly_url = bitly_url;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [source_post_url = "+source_post_url+", trending_datetime = "+trending_datetime+", type = "+type+", url = "+url+", content_url = "+content_url+", id = "+id+", import_datetime = "+import_datetime+", username = "+username+", is_indexable = "+is_indexable+", source = "+source+", embed_url = "+embed_url+", source_tld = "+source_tld+", images = "+images+", bitly_gif_url = "+bitly_gif_url+", slug = "+slug+", bitly_url = "+bitly_url+", rating = "+rating+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.source_post_url);
        dest.writeString(this.trending_datetime);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.content_url);
        dest.writeString(this.id);
        dest.writeString(this.import_datetime);
        dest.writeString(this.username);
        dest.writeString(this.is_indexable);
        dest.writeString(this.source);
        dest.writeString(this.embed_url);
        dest.writeString(this.source_tld);
        dest.writeParcelable(this.images, flags);
        dest.writeString(this.bitly_gif_url);
        dest.writeString(this.slug);
        dest.writeString(this.bitly_url);
        dest.writeString(this.rating);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.source_post_url = in.readString();
        this.trending_datetime = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.content_url = in.readString();
        this.id = in.readString();
        this.import_datetime = in.readString();
        this.username = in.readString();
        this.is_indexable = in.readString();
        this.source = in.readString();
        this.embed_url = in.readString();
        this.source_tld = in.readString();
        this.images = in.readParcelable(Images.class.getClassLoader());
        this.bitly_gif_url = in.readString();
        this.slug = in.readString();
        this.bitly_url = in.readString();
        this.rating = in.readString();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}