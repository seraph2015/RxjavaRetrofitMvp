package org.seraph.mvprxjavaretrofit.data.network.service;

import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 网络请求接口
 * date：2017/2/16 11:57
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface ApiService {

    //String BASE_URL = "http://106.15.103.137:80/zycartrade-app/";

    String BASE_URL = "http://120.27.227.156:8080/index.php/Api/";

    @POST("User/login")
    Flowable<BaseDataResponse<UserBean>> login(@QueryMap Map<String, Object> map);

    @POST("User/login")
    @FormUrlEncoded
    Flowable<BaseDataResponse<UserBean>> login(@Field("mobile") String mobile, @Field("password") String password);



}
