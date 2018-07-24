package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.seraph.mvprxjavaretrofit.ui.views.CustomLoadingDialog;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 所有的activity的父类继承，包含的一系列的常用操作
 * mvp结构设计
 *
 * @see #initContextView() 初始化对应的布局
 * @see #getMVPPresenter() 获取实现{@link IBaseContract.IBaseActivityPresenter}接口的实现类，也是mvp架构中的Presenter层
 * @see #initCreate(Bundle) 初始化之后的第一次调用相当于activity的{@link #onCreate(Bundle)}
 * 此类设计必须实现{@link IBaseContract.IBaseActivityPresenter}或者子类接口，以完成mvp架构中的View层
 * date：2017/2/15 09:09
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class ABaseActivity<P extends IABaseContract.ABaseActivityPresenter> extends RxAppCompatActivity implements IABaseContract.IBaseActivityView, HasSupportFragmentInjector {

    protected abstract void initContextView();

    protected abstract P getMVPPresenter();

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Inject
    protected CustomLoadingDialog mLoadingDialog;

    protected P mPresenter;

    /**
     * ActivityLifecycleCallbacks回调在super中，
     * 所以加载布局需要super之前{@link org.seraph.mvprxjavaretrofit.AppActivityCallbacks#onActivityCreated(Activity, Bundle)}
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initContextView();
        super.onCreate(savedInstanceState);
        initMVP();
        initCreate(savedInstanceState);
    }


    @SuppressWarnings("unchecked")
    private void initMVP() {
        try {
            if (getMVPPresenter() == null) {
                return;
            }
            mPresenter = getMVPPresenter();
            mPresenter.setView(this);
        } catch (ClassCastException e) {
            throw new RuntimeException("子类必须实现IABaseContract.IBaseActivityView接口");
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public CustomLoadingDialog showLoading() {
        return showLoading("");
    }

    @Override
    public CustomLoadingDialog showLoading(String str) {
        mLoadingDialog.setDialogMessage(str);
        return mLoadingDialog;
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector(){
        return supportFragmentInjector;
    }

}
