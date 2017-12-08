package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

/**
 * 欢迎页
 * date：2017/5/3 10:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface WelcomeActivityContract extends IABaseContract {

    interface View extends IBaseActivityView {

        void jumpNextActivity();
    }

    abstract class Presenter extends ABaseActivityPresenter<View> {


    }

}
