package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.common.photolist.LocalImageListActivity;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.WelcomeActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * 查看本地图片
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class LocalImageListModule {

    @ActivityScoped
    @Binds
    abstract Context bindContext(LocalImageListActivity activity);

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(LocalImageListActivity activity);

    @ActivityScoped
    @Provides
    static GridLayoutManager provideGridLayoutManager(Activity activity) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 4);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        return gridLayoutManager;
    }

    @ActivityScoped
    @Provides
    static ContentResolver provideContentResolver(Activity activity) {
        return activity.getContentResolver();
    }

}
