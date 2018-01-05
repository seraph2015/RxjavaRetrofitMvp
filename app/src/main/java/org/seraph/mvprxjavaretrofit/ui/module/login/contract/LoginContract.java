package org.seraph.mvprxjavaretrofit.ui.module.login.contract;


import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

/**
 * 登录契约类
 * date：2017/10/25 10:29
 * author：Seraph
 * mail：417753393@qq.com
 **/
public interface LoginContract extends IABaseContract {

    interface View extends IBaseActivityView{

        void setUserName(String username);
    }

    abstract class Presenter extends ABaseActivityPresenter<View>{

    }

}