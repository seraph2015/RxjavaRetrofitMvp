package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.test.DesignLayoutTestActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * 布局测试类模型
 * date：2017/4/12 11:56
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class DesignLayoutModule {

    @Binds
    abstract Activity bindActivity(DesignLayoutTestActivity activity);

    @ActivityScoped
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

}
