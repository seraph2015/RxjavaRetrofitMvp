package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.main.DaggerMainFragmentComponent;
import org.seraph.mvprxjavaretrofit.di.module.FragmentModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainThreeFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainThreeFragmentPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 第三页
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragment extends ABaseFragment<MainThreeFragmentContract.View,MainThreeFragmentContract.Presenter> implements MainThreeFragmentContract.View{

    @BindView(R.id.tv_https_value)
    TextView tvHttpsValue;

    @Inject
    MainThreeFragmentPresenter mPresenter;

    @Override
    public int getContextView() {
        return R.layout.test_fragment_three;
    }

    @Override
    protected MainThreeFragmentContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void setupActivityComponent() {
        DaggerMainFragmentComponent.builder().appComponent(AppApplication.getAppComponent()).fragmentModule(new FragmentModule(this)).build().inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {

    }


    public void setTextView(CharSequence charSequence) {
        tvHttpsValue.setText(charSequence);
    }


    @OnClick(R.id.btn_https)
    public void onViewClicked() {
        mPresenter.post12306Https();
    }

}
