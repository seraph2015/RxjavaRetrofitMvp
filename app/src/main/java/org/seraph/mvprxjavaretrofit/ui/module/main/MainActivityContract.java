package org.seraph.mvprxjavaretrofit.ui.module.main;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBasePresenter;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseView;
import org.seraph.mvprxjavaretrofit.utlis.FragmentController;

/**
 * main契约类
 * date：2017/4/6 15:11
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface MainActivityContract {

    interface View extends IBaseView {

        void setTitle(String title);

        FragmentController getFragmentController();

        void showToast(String str);

        void finish();
    }

    interface Presenter extends IBasePresenter<View> {

        void setSelectedFragment(int page);

        void onBackPressed();


    }

}
