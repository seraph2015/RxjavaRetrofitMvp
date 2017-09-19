package org.seraph.mvprxjavaretrofit.data.local.db;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoMaster;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;

import javax.inject.Inject;

/**
 * greenDao数据库初始化
 * date：2017/2/14 17:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DBManager {

    private DBOpenHelper mHelper;

    @Inject
    DBManager(AppApplication context) {
        mHelper = new DBOpenHelper(context.getApplicationContext(), AppConfig.DB_NAME);
    }

    /**
     * 获取操作数据库的Session
     */
    public DaoSession getDaoSession() {
        //该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        return mDaoMaster.newSession();
    }

}
