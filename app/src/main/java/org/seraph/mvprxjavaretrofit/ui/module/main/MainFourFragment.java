package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.main.DaggerMainActivityComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseFragment;

import javax.inject.Inject;

/**
 * 4
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainFourFragment extends BaseFragment implements MainFourFragmentContract.View {


    @Override
    public int getContentView() {
        return R.layout.fragment_four;
    }

    @Inject
    MainFourFragmentPresenter mPresenter;

    @Override
    public void setupActivityComponent() {
        DaggerMainActivityComponent.builder().appComponent(AppApplication.getAppComponent()).activityModule(new ActivityModule(getContext())).build().inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        mPresenter.setView(this);
        mPresenter.start();
    }
}
