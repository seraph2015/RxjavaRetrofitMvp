package org.seraph.mvprxjavaretrofit.data.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    private Context mContext;

    private DaoMaster.DevOpenHelper mHelper;

    @Inject
    DBManager(AppApplication context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 获取操作数据库的Session
     */
    public DaoSession getDaoSession() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(mContext, AppConfig.DB_NAME);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        return mDaoMaster.newSession();
    }

}