package org.seraph.mvprxjavaretrofit.di.module.base;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.data.db.DBManager;
import org.seraph.mvprxjavaretrofit.data.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.di.module.ApiServiceModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 全局Module
 * date：2017/4/5 15:37
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module(includes = ApiServiceModule.class)
public class AppModule {

    private final AppApplication application;

    public AppModule(AppApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    AppApplication provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(DBManager dbGreenDaoHelp) {
        return dbGreenDaoHelp.getDaoSession();
    }


}
