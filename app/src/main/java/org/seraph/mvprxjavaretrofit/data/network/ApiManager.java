package org.seraph.mvprxjavaretrofit.data.network;

import android.content.Context;

import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import io.reactivex.Flowable;

/**
 * 网络请求管理。（分配请求的baseurl和使用的解析规则）
 * date：2017/2/16 11:14
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ApiManager {

    private ApiBuild apiBuild;

    private ApiManager(Context context) {
        apiBuild = ApiBuild.build(context);
    }

    public static ApiManager build(Context context) {
        return new ApiManager(context);
    }

    /**
     * 登录请求
     */
    public Flowable<BaseDataResponse<UserBean>> doLogin(String... params) {
        //创建请求的类
        return RxServerData.getPublicDataProcessing(apiBuild.apiService().login(params[0], params[1]));
    }

    /**
     * 获取百度图片 http://image.baidu.com/
     */
    public Flowable<ImageBaiduBean> doBaiduImage(String... params) {
        return RxServerData.getDataProcessing(apiBuild.apiBaiduService().doBaiduImageUrl(params[0]));
    }

    /**
     * 12306测试https证书   https://kyfw.12306.cn/
     */
    public Flowable<String> do12306() {
        return RxServerData.getDataProcessing(apiBuild.api12306Service().do12306Url());
    }
}
