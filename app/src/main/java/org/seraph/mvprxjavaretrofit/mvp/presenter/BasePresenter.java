package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.graphics.Color;

import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;

/**
 * MVP中Presenter全局父类，包含通用的逻辑操作
 * date：2017/2/15 09:26
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BasePresenter {

    protected BaseView mView;

    /**
     * 绑定view
     */
    public void attachView(BaseView mView) {
        this.mView = mView;
    }

    /**
     * 销毁view,防止内存泄漏
     */
    public void onDestroy() {
        unSubscribe();
        this.mView = null;
    }

    /**
     * 取消订阅
     */
    public void unSubscribe() {
    }

    /**
     * 初始化默认配置(可选)
     */
    public void initBaseDefaultConfig() {
        //不使用沉浸模式
        mView.setStatusBarImmersionMode(false);
        //设置状态栏颜色
        mView.setToolBarBackgroundColor(Color.argb(99, 0, 100, 0));
        //
    }


}
