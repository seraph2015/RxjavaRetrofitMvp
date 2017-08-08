package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

/**
 * 欢迎页
 * date：2017/5/3 10:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface GuidePagesActivityContract extends IBaseContract{

    interface View extends IBaseActivityView {

        void jumpNextActivity();

        void setImageList(Integer[] images);
    }

    interface Presenter extends IBaseActivityPresenter<View> {


        void onItemClick(int position);

    }

}
