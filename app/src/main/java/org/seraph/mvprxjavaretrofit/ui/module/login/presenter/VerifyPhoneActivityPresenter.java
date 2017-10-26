package org.seraph.mvprxjavaretrofit.ui.module.login.presenter;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.local.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.VerifyPhoneActivityContract;

import javax.inject.Inject;

/**
 * 验证手机号逻辑
 * date：2017/10/25 10:43
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class VerifyPhoneActivityPresenter implements VerifyPhoneActivityContract.Presenter {

    private VerifyPhoneActivityContract.View view;

    @Override
    public void setView(VerifyPhoneActivityContract.View view) {
        this.view = view;
    }

    private ApiService apiService;


    @Inject
    public VerifyPhoneActivityPresenter(ApiService apiService, UserBeanHelp userBeanHelp) {
        this.apiService = apiService;
    }

    @Override
    public void start() {

    }


    @Override
    public void onGetCode(String phone) {
        //获取验证码
        ToastUtils.showShortToast("获取验证码");
    }
}
