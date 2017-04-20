package org.seraph.mvprxjavaretrofit.data.network.service;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * 12306接口
 * date：2017/4/20 14:36
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface Api12306Service{

    String BASE_URL_12306 = "https://kyfw.12306.cn/";

    @GET("otn")
    Flowable<String> do12306Url();

}
