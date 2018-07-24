package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.LoginActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * 登录模型
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class LoginModule {

    @ActivityScoped
    @Binds
    abstract Context bindContext(LoginActivity activity);

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(LoginActivity activity);


}
