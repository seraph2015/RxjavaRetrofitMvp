package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

/**
 * 欢迎页
 * date：2017/5/3 10:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface WelcomeActivityContract extends IBaseContract{

    interface View extends IBaseActivityView {

        void jumpNextActivity();
    }

    interface Presenter extends IBaseActivityPresenter<View> {


    }

}
