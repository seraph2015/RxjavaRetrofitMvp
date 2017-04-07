package org.seraph.mvprxjavaretrofit.di.component;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.data.network.ApiManager;
import org.seraph.mvprxjavaretrofit.data.local.PreferencesManager;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 全局的Component
 * date：2017/4/5 15:36
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(AppApplication appApplication);

    AppApplication app();

    ApiManager apiManager();

    PreferencesManager preferencesManager();

    DaoSession daoSession();
}
