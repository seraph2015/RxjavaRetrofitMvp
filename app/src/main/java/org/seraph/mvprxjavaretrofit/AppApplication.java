package org.seraph.mvprxjavaretrofit;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.Bugly;

import org.seraph.mvprxjavaretrofit.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * app初始化
 * date：2017/2/14 18:03
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AppApplication extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        // initImageLoad();
        //初始化dagger2
        DaggerAppComponent.builder().application(this).build().inject(this);
        //工具初始化
        Utils.init(this);
        initToastLayout();
        //腾讯bug日志收集
        Bugly.init(this, "c475f0a560", false);
        //注册activity回调
        registerActivityLifecycleCallbacks(new AppActivityCallbacks());
    }


    private void initToastLayout() {
        ToastUtils.setBgColor(0xFE000000);
        ToastUtils.setMsgColor(0xFFFFFFFF);
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }


}
