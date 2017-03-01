package org.seraph.mvprxjavaretrofit.request;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.seraph.mvprxjavaretrofit.App;
import org.seraph.mvprxjavaretrofit.preference.AppConstant;
import org.seraph.mvprxjavaretrofit.request.https.HTTPS;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
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

    private static ApiInterface apiInterface;

    private static String baseUrl = ApiInterface.BASE_URL;

    private static OkHttpClient.Builder httpClientBuilder =
            new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(AppConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

    /**
     * 更新baseUrl
     */
    private static void changeApiBaseUrl(String newApiBaseUrl) {
        if (apiInterface == null) {
            if (Tools.isNull(newApiBaseUrl)) {
                baseUrl = ApiInterface.BASE_URL;
            } else {
                baseUrl = newApiBaseUrl;
            }
            apiInterface = buildRetrofitToApiInterface(baseUrl);
        } else {
            if (Tools.isNull(newApiBaseUrl)) {
                //没有新的url
                if (!baseUrl.equals(ApiInterface.BASE_URL)) {
                    //如果在没有传递baseUrl的情况下，如果之前的有改变则还原成默认的baseUrl
                    baseUrl = ApiInterface.BASE_URL;
                    apiInterface = buildRetrofitToApiInterface(baseUrl);
                }
            } else {
                //有新的url
                if (!baseUrl.equals(newApiBaseUrl)) {
                    baseUrl = newApiBaseUrl;
                    //如果新的url和之前的不一样，则重新构建
                    apiInterface = buildRetrofitToApiInterface(baseUrl);
                }
            }
        }

    }

    /**
     * 构建ApiInterface
     */

    private static ApiInterface buildRetrofitToApiInterface(String apiBaseUrl) {

        final ApiInterface[] apiInterface = new ApiInterface[1];

        Observable.just(AppConstant.IS_ENABLED_CER).map(aBoolean -> {
            if (aBoolean) {
                return AppConstant.HTTPS_CER_NAME;
            }
            return "";
        }).map(s -> {
            if (!Tools.isNull(s)) {
                InputStream inputStream = App.getSingleton().getAssets().open(AppConstant.HTTPS_CER_NAME);
                X509TrustManager x509TrustManager = HTTPS.getX509TrustManager(inputStream);
                SSLSocketFactory sslSocketFactory = HTTPS.getSSLSocketFactory(x509TrustManager);
                return httpClientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager).build();
            }
            return httpClientBuilder.build();
        }).subscribe(okHttpClient -> {
            apiInterface[0] = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(apiBaseUrl).client(okHttpClient).build().create(ApiInterface.class);
        });

        return apiInterface[0];

    }


    //获取单例
    static ApiInterface getApiInterface(String newApiBaseUrl) {
        changeApiBaseUrl(newApiBaseUrl);
        return apiInterface;
    }

    //获取单例
    static ApiInterface getApiInterface() {
        return getApiInterface(null);
    }

}
