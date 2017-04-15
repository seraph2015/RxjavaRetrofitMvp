package org.seraph.mvprxjavaretrofit.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * 布局测试类模型
 * date：2017/4/12 11:56
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public class DesignLayoutModule {

    private final Activity mActivity;

    public DesignLayoutModule(Activity activity) {
        this.mActivity = activity;
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
    LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(mActivity);
    }


}
