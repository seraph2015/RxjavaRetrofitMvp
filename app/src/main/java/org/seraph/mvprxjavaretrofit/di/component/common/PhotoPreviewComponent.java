package org.seraph.mvprxjavaretrofit.di.component.common;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;

import dagger.Component;

/**
 * 图片预览
 * date：2017/4/6 09:05
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface PhotoPreviewComponent {

    void inject(PhotoPreviewActivity photoPreviewActivity);

    Activity activity();

    Context context();

}
