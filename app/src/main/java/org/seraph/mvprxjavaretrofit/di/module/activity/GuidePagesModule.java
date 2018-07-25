package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.GuidePagesActivity;

import dagger.Binds;
import dagger.Module;

/**
 * 引导页模型
 * date：2017/4/6 09:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class GuidePagesModule {

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(GuidePagesActivity activity);

}
