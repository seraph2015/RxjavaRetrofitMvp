package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.login.RegisteredActivity;

import dagger.Binds;
import dagger.Module;

/**
 * 注册模型
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class RegisteredModule {

    @ActivityScoped
    @Binds
    abstract Context bindContext(RegisteredActivity activity);

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(RegisteredActivity activity);


}
