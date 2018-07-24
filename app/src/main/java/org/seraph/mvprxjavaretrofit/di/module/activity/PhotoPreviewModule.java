package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;

import dagger.Binds;
import dagger.Module;

/**
 * 图片预览
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class PhotoPreviewModule {

    @ActivityScoped
    @Binds
    abstract Context bindContext(PhotoPreviewActivity activity);

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(PhotoPreviewActivity activity);


}
