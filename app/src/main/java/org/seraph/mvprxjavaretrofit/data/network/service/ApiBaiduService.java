package org.seraph.mvprxjavaretrofit.data.network.service;

import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * 百度api
 * date：2017/4/20 14:33
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface ApiBaiduService {

    String BASE_URL_BAIDU = "http://image.baidu.com/";

    /**
     * 获取百度图片
     */
    @GET
    Flowable<ImageBaiduBean> doBaiduImageUrl(@Url String url);
}
