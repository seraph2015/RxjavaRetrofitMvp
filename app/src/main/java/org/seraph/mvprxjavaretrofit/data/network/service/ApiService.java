package org.seraph.mvprxjavaretrofit.data.network.service;

import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 网络请求接口
 * date：2017/2/16 11:57
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface ApiService {

    String BASE_URL = "http://thrust.yx.xubei.com/";

    @POST("auth/login")
    @FormUrlEncoded
    Flowable<BaseDataResponse<UserBean>> login(@Field("phone") String phone, @Field("password") String password);

    /**
     * 通用多文件和字段上传（使用RequestBody方式）
     *
     * @param url         上传地址
     * @param requestBody 请求体，生成对应上传requestBody参见
     *                    {@link org.seraph.mvprxjavaretrofit.data.network.FileUploadHelp#multipartRequestBody(Map, List, String)}
     *                    {@link org.seraph.mvprxjavaretrofit.data.network.FileUploadHelp#multipartRequestBody(Map, Map)}
     */
    @POST()
    Flowable<BaseDataResponse> multipart(@Url() String url, @Body RequestBody requestBody);
}
