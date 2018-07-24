package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.GuidePagesActivity;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.WelcomeActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

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
    abstract Context bindContext(GuidePagesActivity activity);

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(GuidePagesActivity activity);

}
