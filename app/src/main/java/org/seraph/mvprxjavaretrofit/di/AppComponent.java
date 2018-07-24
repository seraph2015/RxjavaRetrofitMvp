package org.seraph.mvprxjavaretrofit.di;

import android.app.Application;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.di.module.ActivityBindingModule;
import org.seraph.mvprxjavaretrofit.di.module.ApiServiceModule;
import org.seraph.mvprxjavaretrofit.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * 全局的Component
 * date：2017/4/5 15:36
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        AppModule.class,
        ApiServiceModule.class,
})
public interface AppComponent extends AndroidInjector<AppApplication> {


    //@BindsInstance使得component可以在构建时绑定实例Application
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
