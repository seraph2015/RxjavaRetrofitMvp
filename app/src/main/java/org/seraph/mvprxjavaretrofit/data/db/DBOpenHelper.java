package org.seraph.mvprxjavaretrofit.data.db;

import android.content.Context;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;
import org.seraph.mvprxjavaretrofit.data.db.gen.DaoMaster;
import org.seraph.mvprxjavaretrofit.data.db.gen.SearchHistoryTableDao;
import org.seraph.mvprxjavaretrofit.data.db.gen.UserTableDao;

/**
 * 管理表的升级
 * date：2017/9/18 11:24
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class DBOpenHelper extends DaoMaster.OpenHelper {

    public DBOpenHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //数据库升级
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db,ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db,ifExists);
            }
        }, UserTableDao.class, SearchHistoryTableDao.class);
    }
}
