package org.seraph.mvprxjavaretrofit.ui.module.login.presenter;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.local.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.RegisteredActivityContract;

import javax.inject.Inject;

/**
 * 注册逻辑
 * date：2017/10/25 10:43
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class RegisteredActivityPresenter extends RegisteredActivityContract.Presenter{


    private ApiService apiService;

    private UserBeanHelp userBeanHelp;

    @Inject
    public RegisteredActivityPresenter(ApiService apiService, UserBeanHelp userBeanHelp) {
       this.apiService = apiService;
       this.userBeanHelp = userBeanHelp;
    }

    @Override
    public void start() {

    }


    public void onRegistered() {
        ToastUtils.showShort("提交注册");
    }

    public void onGetCode(String phone) {
        //获取验证码
    }
}
