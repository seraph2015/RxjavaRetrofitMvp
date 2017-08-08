package org.seraph.mvprxjavaretrofit.di.component;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.base.ActivityComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.GuidePagesActivity;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.WelcomeActivity;

import dagger.Component;

/**
 * 欢迎连接类
 * date：2017/4/6 15:15
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface WelcomeComponent extends ActivityComponent {

    void inject(WelcomeActivity welcomeActivity);

    void inject(GuidePagesActivity guidePagesActivity);


}
