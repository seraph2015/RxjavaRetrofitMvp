package org.seraph.mvprxjavaretrofit.data.network;

import android.content.Context;

import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.data.network.http.HttpsRequestHelp;
import org.seraph.mvprxjavaretrofit.utlis.FileUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        mHttpsRequestHelp.setHttpsCerName(AppConfig.HTTPS_CER_NAME);
        // this.mHttpCacheHelp = httpCacheHelp;
    }


    private OkHttpClient.Builder builder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //builder.cache(mHttpCacheHelp.getCache());
        //log输出
        if (AppConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        //https证书
        try {
            SSLSocketFactory sslSocketFactory = mHttpsRequestHelp.getSSLSocketFactory();
            X509TrustManager x509TrustManager = mHttpsRequestHelp.getX509TrustManager();
            if (sslSocketFactory != null && x509TrustManager != null) {
                builder.sslSocketFactory(sslSocketFactory, x509TrustManager);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
