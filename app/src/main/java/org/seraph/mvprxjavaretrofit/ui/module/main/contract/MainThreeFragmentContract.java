package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

/**
 * 第3个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainThreeFragmentContract extends IBaseContract {


    interface View extends IBaseView {

        void setTextView(CharSequence charSequence);

    }

    interface Presenter extends IBasePresenter<View> {
        void post12306Https();
    }

}
