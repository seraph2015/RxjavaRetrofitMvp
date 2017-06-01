package org.seraph.mvprxjavaretrofit.ui.module.main;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBasePresenter;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseView;

/**
 * 第1个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface MainOneFragmentContract {

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
