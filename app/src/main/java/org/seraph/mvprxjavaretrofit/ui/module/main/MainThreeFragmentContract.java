package org.seraph.mvprxjavaretrofit.ui.module.main;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBasePresenter;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseView;

/**
 * 第3个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface MainThreeFragmentContract {


    interface View extends IBaseView {

        void setTextView(CharSequence charSequence);

    }

    interface Presenter extends IBasePresenter<View> {
        void post12306Https();
    }

}
