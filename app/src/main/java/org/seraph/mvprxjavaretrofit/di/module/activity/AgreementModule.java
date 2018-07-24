package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.login.AgreementActivity;

import dagger.Binds;
import dagger.Module;

/**
 * 协议查看模型
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class AgreementModule {

    @ActivityScoped
    @Binds
    abstract Context bindContext(AgreementActivity activity);

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(AgreementActivity activity);


}
