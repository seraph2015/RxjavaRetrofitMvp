package org.seraph.mvprxjavaretrofit.di.module;

import org.seraph.mvprxjavaretrofit.data.db.DBManager;
import org.seraph.mvprxjavaretrofit.data.db.gen.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 全局Module
 * date：2017/4/5 15:37
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public abstract class AppModule {

    @Provides
    @Singleton
    static DaoSession provideDaoSession(DBManager dbGreenDaoHelp) {
        return dbGreenDaoHelp.getDaoSession();
    }

}

