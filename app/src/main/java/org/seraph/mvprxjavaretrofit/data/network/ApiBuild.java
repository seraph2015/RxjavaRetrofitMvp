package org.seraph.mvprxjavaretrofit.data.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.data.network.http.HttpsRequestHelp;
import org.seraph.mvprxjavaretrofit.utlis.FileUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 构建请求api
 * 缓存文件夹使用应用专属缓存目录,参加{@link FileUtils#getCacheDirectory(Context, String)}方法
 * date：2017/4/6 20:01
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ApiBuild {


    private HttpsRequestHelp mHttpsRequestHelp;
   // private HttpCacheHelp mHttpCacheHelp;

    @Inject
    public ApiBuild(HttpsRequestHelp httpsRequestHelp) {
        this.mHttpsRequestHelp = httpsRequestHelp;
       // this.mHttpCacheHelp = httpCacheHelp;
    }


    private OkHttpClient.Builder builder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //builder.cache(mHttpCacheHelp.getCache());

        if (AppConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        //判断是否启用证书
        if (AppConfig.IS_ENABLED_CER) {
            try {
                builder.sslSocketFactory(mHttpsRequestHelp.getSSLSocketFactory(), mHttpsRequestHelp.getX509TrustManager());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        builder.connectTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return builder;
    }


    /**
     * 构建ApiInterface
     */
    public <T> T buildApiInterface(String apiBaseUrl, Class<T> service) {
        OkHttpClient.Builder builder = builder();
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(apiBaseUrl)
                .client(builder.build()).build()
                .create(service);
    }


}
