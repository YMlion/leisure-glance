package com.ymlion.leisure.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by YMlion on 2017/7/7.
 */

@Entity
public class Coser implements Parcelable {

    /**
     * id : 4967
     * title : 蕾姆性感兔女郎装COS
     * url : https://img.7531.com/wjgame/up/lo/upload_319d5a25339fea663bcecd869b76b3dd.jpg
     * width : 650
     * height : 996
     */

    @Id
    private long id;
    private String title;
    private String url;
    private int width;
    private int height;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    public Coser() {
    }

    protected Coser(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    @Generated(hash = 1449897741)
    public Coser(long id, String title, String url, int width, int height) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public static final Parcelable.Creator<Coser> CREATOR = new Parcelable.Creator<Coser>() {
        @Override
        public Coser createFromParcel(Parcel source) {
            return new Coser(source);
        }

        @Override
        public Coser[] newArray(int size) {
            return new Coser[size];
        }
    };
}
