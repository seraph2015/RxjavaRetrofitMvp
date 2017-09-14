package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.local.AppSPConstant;
import org.seraph.mvprxjavaretrofit.data.local.AppSPManager;
import org.seraph.mvprxjavaretrofit.di.component.DaggerWelcomeComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;

import javax.inject.Inject;

/**
 * 欢迎页
 * date：2017/5/3 09:50
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class WelcomeActivity extends ABaseActivity<WelcomeActivityContract.View, WelcomeActivityContract.Presenter> implements WelcomeActivityContract.View {


    @Override
    public int getContextView() {
        return R.layout.welcome_activity;
    }


    @Inject
    WelcomeActivityPresenter mPresenter;


    @Override
    protected WelcomeActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Inject
    AppSPManager mSPManager;

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerWelcomeComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        mPresenter.start();
    }


    @Override
    public void jumpNextActivity() {
        //如果已经不是第一次则跳转主界面。如果是第一次，跳转引导页
        Intent intent;
        if ((Boolean) mSPManager.get(AppSPConstant.IS_FIRST, true)) {
            intent = new Intent(this, GuidePagesActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
