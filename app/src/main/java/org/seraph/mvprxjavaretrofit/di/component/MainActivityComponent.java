package org.seraph.mvprxjavaretrofit.di.component;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.base.ActivityComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.MainModule;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainFourFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainOneFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainThreeFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainTwoFragment;

import dagger.Component;

/**
 * main连接类
 * date：2017/4/6 15:15
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainActivityComponent extends ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(MainOneFragment mainOneFragment);

    void inject(MainTwoFragment mainTwoFragment);

    void inject(MainThreeFragment mainThreeFragment);

    void inject(MainFourFragment mainFourFragment);

}
