package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import android.os.Bundle;
import android.support.annotation.DrawableRes;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

/**
 * main契约类
 * date：2017/4/6 15:11
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainActivityContract extends IBaseContract {

    interface View extends IBaseActivityView {

        void setTitle(String title);

        void setBackgroundResource(@DrawableRes int resid);

    }

    interface Presenter extends IBaseActivityPresenter<View> {

        void setSelectedFragment(int page);

        void onBackPressed();

        void onSaveInstanceState(Bundle outState);

    }

}
