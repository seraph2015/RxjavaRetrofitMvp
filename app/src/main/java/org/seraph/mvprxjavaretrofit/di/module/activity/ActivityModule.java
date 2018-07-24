package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;

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
        return new RxPermissions((FragmentActivity) activity);
    }


}
