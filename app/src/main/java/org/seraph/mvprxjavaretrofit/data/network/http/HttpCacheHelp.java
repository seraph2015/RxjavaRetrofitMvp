package org.seraph.mvprxjavaretrofit.data.network.http;

import com.blankj.utilcode.util.NetworkUtils;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.utlis.FileUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络缓存拦截器(未设计完成)
 * date：2017/7/28 16:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class HttpCacheHelp {

    private AppApplication mApplication;


    @Inject
    public HttpCacheHelp(AppApplication application) {
        this.mApplication = application;
    }

    /**
     * 获取缓存文件夹
     */
    public Cache getCache() {
        return new Cache(FileUtils.getCacheDirectory(mApplication.getApplicationContext(), null), AppConfig.CACHE_MAX_SIZE);
    }


    /**
     * 强制读缓存
     */
    public Interceptor getForceCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * 强制读网络数据，并缓存.
     * maxAge：限制了缓存的生存时间，超过了就会删除该请求的缓存。
     * StaleTime：指的是过时时间，设置它后，会将数据缓存，如果没有超过过时时间，说明数据还是新的，或直接拿缓存数据返回，如果超过是过时时间，那么我们认为数据过时了，它会去或者网络数据进行更新并返回，注意，它是不会删除缓存的。
     */
    public Interceptor getForceNetworkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                Response.Builder builder = chain.proceed(request).newBuilder().removeHeader("Cache-Control").removeHeader("Pragma");
                if (NetworkUtils.isConnected()) {
                    //有网络则设置网络超时为0
                    builder.header("Cache-Control", "public,max-age=" + 60 * 60);
                } else {
                    //没有网络，则设置网络超时为1周
                    builder.header("Cache-Control", "public, only-if-cached, max-stale=" + 7 * 24 * 60 * 60);
                }
                return builder.build();
            }
        };
    }

}
