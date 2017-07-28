package org.seraph.mvprxjavaretrofit.di.module;

import android.app.Activity;
import android.content.ContentResolver;
import android.support.v7.widget.GridLayoutManager;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;

import dagger.Module;
import dagger.Provides;

/**
 * 通用模块
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public class CommonModule {

    @Provides
    @ActivityScope
    GridLayoutManager provideGridLayoutManager(Activity activity) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 4);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        return gridLayoutManager;

    }

    @Provides
    @ActivityScope
    ContentResolver provideContentResolver(Activity activity) {
        return activity.getContentResolver();
    }

}
