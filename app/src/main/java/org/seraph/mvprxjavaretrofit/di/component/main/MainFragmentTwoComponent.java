package org.seraph.mvprxjavaretrofit.di.component.main;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.network.ApiManager;
import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.MainFragmentTwoModule;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainTwoFragment;

import dagger.Component;

/**
 * fragment2连接类
 * date：2017/4/10 16:45
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {MainFragmentTwoModule.class})
public interface MainFragmentTwoComponent {

    void inject(MainTwoFragment mainTwoFragment);

    Activity activity();

    Context context();

    ApiManager apiManager();

    DaoSession daoSession();
}
