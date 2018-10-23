package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.app.Dialog;
import android.content.Context;

import com.uber.autodispose.AutoDisposeConverter;

import androidx.lifecycle.Lifecycle;


/**
 * mvp框架P层父类接口
 * date：2017/3/29 15:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface IBaseContract {

    /**
     * base view层
     * 公共常用操作showToast请参考{@link com.blankj.utilcode.util.ToastUtils}类
     */
    interface IBaseView {

        Context getContext();

        <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event untilEvent);

        <T> AutoDisposeConverter<T> bindLifecycle();

        Dialog showLoading();

        Dialog showLoading(String str);

        void hideLoading();

        void finish();
    }

    /**
     * base presenter层
     */
    interface IBasePresenter<V extends IBaseView> {

        void setView(V v);

        void start();

        void onDetach();

    }

}
