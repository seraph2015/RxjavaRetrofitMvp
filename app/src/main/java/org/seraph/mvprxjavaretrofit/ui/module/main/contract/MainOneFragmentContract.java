package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

/**
 * 第1个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainOneFragmentContract extends IABaseContract {

    interface View extends IBaseView {

        void setUserTextViewValue(CharSequence charSequence);

    }

    abstract class Presenter extends ABasePresenter<View> {


    }
}
