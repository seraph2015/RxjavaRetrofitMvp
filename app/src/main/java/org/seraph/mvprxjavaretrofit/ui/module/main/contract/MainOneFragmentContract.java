package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

/**
 * 第1个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainOneFragmentContract extends IBaseContract {

    interface View extends IBaseView {

        void setTextViewValue(CharSequence charSequence);

        void setUserTextViewValue(CharSequence charSequence);

    }

    interface Presenter extends IBasePresenter<View> {

        void doLoginTest();

        void saveUserInfo();

        void upDataUserInfo();

        void queryUserInfo();

        void cleanUserInfo();

    }
}