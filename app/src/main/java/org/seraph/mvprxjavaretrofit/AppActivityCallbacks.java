package org.seraph.mvprxjavaretrofit;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.LoginActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;
import org.seraph.mvprxjavaretrofit.utlis.FontUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        //管理activity
        AppActivityManage.getInstance().addActivity(activity);
        //设置字体，需要在设置布局之后
        FontUtils.injectFont(activity.findViewById(android.R.id.content));
        //注册事件总线
        RxBus.get().register(activity);
        //初始化公共依赖注入
        if (activity instanceof ABaseActivity) {
            ((ABaseActivity) activity).setupActivityComponent(getAppComponent(activity), getActivityModule(activity));
        }
        //todo 可以进行其他公共设置（例如布局，切换动画。）...
        //例如：如果有toolbar。则初始化部分公共设置，如果部分界面不需要，自己进行清除
        View view = activity.findViewById(R.id.toolbar);
        if (view != null && view instanceof Toolbar && activity instanceof ABaseActivity) {
            Toolbar toolbar = ((Toolbar) view);
            ((ABaseActivity) activity).setSupportActionBar(toolbar);
            //主页特殊处理。不需要进行返回键的操作
            if (activity instanceof MainActivity) {
                return;
            }
            //符合条件的布局设置统一的返回按键和监听
            if (activity instanceof LoginActivity) {
                toolbar.setNavigationIcon(R.drawable.common_icon_close);
            } else {
                toolbar.setNavigationIcon(R.drawable.common_title_arrow_white_left);
            }
            RxToolbar.navigationClicks(toolbar).subscribe(new Consumer<Object>() {
                @Override
                public void accept(@NonNull Object o) throws Exception {
                    activity.onBackPressed();
                }
            });
        }
        //如果是进入登录界面，则使用从下叠加到页面上的动画
        if (activity instanceof LoginActivity) {
            activity.overridePendingTransition(R.anim.anim_slide_in_from_bottom, 0);
        }
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
        RxBus.get().unregister(activity);
        //移除关闭activity
        AppActivityManage.getInstance().closeActivity(activity);
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
