package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;

/**
 * mvp框架P层父类接口
 * date：2017/3/29 15:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface IBaseContract {

    /**
     * base view层
     */
    interface IBaseView {

        void showToast(String str);

        void showLoading(String str);

        void hideLoading();

        Context getContext();

    }

    /**
     * base presenter层
     */
    interface IBasePresenter<V extends IBaseView> {
        void setView(V v);

        void start();

        void unSubscribe();
    }


    /* rxJava管理*/


    interface IBaseActivityView extends IBaseView {

        Observable<ActivityEvent> lifecycle();

        <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event);

        <T> LifecycleTransformer<T> bindToLifecycle();
    }


    interface IBaseFragmentView extends IBaseView {

        Observable<FragmentEvent> lifecycle();

        <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event);

        <T> LifecycleTransformer<T> bindToLifecycle();
    }


    interface IBaseFragmentPresenter<V extends IBaseFragmentView> extends IBasePresenter<V> {

    }

    interface IBaseActivityPresenter<V extends IBaseActivityView> extends IBasePresenter<V> {

    }

}
