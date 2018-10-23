package org.seraph.mvprxjavaretrofit.di.module.activity;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.di.scope.FragmentScoped;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainFourFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainOneFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainThreeFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainTwoFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * 主页模型
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ActivityModule.class)
public abstract class MainModule {

    @ActivityScoped
    @Binds
    abstract Activity bindActivity(MainActivity activity);

    @ActivityScoped
    @Provides
    static FragmentManager provideFragmentManager(Activity activity) {
        return ((FragmentActivity) activity).getSupportFragmentManager();
    }

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainOneFragment contributeMainOneFragmentInjector();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainTwoFragment contributeMainTwoFragmentInjector();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainThreeFragment contributeMainThreeFragmentInjector();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainFourFragment contributeMainFourFragmentInjector();

    @ActivityScoped
    @Provides
    static StaggeredGridLayoutManager provideStaggeredGridLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @ActivityScoped
    @Provides
    static List<Fragment> provideListFragment(MainOneFragment one, MainTwoFragment two, MainThreeFragment three, MainFourFragment four) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(one);
        fragments.add(two);
        fragments.add(three);
        fragments.add(four);
        return fragments;
    }

}
