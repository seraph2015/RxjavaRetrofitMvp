package org.seraph.mvprxjavaretrofit.di.component;


import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.di.component.base.ActivityComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.login.AgreementActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.LoginActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.RegisteredActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.ResetPasswordActivity;

import dagger.Component;

/**
 * 登录模块连接类
 * date：2017/4/6 15:15
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface LoginComponent extends ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(RegisteredActivity registeredActivity);

    void inject(AgreementActivity agreementActivity);

    void inject(ResetPasswordActivity resetPasswordActivity);
}
