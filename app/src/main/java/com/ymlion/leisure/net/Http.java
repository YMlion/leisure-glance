package com.ymlion.leisure.net;

import com.ymlion.leisure.data.DbHelper;
import com.ymlion.leisure.module.pic.model.Coser;
import com.ymlion.leisure.net.response.HttpException;
import com.ymlion.leisure.net.response.HttpResult;
import com.ymlion.leisure.ui.model.Meizi;
import com.ymlion.lib.utils.RxUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Http request class
 *
 * Created by ymlion on 16/6/19.
 */

public class Http {
    private static final String TAG = "Http";
    private static final String BASE_URL = "http://ymlion.com/";

    private static Http HTTP;

    private IRequest request;

    private Http() {
        OkHttpClient okHttpClient = initClient();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        request = retrofit.create(IRequest.class);
    }

    private OkHttpClient initClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return builder.connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(chain -> {
                    Request request = chain.request().newBuilder().build();
                    return chain.proceed(request);
                }).build();
    }

    /**
     * create a Http object
     *
     * @return
     */
    public static Http build() {
        if (HTTP == null) {
            HTTP = new Http();
        }
        return HTTP;
    }

    /**
     * 解析返回结果
     *
     * @param <T>
     * @return
     */
    private <T> Observable.Transformer<HttpResult<T>, T> handleGankResult() {
        return tObservable -> tObservable.flatMap(httpResult -> {
                if (httpResult.error) {
                    return Observable.error(new HttpException(HttpException.DATA_EXCEPTION, "获取数据出错"));
                }
                return Observable.just(httpResult.results);
            });
    }

    /**
     * 解析返回结果
     *
     * @param <T>
     * @return
     */
    private <T> Observable.Transformer<HttpResult<T>, T> handleYXResult() {
        return tObservable -> tObservable.flatMap(httpResult -> {
                if (!httpResult.RetSucceed) {
                    return Observable.error(new HttpException(HttpException.DATA_EXCEPTION, "获取数据出错"));
                }
                return Observable.just(httpResult.msg);
            });
    }

    /**
     * 异常处理
     *
     * @param <T>
     * @return
     */
    private <T> Observable.Transformer<T, T> handleError() {
        return tObservable -> tObservable.compose(RxUtil.applyScheduler())
                .onErrorResumeNext(throwable -> {
                    throwable.printStackTrace();
                    HttpException he;
                    if (throwable instanceof ConnectException) {
                        he = new HttpException(throwable, HttpException.NET_EXCEPTION, "网络异常，请检查网络");
                    } else if (throwable instanceof SocketTimeoutException) {
                        he = new HttpException(throwable, HttpException.NET_EXCEPTION, "网络异常，请检查网络");
                    } else if (throwable instanceof UnknownHostException) {
                        he = new HttpException(throwable, HttpException.NET_EXCEPTION, "网络异常，请检查网络");
                    } else if (throwable instanceof HttpException) {
                        he = (HttpException) throwable;
                    } else {
                        he = new HttpException(throwable, HttpException.OTHER_EXCEPTION);
                    }
                    return Observable.error(he);
                });
    }

    /**
     * get images from gank.io
     *
     * @param size
     * @param page
     * @return
     */
    public Observable<List<Meizi>> getMeizhis(int size, int page, boolean loadCache) {
        Observable<List<Meizi>> cache = DbHelper.get()
                .getMeizis()
                .compose(handleError());

        Observable<List<Meizi>> net = request.getMeizhis(size, page)
                .compose(this.handleGankResult())
                .doOnNext(meizis -> DbHelper.get().saveMeizis(meizis))
                .compose(this.handleError());

        if (loadCache) {
            return Observable.concat(cache, net);
        }
        return net;
    }

    /**
     * get cos from yx
     *
     * @param count
     * @param lastId
     * @return
     */
    public Observable<List<Coser>> getCosers(int count, int lastId, boolean loadCache) {
        Observable<List<Coser>> cache = DbHelper.get()
                .getCosers()
                .compose(handleError());

        Observable<List<Coser>> net = request.getCosers(count, lastId)
                .compose(handleYXResult())
                .doOnNext(cosers -> DbHelper.get().saveCosers(cosers))
                .compose(handleError());

        if (loadCache) {
            return Observable.concat(cache, net);
        }
        return net;
    }

}
