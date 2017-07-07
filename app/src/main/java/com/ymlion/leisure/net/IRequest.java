package com.ymlion.leisure.net;

import com.ymlion.leisure.data.model.Coser;
import com.ymlion.leisure.net.response.HttpResult;
import com.ymlion.leisure.data.model.Meizi;

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

    @GET("http://gank.io/api/data/福利/{size}/{page}")
    Observable<HttpResult<List<Meizi>>> getMeizhis(@Path("size") int size, @Path("page") int page);

    @GET("http://youxin.357.com/v1/welfare/client/photos")
    Observable<HttpResult<List<Coser>>> getCosers(@Query("count") int count, @Query("lastId") int lastId);
}
