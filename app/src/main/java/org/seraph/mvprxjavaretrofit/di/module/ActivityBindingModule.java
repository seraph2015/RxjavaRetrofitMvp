package org.seraph.mvprxjavaretrofit.di.module;

import org.seraph.mvprxjavaretrofit.di.module.activity.DesignLayoutModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.LocalImageListModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.LoginModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.MainModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.RegisteredModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.ResetPasswordModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.AgreementModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.PhotoPreviewModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.WelcomeModule;
import org.seraph.mvprxjavaretrofit.di.module.activity.GuidePagesModule;
import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.common.photolist.LocalImageListActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.AgreementActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.LoginActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.RegisteredActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.ResetPasswordActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;
import org.seraph.mvprxjavaretrofit.ui.module.test.DesignLayoutTestActivity;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.GuidePagesActivity;
import org.seraph.mvprxjavaretrofit.ui.module.welcome.WelcomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 负责所有ActivityModule实例的管理
 * date：2018/7/20 16:00
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public abstract class ActivityBindingModule {


    @ActivityScoped
    @ContributesAndroidInjector(modules = {WelcomeModule.class})
    abstract WelcomeActivity contributeWelcomeActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {GuidePagesModule.class})
    abstract GuidePagesActivity contributeGuidePagesActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {LocalImageListModule.class})
    abstract LocalImageListActivity contributeLocalImageListActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {PhotoPreviewModule.class})
    abstract PhotoPreviewActivity contributePhotoPreviewActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {LoginModule.class})
    abstract LoginActivity contributeLoginActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {RegisteredModule.class})
    abstract RegisteredActivity contributeRegisteredActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {ResetPasswordModule.class})
    abstract ResetPasswordActivity contributeResetPasswordActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {AgreementModule.class})
    abstract AgreementActivity contributeAgreementActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {DesignLayoutModule.class})
    abstract DesignLayoutTestActivity contributeDesignLayoutTestActivityInjector();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity contributeMainActivityInjector();


}
