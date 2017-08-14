package org.seraph.mvprxjavaretrofit.di.component;

import android.content.ContentResolver;
import android.support.v7.widget.GridLayoutManager;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.base.ActivityComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.CommonModule;
import org.seraph.mvprxjavaretrofit.ui.module.common.camera2.SmallVideoActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photolist.LocalImageListActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;

import dagger.Component;

/**
 * 通用模块
 * date：2017/4/6 09:05
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {CommonModule.class})
public interface CommonComponent extends ActivityComponent {

    void inject(PhotoPreviewActivity photoPreviewActivity);

    void inject(LocalImageListActivity localImageListActivity);

    void inject(SmallVideoActivity videoActivity);

    GridLayoutManager GRID_LAYOUT_MANAGER();

    ContentResolver CONTENT_RESOLVER();

    RxPermissions RX_PERMISSIONS();

}
