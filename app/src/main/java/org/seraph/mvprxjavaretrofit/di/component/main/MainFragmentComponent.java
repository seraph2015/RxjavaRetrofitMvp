package org.seraph.mvprxjavaretrofit.di.component.main;

import android.app.Activity;
import android.content.Context;

import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.FragmentModule;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainFourFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainOneFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainThreeFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainTwoFragment;

import dagger.Component;

/**
 * fragment连接类
 * date：2017/4/10 16:45
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface MainFragmentComponent {

    void inject(MainOneFragment mainOneFragment);

    void inject(MainTwoFragment mainTwoFragment);

    void inject(MainThreeFragment mainThreeFragment);

    void inject(MainFourFragment mainFourFragment);

    Activity ACTIVITY();

    Context CONTEXT();

}
