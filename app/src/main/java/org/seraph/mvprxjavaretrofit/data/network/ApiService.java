package org.seraph.mvprxjavaretrofit.data.network;

import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 网络请求接口
 * date：2017/2/16 11:57
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface ApiService {

    String BASE_URL = "http://47.89.26.54:8080/yxtg-app/";
    String BASE_URL_BAIDU = "http://image.baidu.com/";
    String BASE_URL_12306 = "https://kyfw.12306.cn/";


    @POST("member/login")
    Flowable<BaseDataResponse<UserBean>> login(@QueryMap Map<String, Object> map);

    @POST("member/login")
    Flowable<BaseDataResponse<UserBean>> login(@Query("cellphone") String cellphone, @Query("password") String password);

    /**
     * 获取百度图片
     */
    @GET
    Flowable<ImageBaiduBean> doBaiduImageUrl(@Url String url);

    @GET("otn")
    Flowable<String> do12306Url();
}
