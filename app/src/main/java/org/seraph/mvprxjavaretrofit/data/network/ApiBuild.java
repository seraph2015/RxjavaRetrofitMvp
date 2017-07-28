package org.seraph.mvprxjavaretrofit.data.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.data.network.https.HTTPS;
import org.seraph.mvprxjavaretrofit.utlis.FileUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

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


    private AppApplication mApplication;

    @Inject
    public ApiBuild(AppApplication application) {
        this.mApplication = application;
    }


    private OkHttpClient.Builder builder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
      //  builder.cache(new Cache(FileUtils.getCacheDirectory(mApplication.getApplicationContext(), null), AppConfig.CACHE_MAX_SIZE));
        if (AppConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(logging);
        }
        builder.connectTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return builder;
    }


    /**
     * 构建ApiInterface
     */
    public <T> T buildApiInterface(String apiBaseUrl, Class<T> service) {
        OkHttpClient.Builder builder = builder();
        //判断是否启用证书
        if (AppConfig.IS_ENABLED_CER) {
            try {
                InputStream inputStream = mApplication.getAssets().open(AppConfig.HTTPS_CER_NAME);
                X509TrustManager x509TrustManager = HTTPS.getX509TrustManager(inputStream);
                SSLSocketFactory sslSocketFactory = HTTPS.getSSLSocketFactory(x509TrustManager);
                builder.sslSocketFactory(sslSocketFactory, x509TrustManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(apiBaseUrl)
                .client(builder.build()).build()
                .create(service);
    }


}
