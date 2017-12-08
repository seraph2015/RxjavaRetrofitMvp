package org.seraph.mvprxjavaretrofit.ui.module.login.presenter;


import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.AgreementActivityContract;

import javax.inject.Inject;

/**
 * 注册协议逻辑
 * date：2017/10/25 10:43
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class AgreementActivityPresenter extends AgreementActivityContract.Presenter{


    private ApiService apiService;

    @Inject
    public AgreementActivityPresenter(ApiService apiService) {
       this.apiService = apiService;
    }

    @Override
    public void start() {

    }


}
