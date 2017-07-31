package org.seraph.mvprxjavaretrofit;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.utlis.FontUtils;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * App中每个Activity的回调,处理部分公共的问题
 * 在{@link AppApplication#onCreate()}中注册。
 * 注意: ActivityLifecycleCallbacks 中所有方法的调用时机都是在 Activity 对应生命周期的 Super 方法中进行的。
 * date：2017/7/31 11:51
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AppActivityCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //管理activity
        AppActivityManage.getInstance().addActivity(activity);
        //设置字体，需要在设置布局之后
        FontUtils.injectFont(activity.findViewById(android.R.id.content));
        //ButterKnife控件绑定
        activity.getIntent().putExtra("ActivityBean", new ActivityBean(ButterKnife.bind(activity)));
        //初始化公共依赖注入
        if (activity instanceof ABaseActivity) {
            ((ABaseActivity) activity).setupActivityComponent(getAppComponent(activity), getActivityModule(activity));
        }
        //todo 可以进行其他公共设置（例如布局，切换动画。）...
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //解除ButterKnife控件绑定
        ((ActivityBean) activity.getIntent().getSerializableExtra("ActivityBean")).getUnbinder().unbind();
        //移除关闭activity
        AppActivityManage.getInstance().closeActivity(activity);
    }

    private class ActivityBean implements Serializable {

        private Unbinder unbinder;

        private ActivityBean(Unbinder unbinder) {
            this.unbinder = unbinder;
        }

        public Unbinder getUnbinder() {
            return unbinder;
        }

    }

    /**
     * 获取公用的AppComponent
     */
    private AppComponent getAppComponent(Activity activity) {
        return ((AppApplication) activity.getApplication()).getAppComponent();
    }

    /**
     * 获取公用的ActivityModule
     */
    private ActivityModule getActivityModule(Activity activity) {
        return new ActivityModule(activity);
    }


}
