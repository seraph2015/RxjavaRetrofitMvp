package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;
import org.seraph.mvprxjavaretrofit.utlis.FragmentController;

/**
 * main契约类
 * date：2017/4/6 15:11
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainActivityContract extends IBaseContract {

    interface View extends IBaseActivityView {

        void setTitle(String title);

        FragmentController getFragmentController();

        void showToast(String str);

        void finish();
    }

    interface Presenter extends IBaseActivityPresenter<View> {

        void setSelectedFragment(int page);

        void onBackPressed();


    }

}
