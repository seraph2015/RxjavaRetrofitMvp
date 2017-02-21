package org.seraph.mvprxjavaretrofit.fragment;

import android.os.Bundle;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainTwoFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainTwoFragmentView;

/**
 * 第二页
 * date：2017/2/21 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragment extends BaseFragment implements MainTwoFragmentView {


    @Override
    protected int getContextView() {
        return R.layout.fragment_two;
    }

    MainTwoFragmentPresenter mainTwoFragmentPresenter;

    @Override
    protected BasePresenter getPresenter() {
        mainTwoFragmentPresenter = new MainTwoFragmentPresenter();
        return mainTwoFragmentPresenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mainTwoFragmentPresenter.initData();
    }

}
