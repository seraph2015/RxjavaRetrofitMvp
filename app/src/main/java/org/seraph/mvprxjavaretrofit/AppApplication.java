package org.seraph.mvprxjavaretrofit;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.data.network.picasso.PicassoFactory;
import org.seraph.mvprxjavaretrofit.di.component.AppComponent;
import org.seraph.mvprxjavaretrofit.di.component.DaggerAppComponent;
import org.seraph.mvprxjavaretrofit.di.module.AppModule;

/**
 * app初始化
 * date：2017/2/14 18:03
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AppApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initPicasso();
        initDagger2();
        Utils.init(this);
    }

    private void initDagger2() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    private void initPicasso() {
        PicassoFactory.initPicassoToOkHttp(this);
        Picasso.with(this).setIndicatorsEnabled(AppConfig.DEBUG);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
