package org.seraph.mvprxjavaretrofit.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.seraph.mvprxjavaretrofit.activity.BaseActivity;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;

/**
 * frgment基类
 * date：2017/2/20 16:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BaseFragment extends Fragment implements BaseView {


    protected abstract int getContextView();

    protected abstract BasePresenter getPresenter();

    protected abstract void init(Bundle savedInstanceState);

    //声明基类中的Presenter
    public BasePresenter mPresenter;

    private BaseActivity baseActivity;

    protected View rootView;

    @Override
    public void onAttach(Context context) {
        initMVPBind();
        if (mPresenter != null) {
            mPresenter.onAttach(context);
        }
        if (context instanceof BaseActivity) {
            baseActivity = (BaseActivity) context;
        }
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getContextView(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(savedInstanceState);
    }

    /**
     * 绑定mvp关系
     */
    private void initMVPBind() {
        //获取Presenter持有
        mPresenter = getPresenter();
        //绑定mView
        mPresenter.attachView(this);
    }

    @Override
    public void showLoading() {
        if (baseActivity != null) {
            baseActivity.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (baseActivity != null) {
            baseActivity.hideLoading();
        }

    }

    @Override
    public void onStart() {
        if (mPresenter != null) {
            mPresenter.onStart();
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (mPresenter != null) {
            mPresenter.onResume();
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        if (mPresenter != null) {
            mPresenter.onStop();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        if (mPresenter != null) {
            mPresenter.onPause();
        }
        super.onPause();
    }


    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroyView();
        }
        super.onDestroyView();
    }

    /**
     * 恢复部分公共数据（解决一个activity中多个碎片共用toolbar问题）
     */
    public void restoreData() {
        if (mPresenter != null) {
            mPresenter.restoreData();
        }
    }

}
