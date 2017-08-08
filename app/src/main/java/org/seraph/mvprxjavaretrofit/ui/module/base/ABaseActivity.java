package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.seraph.mvprxjavaretrofit.data.network.rx.RxDisposableHelp;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.views.CustomLoadingDialog;

import javax.inject.Inject;

/**
 * 所有的activity的父类继承，包含的一系列的常用操作
 * mvp结构设计
 *
 * @see #getContextView 获取对应的加载的布局view
 * @see #getMVPPresenter() 获取实现{@link IBaseContract.IBaseActivityPresenter<V>}接口的实现类，也是mvp架构中的Presenter层
 * @see #setupActivityComponent(AppComponent, ActivityModule) () 进行dagger2的依赖注入绑定
 * @see #initCreate(Bundle) 初始化之后的第一次调用相当于activity的{@link #onCreate(Bundle)}
 * 此类设计必须实现{@link IBaseContract.IBaseActivityPresenter}或者子类接口，以完成mvp架构中的View层
 * date：2017/2/15 09:09
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class ABaseActivity<V extends IBaseContract.IBaseActivityView, P extends IBaseContract.IBaseActivityPresenter<V>> extends RxAppCompatActivity implements IBaseContract.IBaseActivityView {

    @Inject
    protected CustomLoadingDialog mLoadingDialog;


    public abstract int getContextView();

    protected abstract P getMVPPresenter();

    public abstract void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule);

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    /**
     * ActivityLifecycleCallbacks回调在super中，
     * 所以加载布局需要super之前{@link org.seraph.mvprxjavaretrofit.AppActivityCallbacks#onActivityCreated(Activity, Bundle)}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(getContextView());
        super.onCreate(savedInstanceState);
        initMVP();
        initCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    private void initMVP() {
        try {
            getMVPPresenter().setView((V) this);
        } catch (ClassCastException e) {
            throw new RuntimeException("子类必须实现IBaseContract.IBaseView接口");
        }
    }




    @Override
    public void showLoading(String str) {
        mLoadingDialog.setDialogMessage(str);
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                RxDisposableHelp.dispose();
                mLoadingDialog.setOnDismissListener(null);
            }
        });
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }



}
