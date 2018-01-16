package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.TestFragmentOneBinding;
import org.seraph.mvprxjavaretrofit.di.component.MainActivityComponent;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainOneFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainOneFragmentPresenter;

import javax.inject.Inject;


/**
 * 主界面
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainOneFragment extends ABaseFragment<MainOneFragmentContract.Presenter> implements MainOneFragmentContract.View {


    TestFragmentOneBinding binding;

    @Override
    protected View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.test_fragment_one, container, false);
        return binding.getRoot();
    }

    @Inject
    MainOneFragmentPresenter mPresenter;

    @Override
    protected MainOneFragmentContract.Presenter getMVPPresenter() {
        return mPresenter;
    }


    @Override
    public void setupActivityComponent() {
        this.getComponent(MainActivityComponent.class).inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        binding.setPresenter(mPresenter);
    }

    public void setUserTextViewValue(CharSequence charSequence) {
        binding.tvDbUser.setText(charSequence);
    }



}
