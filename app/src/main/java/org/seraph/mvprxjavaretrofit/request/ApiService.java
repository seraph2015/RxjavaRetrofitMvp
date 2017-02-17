package org.seraph.mvprxjavaretrofit.request;

import org.seraph.mvprxjavaretrofit.mvp.model.BaseResponse;
import org.seraph.mvprxjavaretrofit.mvp.model.UserBean;

import io.reactivex.Flowable;

/**
 * 网络请求主体
 * date：2017/2/16 11:14
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ApiService {

    /**
     * 登录请求
     */
    public static Flowable<BaseResponse<UserBean>> doLogin(String... params) {
        //创建请求的类
        return RxServerData.getPublicDataProcessing(HttpMethods.getApiInterface().login(params[0], params[1]));
    }

}
