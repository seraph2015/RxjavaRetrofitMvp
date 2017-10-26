package org.seraph.mvprxjavaretrofit.ui.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerLoginComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.AgreementActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.AgreementActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 注册协议界面
 * date：2017/10/25 14:16
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class AgreementActivity extends ABaseActivity<AgreementActivityContract.View, AgreementActivityContract.Presenter> implements AgreementActivityContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    @Override
    public int getContextView() {
        return R.layout.act_login_agreement;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerLoginComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    AgreementActivityPresenter mPresenter;

    @Override
    protected AgreementActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        tvToolbarTitle.setText("注册协议");
    }

}
