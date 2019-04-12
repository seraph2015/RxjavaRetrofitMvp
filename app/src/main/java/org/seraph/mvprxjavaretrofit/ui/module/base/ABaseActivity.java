package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.ui.module.login.LoginActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;
import org.seraph.mvprxjavaretrofit.ui.views.CustomLoadingDialog;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * 所有的activity的父类继承，包含的一系列的常用操作
 * mvp结构设计
 *
 * @see #initContextView() 初始化对应的布局
 * @see #getMVPPresenter() 获取实现{@link IBaseContract.IBasePresenter}接口的实现类，也是mvp架构中的Presenter层
 * @see #initCreate(Bundle) 初始化之后的第一次调用相当于activity的{@link #onCreate(Bundle)}
 * 此类设计必须实现{@link IBaseContract.IBasePresenter}或者子类接口，以完成mvp架构中的View层
 * date：2017/2/15 09:09
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class ABaseActivity extends AppCompatActivity implements IABaseContract.IBaseView, HasSupportFragmentInjector {

    protected abstract void initContextView();

    protected abstract IABaseContract.ABasePresenter getMVPPresenter();

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Inject
    protected CustomLoadingDialog mLoadingDialog;

    private IABaseContract.ABasePresenter mPresenter;

    //自动解绑rxjava（在指定的生命周期）
    public <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event untilEvent) {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, untilEvent));
    }

    //自动解绑rxjava（在结束的时候）
    public <T> AutoDisposeConverter<T> bindLifecycle() {
        return bindLifecycle(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //一处声明，处处依赖注入
        AndroidInjection.inject(this);
        RxBus.get().register(this);
        //布局
        initContextView();
        //布局的配置
        initUIConfig();
        //逻辑层
        mPresenter = getMVPPresenter();
        initCreate(savedInstanceState);
    }


    //初始化界面配置
    private void initUIConfig() {
        //例如：如果有toolbar。则初始化部分公共设置，如果部分界面不需要，自己进行清除
        View view = findViewById(R.id.toolbar);
        if (view instanceof Toolbar) {
            Toolbar toolbar = ((Toolbar) view);
            setSupportActionBar(toolbar);
            if (this instanceof MainActivity) {
                return;
            }
            //符合条件的布局设置统一的返回按键和监听
            if (this instanceof LoginActivity) {
                toolbar.setNavigationIcon(R.drawable.common_icon_close);
            } else {
                toolbar.setNavigationIcon(R.drawable.common_title_arrow_white_left);
            }
            RxToolbar.navigationClicks(toolbar).as(bindLifecycle()).subscribe(o -> onBackPressed());
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
        RxBus.get().unregister(this);
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }


}
