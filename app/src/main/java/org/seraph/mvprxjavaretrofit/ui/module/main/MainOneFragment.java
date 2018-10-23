package org.seraph.mvprxjavaretrofit.ui.module.main;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActMainFrg1Binding;
import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
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
@ActivityScoped
public class MainOneFragment extends ABaseFragment<MainOneFragmentContract.Presenter> implements MainOneFragmentContract.View {


    private ActMainFrg1Binding binding;


    @Inject
    public MainOneFragment(){}

    @Override
    protected View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.act_main_frg1, container, false);
        return binding.getRoot();
    }

    @Inject
    MainOneFragmentPresenter presenter;

    @Override
    protected MainOneFragmentContract.Presenter getMVPPresenter() {
        return presenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
       binding.setPresenter(presenter);
    }

    public void setUserTextViewValue(CharSequence charSequence) {
        binding.tvDbUser.setText(charSequence);
    }



}
