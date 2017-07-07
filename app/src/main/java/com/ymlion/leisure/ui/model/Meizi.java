package com.ymlion.leisure.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Meizi
 * Created by ymlion on 16/6/26.
 */
@Entity
public class Meizi implements Parcelable {


    /**
     * _id : 576caea6421aa90c875dc46b
     * createdAt : 2016-06-24T11:53:10.564Z
     * desc : 6.25
     * publishedAt : 2016-06-24T12:01:16.638Z
     * source : chrome
     * type : Meizi
     * url : http://ww1.sinaimg.cn/large/610dc034jw1f566a296rpj20lc0sggoj.jpg
     * used : true
     * who : 代码家
     */
    @Id
    private String _id;
    private String createdAt;
    private String desc;
    @NotNull
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.createdAt);
        dest.writeString(this.desc);
        dest.writeString(this.publishedAt);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
    }

    public boolean getUsed() {
        return this.used;
    }

    public Meizi() {
    }

    protected Meizi(Parcel in) {
        this._id = in.readString();
        this.createdAt = in.readString();
        this.desc = in.readString();
        this.publishedAt = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
    }

    @Generated(hash = 660574406)
    public Meizi(String _id, String createdAt, String desc, @NotNull String publishedAt,
            String source, String type, String url, boolean used, String who) {
        this._id = _id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    public static final Parcelable.Creator<Meizi> CREATOR = new Parcelable.Creator<Meizi>() {
        @Override
        public Meizi createFromParcel(Parcel source) {
            return new Meizi(source);
        }

        @Override
        public Meizi[] newArray(int size) {
            return new Meizi[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Meizi) {
            return _id.equals(((Meizi) obj)._id);
        }
        return super.equals(obj);
    }
}
