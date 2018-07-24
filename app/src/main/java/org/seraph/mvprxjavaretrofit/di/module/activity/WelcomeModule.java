package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.WelcomeActivity;

import dagger.Binds;
import dagger.Module;

/**
 * 欢迎界面模型
 * date：2017/4/6 09:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class WelcomeModule {

    @ActivityScoped
    @Binds
    abstract Context bindContext(WelcomeActivity activity);

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(WelcomeActivity activity);

}
