package com.ymlion.leisure.net;

import com.ymlion.leisure.net.response.HttpResult;
import com.ymlion.leisure.ui.model.Meizi;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * retrofit interface
 *
 * Created by ymlion on 16/6/30.
 */

public interface IRequest {

    @GET("data/福利/{size}/{page}")
    Observable<HttpResult<List<Meizi>>> getMeizhis(@Path("size") int size, @Path("page") int page);
}
