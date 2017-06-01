package org.seraph.mvprxjavaretrofit.data.network;

import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * 网络请求管理。（分配请求的baseurl和使用的解析规则）
 * date：2017/2/16 11:14
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ApiManager {

    private ApiBuild mApiBuild;

    @Inject
    ApiManager(ApiBuild apiBuild) {
        mApiBuild = apiBuild;
    }

    /**
     * 登录请求
     */
    public Flowable<BaseDataResponse<UserBean>> doLogin(String... params) {
        //创建请求的类
        return RxServerData.getPublicDataProcessing(mApiBuild.apiService().login(params[0], params[1]));
    }

    /**
     * 获取百度图片 http://image.baidu.com/
     */
    public Flowable<ImageBaiduBean> doBaiduImage(String... params) {
        return RxServerData.getDataProcessing(mApiBuild.apiBaiduService().doBaiduImageUrl(params[0]));
    }

    /**
     * 12306测试https证书   https://kyfw.12306.cn/
     */
    public Flowable<String> do12306() {
        return RxServerData.getDataProcessing(mApiBuild.api12306Service().do12306Url());
    }
}
