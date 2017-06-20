package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.content.Context;

/**
 * mvp框架P层父类接口
 * date：2017/3/29 15:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface IBaseContract {

    /**
     * v层
     */
    interface IBaseView {

        void showToast(String str);

        void showLoading(String str);

        void hideLoading();

        Context getContext();

    }

    /**
     * p层
     */
    interface IBasePresenter<V extends IBaseView> {

        void setView(V v);

        void start();

        void unSubscribe();

    }
}
