package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Activity公共的模型
 * date：2017/4/6 09:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public abstract class ActivityModule {


    @ActivityScoped
    @Provides
    static RxPermissions providesRxPermissions(Activity activity) {
        return new RxPermissions(activity);
    }


}
