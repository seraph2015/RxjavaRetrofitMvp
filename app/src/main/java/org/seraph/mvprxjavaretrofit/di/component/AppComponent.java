package org.seraph.mvprxjavaretrofit.di.component;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.network.service.Api12306Service;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiBaiduService;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
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

    AppApplication APP_APPLICATION();

    DaoSession DAO_SESSION();

    ApiService API_SERVICE();

    ApiBaiduService API_BAIDU_SERVICE();

    Api12306Service API_12306_SERVICE();
}
