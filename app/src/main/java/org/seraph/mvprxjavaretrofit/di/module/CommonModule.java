package org.seraph.mvprxjavaretrofit.di.module;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * 通用模块
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public class CommonModule {

    private final Activity mActivity;

    public CommonModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    GridLayoutManager provideGridLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        return gridLayoutManager;

    }

    @Provides
    @ActivityScope
    ContentResolver provideContentResolver() {
        return mActivity.getContentResolver();
    }

}
