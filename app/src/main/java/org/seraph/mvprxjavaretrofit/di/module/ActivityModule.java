package org.seraph.mvprxjavaretrofit.di.module;

import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Activity注入模块 与Activity生命周期相同
 * date：2017/4/6 09:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @ActivityScope
    Context provideContextActivity() {
        return mContext;
    }

}
