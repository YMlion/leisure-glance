package com.ymlion.leisure.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by YMlion on 2017/7/10.
 */
@Entity
public class YVideo implements Parcelable {

    /**
     * mid : 27
     * id : 1160170
     * title : 也许他不是个好新郎 但他一定是个好队友
     * name : 也许他不是个好新郎 但他一定是个好队友
     * time : 1499668362428
     * type : 3
     * top : 0
     * url : https://youxin.7531.com/v2/video/1160170.html
     * youxinUrl : https://youxin.7531.com/v2/video/1160170.html
     * playLink : https://youxin.7531.com/v2/video/play/1160170
     * img : http://img.357.com/wjgame/up/lo/upload_2528bc21d920b0b2c5276e65d4007546.jpg
     * aVideo : 1
     * duration : 0′8″
     * clientTags : 生活娱乐
     * tags : [{"id":45341,"name":"生活娱乐"}]
     * orderKey : 1499668377545
     * imgStyle : 1
     * imgtype : 0
     * pictures : []
     * orderId : 0
     * content : 王者荣耀 也许他不是个好新郎 但他一定是个好队友
     * author : {"name":"佚名","avatar":""}
     * commentCount : 0
     * collectid : 0
     */

    private int mid;
    @Id
    private long id;
    private String title;
    private String name;
    private long time;
    private int type;
    private int top;
    private String url;
    private String youxinUrl;
    private String playLink;
    private String img;
    private int aVideo;
    private String duration;
    private String clientTags;
    private long orderKey;
    private String content;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mid);
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeLong(this.time);
        dest.writeInt(this.type);
        dest.writeInt(this.top);
        dest.writeString(this.url);
        dest.writeString(this.youxinUrl);
        dest.writeString(this.playLink);
        dest.writeString(this.img);
        dest.writeInt(this.aVideo);
        dest.writeString(this.duration);
        dest.writeString(this.clientTags);
        dest.writeLong(this.orderKey);
        dest.writeString(this.content);
    }

    public YVideo() {
    }

    protected YVideo(Parcel in) {
        this.mid = in.readInt();
        this.id = in.readLong();
        this.title = in.readString();
        this.name = in.readString();
        this.time = in.readLong();
        this.type = in.readInt();
        this.top = in.readInt();
        this.url = in.readString();
        this.youxinUrl = in.readString();
        this.playLink = in.readString();
        this.img = in.readString();
        this.aVideo = in.readInt();
        this.duration = in.readString();
        this.clientTags = in.readString();
        this.orderKey = in.readLong();
        this.content = in.readString();
    }

    @Generated(hash = 1102997115)
    public YVideo(int mid, long id, String title, String name, long time, int type, int top, String url,
            String youxinUrl, String playLink, String img, int aVideo, String duration,
            String clientTags, long orderKey, String content) {
        this.mid = mid;
        this.id = id;
        this.title = title;
        this.name = name;
        this.time = time;
        this.type = type;
        this.top = top;
        this.url = url;
        this.youxinUrl = youxinUrl;
        this.playLink = playLink;
        this.img = img;
        this.aVideo = aVideo;
        this.duration = duration;
        this.clientTags = clientTags;
        this.orderKey = orderKey;
        this.content = content;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYouxinUrl() {
        return youxinUrl;
    }

    public void setYouxinUrl(String youxinUrl) {
        this.youxinUrl = youxinUrl;
    }

    public String getPlayLink() {
        return playLink;
    }

    public void setPlayLink(String playLink) {
        this.playLink = playLink;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getaVideo() {
        return aVideo;
    }

    public void setaVideo(int aVideo) {
        this.aVideo = aVideo;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getClientTags() {
        return clientTags;
    }

    public void setClientTags(String clientTags) {
        this.clientTags = clientTags;
    }

    public long getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(long orderKey) {
        this.orderKey = orderKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAVideo() {
        return this.aVideo;
    }

    public void setAVideo(int aVideo) {
        this.aVideo = aVideo;
    }

    public static final Parcelable.Creator<YVideo> CREATOR = new Parcelable.Creator<YVideo>() {
        @Override
        public YVideo createFromParcel(Parcel source) {
            return new YVideo(source);
        }

        @Override
        public YVideo[] newArray(int size) {
            return new YVideo[size];
        }
    };
}
