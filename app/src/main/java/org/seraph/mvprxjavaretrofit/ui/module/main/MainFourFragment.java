package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.main.DaggerMainActivityComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.test.DesignLayoutTestActivity;

import javax.inject.Inject;

import butterknife.OnClick;

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
        DaggerMainActivityComponent.builder().appComponent(AppApplication.getAppComponent()).activityModule(new ActivityModule(getActivity())).build().inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        mPresenter.setView(this);
        mPresenter.start();
    }


    @OnClick(value = R.id.btn_design_layout)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_design_layout:
                startActivity(new Intent(mContext,DesignLayoutTestActivity.class));
                break;
        }


    }
}
