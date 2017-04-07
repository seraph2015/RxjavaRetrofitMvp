package org.seraph.mvprxjavaretrofit.di.component.main;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.data.network.ApiManager;
import org.seraph.mvprxjavaretrofit.data.local.PreferencesManager;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainFourFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainOneFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainThreeFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainTwoFragment;

import dagger.Component;

/**
 * mian连接类
 * date：2017/4/6 15:15
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(MainOneFragment mainOneFragment);

    void inject(MainTwoFragment mainTwoFragment);

    void inject(MainThreeFragment mainThreeFragment);

    void inject(MainFourFragment mainFourFragment);

    Context context();

    ApiManager apiManager();

    PreferencesManager preferencesManager();

    DaoSession daoSession();

}
