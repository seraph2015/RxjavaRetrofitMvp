package org.seraph.mvprxjavaretrofit.di.module;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;

import dagger.Module;
import dagger.Provides;

/**
 * 常用组件模块
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public class MainModule {

    @Provides
    @ActivityScope
    FragmentManager provideFragmentManager(Activity activity) {
        return ((FragmentActivity) activity).getSupportFragmentManager();

    }


}
