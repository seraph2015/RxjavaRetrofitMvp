package org.seraph.mvprxjavaretrofit;

import android.app.Application;

import org.seraph.mvprxjavaretrofit.db.DBGreenDaoHelp;
import org.seraph.mvprxjavaretrofit.db.gen.DaoSession;

/**
 * app初始化
 * date：2017/2/14 18:03
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class App extends Application {

    private static App app;

    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDBDaoSession();
    }

    /**
     * 获取单例
     */
    public synchronized App getSingleton(){
        if (app == null){
            app = this;
        }
        return app;
    }

    /**
     * 初始化数据库
     */
    private void initDBDaoSession() {
        mDaoSession = DBGreenDaoHelp.getSingleton().getDaoSession(getSingleton());
    }
    /**
     * 获取操作数据库的对象
     */
    public DaoSession getDaoSession(){
        if(mDaoSession == null){
            initDBDaoSession();
        }
        return mDaoSession;
    }


}
