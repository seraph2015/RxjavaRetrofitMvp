package org.seraph.mvprxjavaretrofit.fragment;

import android.os.Bundle;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.activity.MainActivity;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainFourFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainFourFragmentView;

/**
 * 4
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainFourFragment extends BaseFragment implements MainFourFragmentView {


    @Override
    protected int getContextView() {
        return R.layout.fragment_four;
    }

    private MainActivity mMainActivity;

    MainFourFragmentPresenter mPresenter;

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new MainFourFragmentPresenter();
        return mPresenter;
    }



    @Override
    protected void init(Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        mPresenter.initData();
    }


    @Override
    public void setTitleAndLogo(String title, int logoIcon) {
        if (mMainActivity != null) {
            mMainActivity.setTitle(title);
            mMainActivity.setTooBarLogo(logoIcon);
        }
    }

    @Override
    public void upDataToolbarAlpha(float percentScroll) {
        mMainActivity.mPresenter.upDataToolbarAlpha(percentScroll);
    }
}
