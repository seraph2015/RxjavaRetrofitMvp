package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.content.Context;

import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;

/**
 * MVP中Presenter全局父类，包含通用的逻辑操作
 * date：2017/2/15 09:26
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class BasePresenter {

    private BaseView mView;

    /**
     * 绑定view
     */
    public void attachView(BaseView view) {
        this.mView = view;
    }


    /**
     * 取消订阅
     */
    public void unSubscribe() {

    }

    /*接管部分生命周期*/

    /**
     * 销毁view,防止内存泄漏
     */
    public void onDestroy() {
        unSubscribe();
        this.mView = null;
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onRestart() {
    }

    /*fragmentView销毁*/
    public void onDestroyView() {
        unSubscribe();
        this.mView = null;
    }

    public void onAttach(Context context) {
    }

    /**
     * 碎片在共用时恢复（部分保存数据使用）
     */
    public void restoreData() {

    }
}
