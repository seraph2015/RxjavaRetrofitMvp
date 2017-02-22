package org.seraph.mvprxjavaretrofit.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.seraph.mvprxjavaretrofit.db.gen.DaoMaster;
import org.seraph.mvprxjavaretrofit.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.preference.AppConstant;

/**
 * greenDao数据库帮助类
 * date：2017/2/14 17:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DBGreenDaoHelp {

    private DaoSession mDaoSession;

    private static DBGreenDaoHelp mDBGreenDaoHelp;

    public static DBGreenDaoHelp getSingleton() {
        if (mDBGreenDaoHelp == null) {
            mDBGreenDaoHelp = new DBGreenDaoHelp();
        }
        return mDBGreenDaoHelp;
    }

    private DBGreenDaoHelp() {
    }

    /**
     * 获取操作数据库的Session
     */
    public DaoSession getDaoSession(Context context) {
        if (mDaoSession == null) {
            initDatabase(context);
        }
        return mDaoSession;
    }

    /**
     * 初始化数据库
     */
    private void initDatabase(Context context) {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(context, AppConstant.DB_NAME);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

}
