package org.seraph.mvprxjavaretrofit.request;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请求过程进行封装
 * date：2017/2/16 11:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
class HttpMethods {

    /**
     * 30秒超时
     */
    private static final int DEFAULT_TIMEOUT = 30;

    private ApiInterface apiInterface;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //添加请求OkHttp请求日志查看
        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiInterface.BASE_URL)
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    static ApiInterface getApiInterface() {
        return SingletonHolder.INSTANCE.apiInterface;
    }


}
