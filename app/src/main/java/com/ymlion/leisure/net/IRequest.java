package com.ymlion.leisure.net;

import android.util.ArrayMap;
import com.ymlion.leisure.data.model.Coser;
import com.ymlion.leisure.data.model.CoserSet;
import com.ymlion.leisure.data.model.GankModel;
import com.ymlion.leisure.data.model.YVideo;
import com.ymlion.leisure.net.response.HttpResult;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * retrofit interface
 *
 * Created by ymlion on 16/6/30.
 */

public interface IRequest {

    @GET("http://gank.io/api/data/休息视频/{size}/{page}")
    Observable<HttpResult<List<GankModel>>> getRestVideos(@Path("size") int size, @Path("page") int page);

    @GET("http://gank.io/api/data/福利/{size}/{page}")
    Observable<HttpResult<List<GankModel>>> getMeizhis(@Path("size") int size, @Path("page") int page);

    @GET("http://youxin.357.com/v1/welfare/client/photos")
    Observable<HttpResult<List<Coser>>> getCosers(@Query("count") int count, @Query("lastId") long lastId);

    @GET("http://youxin.357.com/v1/welfare/photo")
    Observable<HttpResult<CoserSet>> getCoserPhoto(@Query("id") long id);

    @GET("http://youxin.357.com//v2/youxin/videos-v3")
    Observable<HttpResult<ArrayMap<String, List<List<YVideo>>>>> getYVideos(
            @Query("count") int count, @Query("hasTag") int hasTag,
                                                             @Query("orderKey") long orderKey, @Query("gId") int gId,
                                                             @Query("hasCarousel") int hasCarousel);
}
