package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.graphics.Color;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseActivityView;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;

/**
 * MVP中Presenter全局父类，包含通用的逻辑操作
 * date：2017/2/15 09:26
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class BaseActivityPresenter extends BasePresenter {

    private BaseActivityView mView;

    /**
     * 绑定view
     */
    @Override
    public void attachView(BaseView mView) {
        super.attachView(mView);
        this.mView = (BaseActivityView) mView;
    }

    /**
     * 配置默认可选配置
     */
    public void initBaseDefaultConfig() {
        //设置背景颜色
        mView.setBackgroundResource(R.mipmap.bg_app);
        //不使用沉浸模式
        mView.setStatusBarImmersionMode(false);
        //设置状态栏颜色
        mView.setToolBarBackgroundColor(Color.argb(50, 0x9E, 0x57, 0xFF));
    }



}
