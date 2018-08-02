package com.ymlion.leisure.net.image;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author ymlion
 * @date 2016/7/8
 */

@GlideModule
public class GankGlideModule extends AppGlideModule {
    @Override public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        final int cacheSize250M = 262144000;// glide默认磁盘缓存大小为250m
        builder.setDiskCache(
                new ExternalPreferredCacheDiskCacheFactory(context, "pic_cache", cacheSize250M));
        // 默认内存缓存为两个屏幕像素字节大小w*h*4*2

        // 设置默认的请求选项
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override public void registerComponents(@NonNull Context context, @NonNull Glide glide,
            @NonNull Registry registry) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
}
