package org.seraph.mvprxjavaretrofit.di.component.base;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;

import dagger.Component;

/**
 * ActivityComponent
 * date：2017/7/28 10:09
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity ACTIVITY();

    Context CONTEXT();

}
