package org.seraph.mvprxjavaretrofit;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.seraph.mvprxjavaretrofit.di.DaggerAppComponent;
import org.seraph.mvprxjavaretrofit.ui.views.LottieHeader;

import javax.inject.Inject;

import androidx.multidex.MultiDexApplication;
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

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new LottieHeader(context).setAnimationViewJson("trail_loading.json"));
    }

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // initImageLoad();
        //初始化dagger2
        DaggerAppComponent.builder().application(this).build().inject(this);
        //工具初始化
        Utils.init(this);
    }


}
