package org.seraph.mvprxjavaretrofit.fragment;

import android.os.Bundle;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainThreeFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainThreeFragmentView;
import org.seraph.mvprxjavaretrofit.views.ObservableScrollView;

/**
 * 主界面
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragment extends BaseFragment implements MainThreeFragmentView {

    private TextView tvContent;
    private TextView tvDbUser;
    private ObservableScrollView oScrollView;


    @Override
    protected int getContextView() {
        return R.layout.fragment_three;
    }


    MainThreeFragmentPresenter mPresenter;

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new MainThreeFragmentPresenter();
        return mPresenter;
    }



    @Override
    protected void init(Bundle savedInstanceState) {
        mPresenter.initData();
    }

}
