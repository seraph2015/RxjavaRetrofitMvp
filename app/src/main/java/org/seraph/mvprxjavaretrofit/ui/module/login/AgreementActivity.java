package org.seraph.mvprxjavaretrofit.ui.module.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActLoginAgreementBinding;
import org.seraph.mvprxjavaretrofit.di.component.DaggerLoginComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.AgreementContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.AgreementPresenter;

import javax.inject.Inject;

/**
 * 注册协议界面
 * date：2017/10/25 14:16
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class AgreementActivity extends ABaseActivity<AgreementContract.Presenter> implements AgreementContract.View {


    ActLoginAgreementBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.act_login_agreement);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerLoginComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    AgreementPresenter mPresenter;

    @Override
    protected AgreementContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        binding.appbar.tvToolbarTitle.setText("注册协议");
    }

}
