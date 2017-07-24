package org.seraph.mvprxjavaretrofit.di.component.main;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;

import dagger.Component;

/**
 * main连接类
 * date：2017/4/6 15:15
  author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)

public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

    Activity ACTIVITY();

    Context CONTEXT();

}
