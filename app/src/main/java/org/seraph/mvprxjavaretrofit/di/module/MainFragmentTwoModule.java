package org.seraph.mvprxjavaretrofit.di.module;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainTwoFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.adapter.ImageListBaiduAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * MainFragmentTwoModule
 * date：2017/4/10 16:49
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public class MainFragmentTwoModule {

    private final Activity context;

    public MainFragmentTwoModule(MainTwoFragment context) {
        this.context = context.getActivity();
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return context;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return context;
    }

    @Provides
    @ActivityScope
    ImageListBaiduAdapter provideImageListBaiduAdapter() {
        return new ImageListBaiduAdapter(context, new ArrayList<ImageBaiduBean.BaiduImage>());
    }

}
