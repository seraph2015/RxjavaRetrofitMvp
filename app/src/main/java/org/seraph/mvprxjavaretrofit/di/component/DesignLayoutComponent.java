package org.seraph.mvprxjavaretrofit.di.component;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.DesignLayoutModule;
import org.seraph.mvprxjavaretrofit.ui.module.test.DesignLayoutTestActivity;

import dagger.Component;

/**
 * 布局测试
 * date：2017/4/12 11:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {DesignLayoutModule.class})
public interface DesignLayoutComponent {

    void inject(DesignLayoutTestActivity designLayoutTestActivity);

    Activity ACTIVITY();

    Context CONTEXT();

    LinearLayoutManager LINEAR_LAYOUT_MANAGER();

}
