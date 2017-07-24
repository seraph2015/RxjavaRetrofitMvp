package org.seraph.mvprxjavaretrofit.di.module;

import android.app.Activity;
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
@Module(includes = ActivityModule.class)
public class DesignLayoutModule {

    @Provides
    @ActivityScope
    LinearLayoutManager provideLinearLayoutManager(Activity mActivity){
        return new LinearLayoutManager(mActivity);
    }

}
