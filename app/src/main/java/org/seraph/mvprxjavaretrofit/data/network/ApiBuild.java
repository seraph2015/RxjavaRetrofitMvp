package org.seraph.mvprxjavaretrofit.data.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.data.network.https.HTTPS;
import org.seraph.mvprxjavaretrofit.data.network.service.Api12306Service;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiBaiduService;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 构建请求api
 * date：2017/4/6 20:01
 * author：xiongj
 * mail：417753393@qq.com
 **/
class ApiBuild {

    private X509TrustManager x509TrustManager;
    private SSLSocketFactory sslSocketFactory;

    private ApiBuild(Context context) {
        if (AppConfig.IS_ENABLED_CER) {
            try {
                InputStream inputStream = context.getAssets().open(AppConfig.HTTPS_CER_NAME);
                x509TrustManager = HTTPS.getX509TrustManager(inputStream);
                sslSocketFactory = HTTPS.getSSLSocketFactory(x509TrustManager);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public static ApiBuild build(Context context) {
        return new ApiBuild(context);
    }

    private OkHttpClient.Builder builder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (AppConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        builder.connectTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return builder;
    }


    /**
     * 构建ApiInterface
     */
    private <T> T buildApiInterface(String apiBaseUrl, Class<T> service) {
        OkHttpClient.Builder builder = builder();
        //判断是否启用证书
        if (AppConfig.IS_ENABLED_CER && sslSocketFactory != null && x509TrustManager != null) {
            builder.sslSocketFactory(sslSocketFactory, x509TrustManager);
        }
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(apiBaseUrl)
                .client(builder.build()).build()
                .create(service);
    }


    ApiService apiService() {
        return buildApiInterface(ApiService.BASE_URL, ApiService.class);
    }

    ApiBaiduService apiBaiduService() {
        return buildApiInterface(ApiBaiduService.BASE_URL_BAIDU, ApiBaiduService.class);
    }

    Api12306Service api12306Service() {
        return buildApiInterface(Api12306Service.BASE_URL_12306, Api12306Service.class);
    }

}
