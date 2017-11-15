package org.seraph.mvprxjavaretrofit.data.local.db.help;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.UserTableDao;
import org.seraph.mvprxjavaretrofit.data.local.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.ui.module.login.LoginActivity;

import javax.inject.Inject;

/**
 * 专门对于当前登录的用户设计的一个帮助类
 * date：2017/7/26 17:01
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class UserBeanHelp {

    private UserTableDao mUserTableDao;

    private Context mContext;

    @Inject
    public UserBeanHelp(Context context, DaoSession daoSession) {
        mUserTableDao = daoSession.getUserTableDao();
        mContext = context;

    }


    public String getUserToken() {
        return getUserToken(false);
    }

    /**
     * 获取当前用户的token
     */
    public String getUserToken(boolean isAutoJumpLogin) {
        if (getUserBean(isAutoJumpLogin) != null) {
            return getUserBean(isAutoJumpLogin).getToken();
        }
        return null;
    }


    /**
     * 获取用户表
     */
    public UserTable getUserBean() {
        return getUserBean(false);
    }

    /**
     * 获取用户表
     *
     * @param isAutoJumpLogin 没有用户是否自动跳转登录界面
     */
    public UserTable getUserBean(boolean isAutoJumpLogin) {
        if (mUserTableDao != null && mUserTableDao.loadAll().size() > 0) {
            return mUserTableDao.loadAll().get(0);
        }
        if (isAutoJumpLogin) {
            startLoginActivity();
        }
        return null;
    }


    /**
     * 保存唯一用户
     */
    public void saveUserBean(UserTable userTable) {
        mUserTableDao.deleteAll();
        mUserTableDao.save(userTable);
    }

    /**
     * 更新或者保存用户信息
     */
    public void save(UserTable userTable) {
        mUserTableDao.save(userTable);
    }

    /**
     * 清除登录信息
     */
    public void cleanUserBean() {
        mUserTableDao.deleteAll();
    }


    public boolean isLogin() {
        return isLogin(false);
    }

    /**
     * 是否登录
     *
     * @param isAutoJumpLogin 在没有登录的情况下是否跳转
     */
    public boolean isLogin(boolean isAutoJumpLogin) {
        if (mUserTableDao != null && mUserTableDao.loadAll().size() > 0) {
            return true;
        }
        if (isAutoJumpLogin) {
            startLoginActivity();
        }
        return false;
    }

    private void startLoginActivity() {
        ToastUtils.showShort("您还未登录");
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }
}
