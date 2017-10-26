package org.seraph.mvprxjavaretrofit.ui.module.login.presenter;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.SetPasswordActivityContract;

import javax.inject.Inject;

/**
 * 设置密码逻辑
 * date：2017/10/25 10:43
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class SetPasswordActivityPresenter implements SetPasswordActivityContract.Presenter{

    private SetPasswordActivityContract.View view;

    @Override
    public void setView(SetPasswordActivityContract.View view) {
        this.view = view;
    }

    private ApiService apiService;

    @Inject
    public SetPasswordActivityPresenter(ApiService apiService) {
       this.apiService = apiService;
    }

    @Override
    public void start() {

    }


    @Override
    public void onSetPassword(String password) {
        //保存密码
        ToastUtils.showShortToast("设置密码成功");
        view.finish();
    }
}
