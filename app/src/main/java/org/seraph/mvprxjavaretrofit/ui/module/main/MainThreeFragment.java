package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.main.DaggerMainActivityComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 第三页
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragment extends BaseFragment implements MainThreeFragmentContract.View{

    @BindView(R.id.tv_https_value)
    TextView tvHttpsValue;

    @Inject
    MainThreeFragmentPresenter mPresenter;

    @Override
    public int getContentView() {
        return R.layout.fragment_three;
    }

    @Override
    public void setupActivityComponent() {
        DaggerMainActivityComponent.builder().appComponent(AppApplication.getAppComponent()).activityModule(new ActivityModule(getActivity())).build().inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        mPresenter.setView(this);
    }


    public void setTextView(CharSequence charSequence) {
        tvHttpsValue.setText(charSequence);
    }


    @OnClick(R.id.btn_https)
    public void onViewClicked() {
        mPresenter.post12306Https();
    }

}
