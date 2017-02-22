package org.seraph.mvprxjavaretrofit.request;

import org.seraph.mvprxjavaretrofit.mvp.model.BaiduImageBean;
import org.seraph.mvprxjavaretrofit.mvp.model.BaseResponse;
import org.seraph.mvprxjavaretrofit.mvp.model.UserBean;

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
interface ApiInterface {

    String BASE_URL = "http://47.89.26.54:8080/yxtg-app/";

    @POST("member/login")
    Flowable<BaseResponse<UserBean>> login(@QueryMap Map<String, Object> map);

    @POST("member/login")
    Flowable<BaseResponse<UserBean>> login(@Query("cellphone") String cellphone, @Query("password") String password);

    /**
     * 获取百度图片
     */
    @GET
    Flowable<BaiduImageBean> doBaiduImageUrl(@Url String url);
}
