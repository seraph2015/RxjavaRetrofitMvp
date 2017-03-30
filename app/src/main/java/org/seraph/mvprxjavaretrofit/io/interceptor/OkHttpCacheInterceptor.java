package org.seraph.mvprxjavaretrofit.io.interceptor;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.utli.Tools;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp的缓存拦截处理
 * date：2017/2/22 12:36
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class OkHttpCacheInterceptor implements Interceptor {
    /**
     * 请求成功后，缓存数据的时间分钟数
     */
    private int maxAge = 0;

    public OkHttpCacheInterceptor(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //判断网络是否可用
        if (!Tools.IsInternetValidate(AppApplication.getSingleton())) {
            //没有网络使用本地缓存
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge)
                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .build();

        return response;
    }
}
