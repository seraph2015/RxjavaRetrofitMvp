package org.seraph.mvprxjavaretrofit.data.local.db.help;

import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.UserBeanTableDao;
import org.seraph.mvprxjavaretrofit.data.local.db.table.UserBeanTable;

import javax.inject.Inject;

/**
 * 专门对于当前登录的用户设计的一个帮助类
 * date：2017/7/26 17:01
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class UserBeanHelp {

    private UserBeanTableDao mUserBeanTableDao;

    @Inject
    public UserBeanHelp(DaoSession daoSession) {
        mUserBeanTableDao = daoSession.getUserBeanTableDao();
    }


    public void save(UserBeanTable userTable) {
        //保证一个用户，所以先移除之前的用户
        mUserBeanTableDao.deleteAll();
        //保存用户 save
        mUserBeanTableDao.save(userTable);
    }

    public void cleanUser() {
        //保证一个用户，所以先移除之前的用户
        mUserBeanTableDao.deleteAll();
    }


    public UserBeanTable getUserBeanTable() {
        if (mUserBeanTableDao.loadAll().size() == 0) {
            return null;
        }
        return mUserBeanTableDao.loadAll().get(0);
    }

}
