package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

/**
 * 第3个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainThreeFragmentContract extends IABaseContract {


    interface View extends IBaseFragmentView {

        void setTextView(CharSequence charSequence);

        void setDownloadProgressRate(long downloadSize, long rate);
    }

    abstract class Presenter extends ABaseFragmentPresenter<View> {

    }

}
