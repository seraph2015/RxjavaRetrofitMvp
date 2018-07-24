package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.login.LoginActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.ResetPasswordActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * 重置密码
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class ResetPasswordModule {

    @ActivityScoped
    @Binds
    abstract Context bindContext(ResetPasswordActivity activity);

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(ResetPasswordActivity activity);


}
