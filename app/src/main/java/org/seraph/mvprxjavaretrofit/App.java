package org.seraph.mvprxjavaretrofit;

import android.app.Application;

import org.seraph.mvprxjavaretrofit.db.DBGreenDaoHelp;
import org.seraph.mvprxjavaretrofit.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.request.picasso.PicassoFactory;

/**
 * app初始化
 * date：2017/2/14 18:03
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class App extends Application {

    private static App instance;

    private static DaoSession mDaoSession;

    public App() {
        super();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDBDaoSession();
        initPicasso();
    }

    private void initPicasso() {
        PicassoFactory.initPicassoToOkHttp(this);
       // Picasso.with(this).setIndicatorsEnabled(true);
    }

    /**
     * 获取单例
     */
    public static synchronized App getSingleton() {
        return instance;
    }

    /**
     * 初始化数据库
     */
    private static void initDBDaoSession() {
        mDaoSession = DBGreenDaoHelp.getSingleton().getDaoSession(getSingleton());
    }

    /**
     * 获取操作数据库的对象
     */
    public static DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initDBDaoSession();
        }
        return mDaoSession;
    }



}
