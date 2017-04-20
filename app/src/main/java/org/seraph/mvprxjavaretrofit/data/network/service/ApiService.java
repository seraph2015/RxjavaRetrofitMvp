package org.seraph.mvprxjavaretrofit.data.network.service;

import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 网络请求接口
 * date：2017/2/16 11:57
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface ApiService {

    String BASE_URL = "http://47.89.26.54:8080/yxtg-app/";


    @POST("member/login")
    Flowable<BaseDataResponse<UserBean>> login(@QueryMap Map<String, Object> map);

    @POST("member/login")
    Flowable<BaseDataResponse<UserBean>> login(@Query("cellphone") String cellphone, @Query("password") String password);



}
