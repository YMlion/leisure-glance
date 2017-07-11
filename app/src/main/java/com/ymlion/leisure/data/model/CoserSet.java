package com.ymlion.leisure.data.model;

import java.util.List;

/**
 * Created by YMlion on 2017/7/11.
 */

public class CoserSet {

    /**
     * title : 张张都是海报！神奇女侠特效COS
     * cover : {"url":"https://img.7531.com/wjgame/up/lo/upload_7285b26426858af043bf9d5178a9caa4.jpg","width":585,"height":872}
     * pics : [{"url":"https://img.7531.com/wjgame/up/lo/upload_28b14c304e3829090837db6812706d93.jpg","intro":"张张都是海报！神奇女侠特效COS","width":549,"height":820,"type":0}]
     */

    private String title;
    private Coser cover;
    private List<Coser> pics;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Coser getCover() {
        return cover;
    }

    public void setCover(Coser cover) {
        this.cover = cover;
    }

    public List<Coser> getPics() {
        return pics;
    }

    public void setPics(List<Coser> pics) {
        this.pics = pics;
    }
}
