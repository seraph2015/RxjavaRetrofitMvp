package org.seraph.mvprxjavaretrofit;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.crashreport.CrashReport;

import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.DaggerAppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.AppModule;

/**
 * app初始化
 * date：2017/2/14 18:03
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AppApplication extends Application{

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
       // initImageLoad();
        initDagger2();
        //工具初始化
        Utils.init(this);
        initToastLayout();
        //腾讯bug日志收集
        CrashReport.initCrashReport(this, "c475f0a560", false);
        //注册activity回调
        registerActivityLifecycleCallbacks(new AppActivityCallbacks());
    }

    /**
     * 初始化提弹出提示布局资源显示
     */
    private void initToastLayout() {
        ToastUtils.setBgColor(0xFE000000);
    }

    /**
     * 初始化Dagger2
     */
    private void initDagger2() {
        this.appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

//    /**
//     * 初始化图片加载
//     */
//    private void initImageLoad() {
//        ImageFactory.initPicasso(this);
//        ImageFactory.initFresco(this);
//    }

    /**
     * 获取AppComponent
     */
    public AppComponent getAppComponent() {
        return appComponent;
    }


}
